package ksrve;

import ksrve.entity.Business;
import ksrve.entity.SearchObject;
import ksrve.entity.Service;
import ksrve.exceptions.*;
import ksrve.util.EntityPrinter;
import org.apache.juddi.api_v3.AccessPointType;
import org.apache.juddi.v3.client.UDDIConstants;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.Transport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.uddi.api_v3.*;
import org.uddi.v3_service.UDDIInquiryPortType;
import org.uddi.v3_service.UDDIPublicationPortType;
import org.uddi.v3_service.UDDISecurityPortType;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The {@code JuddiClientImpl} class implements the {@code JuddiClient} interface.
 * It provides methods to register businesses and services, as well as to discover
 * services from a mock JUDDI registry.
 */
public class JuddiClientImpl implements JuddiClient {
    /**
     * Logger.
     */
    private static final Logger logger = LogManager.getLogger(JuddiClientImpl.class);
    /**
     * Security UDDI port.
     */
    private UDDISecurityPortType security = null;
    /**
     * Publisher UDDI port for business and service registration.
     */
    private UDDIPublicationPortType publisher = null;
    /**
     * Inquire UDDI port for finding services and business entities from jUDDI.
     */
    private UDDIInquiryPortType inquiry = null;
    /**
     * Token for requests.
     */
    private GetAuthToken token = null;

    /**
     * Constructor
     * @param username for auth
     * @param password for auth
     */
    public JuddiClientImpl(
            String username,
            String password
    ) {
        try {
            UDDIClient client = new UDDIClient("uddi.xml");
            Transport transport = client.getTransport("default");

            this.security = transport.getUDDISecurityService();
            this.publisher = transport.getUDDIPublishService();
            this.inquiry = transport.getUDDIInquiryService();

            this.token = new GetAuthToken(username, password);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Provide auth token for JUDDI registry.
     *
     * @return auth token
     * @throws RemoteException if it fails
     */
    @Override
    public AuthToken provideToken() throws RemoteException {
        return this.security.getAuthToken(this.token);
    }

    /**
     * Registers a business entity with the JUDDI registry.
     *
     * @param business object with name and description
     * @throws RegistrationException if the registration fails
     */
    @Override
    public String registerBusiness(Business business)
            throws RegistrationException, BusinessAlreadyExistsException {
        logger.info("Register business with name '{}' and description '{}'",
                business.getName(), business.getDescription());
        try {

            /* Searching for business */
            List<String> searchQualifiers = new ArrayList<>();
            searchQualifiers.add(UDDIConstants.EXACT_MATCH);

            List<BusinessInfo> businessInfos = null;
            try {
                businessInfos = discoverBusinesses(
                        SearchObject.builder()
                                .searchString(business.getName())
                                .qualifiers(searchQualifiers)
                                .build());
            } catch (Exception e) {
                logger.info("Business registration is allowed");
            }

            if (businessInfos != null && !businessInfos.isEmpty()) throw new BusinessAlreadyExistsException();

            BusinessEntity businessEntity = new BusinessEntity();
            Name name = new Name(business.getName(), Locale.ENGLISH.getDisplayLanguage());
            Description description = new Description(business.getDescription(), Locale.ENGLISH.getDisplayLanguage());

            businessEntity.getName().add(name);
            businessEntity.getDescription().add(description);

            SaveBusiness sb = new SaveBusiness();
            sb.getBusinessEntity().add(businessEntity);
            sb.setAuthInfo(this.provideToken().getAuthInfo());

            String businessKey = this.publisher.saveBusiness(sb).getBusinessEntity().getFirst().getBusinessKey();
            logger.info("Business is registered under the identifier {}", businessKey);
            return businessKey;
        }catch (BusinessAlreadyExistsException e){
            throw e;
        } catch (Exception e) {
            throw new RegistrationException(
                    String.format("Error occurred while trying to register business with name %s: %s",
                            business.getName(), e.getMessage()));
        }
    }

    /**
     * Discovers business that are registered in the JUDDI registry based on given criteria.
     *
     * @param searchCriteria a SearchObject representing the criteria for searching
     * @return list of business infos that match the search criteria
     * @throws DiscoveryException if the discovery process fails
     * @throws BusinessNotFoundException if there is no business found
     */
    @Override
    public List <BusinessInfo> discoverBusinesses(SearchObject searchCriteria)
            throws DiscoveryException, BusinessNotFoundException {
        try {
            logger.info("Discover business with name '{}'", searchCriteria.getSearchString());
            Name searchName = new Name();
            if (searchCriteria.getSearchString() != null ){
                searchName.setValue(searchCriteria.getSearchString());
            } else {
                searchName.setValue(UDDIConstants.WILDCARD);
            }
            searchName.setValue(searchCriteria.getSearchString());

            FindBusiness fb = new FindBusiness();
            fb.getName().add(searchName);
            fb.setAuthInfo(this.provideToken().getAuthInfo());

            FindQualifiers fq = new FindQualifiers();
            if (searchCriteria.getQualifiers() != null) {
                for (String qualifier : searchCriteria.getQualifiers()) {
                    fq.getFindQualifier().add(qualifier);
                }
                fb.setFindQualifiers(fq);
            }

            // Execute the business search
            BusinessList findBusiness = this.inquiry.findBusiness(fb);

            try {
                List<BusinessInfo> businessInfos = findBusiness.getBusinessInfos().getBusinessInfo();
                if (businessInfos.isEmpty()) throw new BusinessNotFoundException(searchCriteria.getSearchString());
                return businessInfos;
            } catch (NullPointerException e) {
                throw new BusinessNotFoundException(searchCriteria.getSearchString());
            }
        } catch (BusinessNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DiscoveryException(
                    String.format("Error occurred while trying to find business by search string %s: %s",
                            searchCriteria.getSearchString(), e.getMessage()));
        }
    }


    /**
     * Discovers business key that are registered in the JUDDI registry based on given criteria.
     *
     * @param searchCriteria a SearchObject representing the criteria for searching
     * @return string of first business object that matched the search criteria
     * @throws DiscoveryException if the discovery process fails
     * @throws BusinessNotFoundException if there is no business found
     */
    @Override
    public String discoverBusinessKey(SearchObject searchCriteria)
            throws DiscoveryException, BusinessNotFoundException {
        List<BusinessInfo> businessInfos = discoverBusinesses(searchCriteria);
        return businessInfos.getFirst().getBusinessKey();
    }

    /**
     * Registers a service entity with the JUDDI registry.
     *
     * @param service object with name and description
     * @throws RegistrationException if the registration fails
     */
    public String registerService(Service service)
            throws RegistrationException, ServiceAlreadyExistsException {
        logger.info("Register service with business key '{}' name '{}' and description '{}'",
                service.getBusinessKey(), service.getName(), service.getDescription());
        try {

            /* Searching for service */
            List<String> searchQualifiers = new ArrayList<>();
            searchQualifiers.add(UDDIConstants.EXACT_MATCH);

            List<ServiceInfo> serviceInfos = null;
            try {
                serviceInfos = discoverServices(
                        service.getBusinessKey(),
                        SearchObject.builder()
                                .searchString(service.getName())
                                .qualifiers(searchQualifiers)
                                .build());
            } catch (Exception e) {
                logger.info("Service registration is allowed");
            }

            if (serviceInfos != null && !serviceInfos.isEmpty()) throw new ServiceAlreadyExistsException();

            BusinessService serviceEntity = new BusinessService();

            Name name = new Name(service.getName(), Locale.ENGLISH.getDisplayLanguage());
            Description description = new Description(service.getDescription(), Locale.ENGLISH.getDisplayLanguage());

            serviceEntity.setBusinessKey(service.getBusinessKey());
            serviceEntity.getName().add(name);
            serviceEntity.getDescription().add(description);

            SaveService sb = new SaveService();
            sb.getBusinessService().add(serviceEntity);
            sb.setAuthInfo(this.provideToken().getAuthInfo());

            String serviceKey = this.publisher.saveService(sb).getBusinessService().getFirst().getServiceKey();
            logger.info("Service is registered under the identifier {}", serviceKey);
            return serviceKey;
        }catch (ServiceAlreadyExistsException e){
            throw e;
        } catch (Exception e) {
            throw new RegistrationException(String.format("Error occurred while trying to register business with name %s: %s",
                    service.getName(), e.getMessage()));
        }
    }

    /**
     * Discovers service that are registered in the JUDDI registry based on given criteria.
     *
     * @param searchCriteria a SearchObject representing the criteria for searching
     * @return list of service infos that match the search criteria
     * @throws DiscoveryException if the discovery process fails
     * @throws ServiceNotFoundException if there is no business found
     */
    @Override
    public List<ServiceInfo> discoverServices(String businessKey, SearchObject searchCriteria)
            throws DiscoveryException, ServiceNotFoundException {
        try {
            logger.info("Discover services with name {}", searchCriteria.getSearchString());
            Name searchName = new Name();
            if (searchCriteria.getSearchString() != null ){
                searchName.setValue(searchCriteria.getSearchString());
            } else {
                searchName.setValue(UDDIConstants.WILDCARD);
            }

            FindService fs = new FindService();
            fs.getName().add(searchName);
            fs.setBusinessKey(businessKey);
            fs.setAuthInfo(this.provideToken().getAuthInfo());

            FindQualifiers fq = new FindQualifiers();
            if (searchCriteria.getQualifiers() != null) {
                for (String qualifier : searchCriteria.getQualifiers()) {
                    fq.getFindQualifier().add(qualifier);
                }
                fs.setFindQualifiers(fq);
            }

            // Execute the service search
            ServiceList findService = this.inquiry.findService(fs);

            try {
                List<ServiceInfo> serviceInfos = findService.getServiceInfos().getServiceInfo();
                if (serviceInfos.isEmpty()) throw new ServiceNotFoundException();
                return serviceInfos;
            } catch (NullPointerException e) {
                throw new ServiceNotFoundException();
            }
        } catch (ServiceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DiscoveryException(
                    String.format("Error occurred while trying to find business by search string %s: %s",
                            searchCriteria.getSearchString(), e.getMessage()));
        }
    }

    /**
     * Discovers service key that are registered in the JUDDI registry based on given criteria.
     *
     * @param businessKey key of the business
     * @param searchCriteria a SearchObject representing the criteria for searching
     * @return service info that match the search criteria
     * @throws DiscoveryException if the discovery process fails
     * @throws ServiceNotFoundException if there is no business found
     */
    @Override
    public ServiceInfo discoverServiceInfo(String businessKey, SearchObject searchCriteria)
            throws DiscoveryException, ServiceNotFoundException {
        List<ServiceInfo> serviceInfos = discoverServices(businessKey, searchCriteria);
        return serviceInfos.getFirst();
    }

    /**
     * Bind service url to exist service.
     *
     * @param service object of service
     * @return binding details
     * @throws RemoteException if got an error from jUDDI
     */
    @Override
    public BindingDetail bindService(Service service)
            throws RemoteException {
        logger.info("Bind url '{}' to service with key '{}'", service.getUrl(), service.getKey());

        AccessPoint accessPoint = new AccessPoint();
        accessPoint.setValue(service.getUrl());
        accessPoint.setUseType(AccessPointType.WSDL_DEPLOYMENT.toString());

        BindingTemplate bindingTemplate = new BindingTemplate();
        bindingTemplate.setServiceKey(service.getKey());
        bindingTemplate.setAccessPoint(accessPoint);

        var bindingTemplateSoap = UDDIClient.addSOAPtModels(bindingTemplate);

        SaveBinding sb = new SaveBinding();
        sb.setAuthInfo(this.provideToken().getAuthInfo());
        sb.getBindingTemplate().add(bindingTemplateSoap);
        return this.publisher.saveBinding(sb);
    }

    /**
     * Discovers service binding.
     *
     * @param serviceKey key of the service
     * @return binding details
     * @throws RemoteException  if got an error from jUDDI
     */
    @Override
    public BindingDetail discoverBindingInfo(String serviceKey)
            throws RemoteException {
        FindBinding findBindingData = new FindBinding();
        findBindingData.setServiceKey(serviceKey);
        return this.inquiry.findBinding(findBindingData);
    }
}
