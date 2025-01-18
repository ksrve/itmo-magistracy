package ksrve;

import ksrve.entity.Business;
import ksrve.entity.SearchObject;
import ksrve.entity.Service;
import ksrve.exceptions.*;
import ksrve.util.EntityPrinter;
import org.apache.juddi.api_v3.AccessPointType;
import org.uddi.api_v3.*;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UDDIProviderImpl implements UDDIProvider {

    /**
     * Juddi client.
     */
    private final JuddiClientImpl client;


    /**
     * Default business name.
     */
    public static final String BUSINESS_NAME = "WEB SERVICES";

    /**
     * Constructor
     * @param client JuddiClientImpl
     */
    public UDDIProviderImpl(JuddiClientImpl client){
        this.client = client;
    }


    /**
     * Find services by business name and service name.
     *
     * @param businessName Name of business entity.
     * @param serviceName Name of service
     * @return list of service info
     * @throws DiscoveryException if got an error from jUDDI
     * @throws ServiceNotFoundException if service did not find
     * @throws BusinessNotFoundException if business did not find
     */
    @Override
    public List<ServiceInfo> findServices(String serviceName, String businessName) throws
            DiscoveryException, ServiceNotFoundException, BusinessNotFoundException {
        if (businessName == null) businessName = BUSINESS_NAME;
        String businessKey = this.client.discoverBusinessKey(
                SearchObject.builder()
                        .searchString(businessName)
                        .build()
        );
        return this.client.discoverServices(
                businessKey,
                SearchObject.builder()
                        .searchString(serviceName)
                        .build()
        );
    }

    /**
     * Find businesses by business name.
     *
     * @param businessName Name of business entity.
     * @return list of business info
     * @throws DiscoveryException if got an error from jUDDI
     * @throws BusinessNotFoundException if business did not find
     */
    @Override
    public List<BusinessInfo> findBusinesses(String businessName)
            throws DiscoveryException, BusinessNotFoundException {
        if (businessName == null) businessName = BUSINESS_NAME;
        return this.client.discoverBusinesses(
                SearchObject.builder()
                        .searchString(businessName)
                        .build()
        );
    }

    /**
     * Register business.
     *
     * @param name Name of business entity.
     * @param description Description of business entity.
     * @return identifier of created business
     * @throws RegistrationException if got an error from jUDDI
     * @throws BusinessAlreadyExistsException if business already exists
     */
    @Override
    public String registerBusiness(String name, String description)
            throws RegistrationException, BusinessAlreadyExistsException {
        return this.client.registerBusiness(
                Business.builder()
                        .name(name)
                        .description(description)
                        .build()
        );
    }

    /**
     * Register service.
     *
     * @param businessName Name of business entity.
     * @param serviceName Name of service entity.
     * @param description Description of service entity.
     * @return identifier of created service
     * @throws RegistrationException if got an error from jUDDI
     * @throws ServiceAlreadyExistsException if service already exists
     * @throws DiscoveryException if got an error from jUDDI
     * @throws BusinessNotFoundException if business did not find.
     */
    public String registerService(String serviceName, String description, String businessName)
            throws RegistrationException, ServiceAlreadyExistsException, DiscoveryException, BusinessNotFoundException {
        if (businessName == null) businessName = BUSINESS_NAME;
        String businessKey = this.client.discoverBusinessKey(
                SearchObject.builder()
                        .searchString(businessName)
                        .build()
        );
        return this.client.registerService(
                Service.builder()
                        .name(serviceName)
                        .description(description)
                        .businessKey(businessKey)
                        .build());
    }

    /**
     * Register service with binding.
     *
     * @param businessName Name of business entity.
     * @param serviceName Name of service entity.
     * @param description Description of service entity.
     * @param url URL for service binding/
     * @return BindingDetail
     * @throws RegistrationException if got an error from jUDDI
     * @throws ServiceAlreadyExistsException if service already exists
     * @throws DiscoveryException if got an error from jUDDI
     * @throws BusinessNotFoundException if business did not find.
     * @throws RemoteException if binding is not successful.
     */
    public BindingDetail registerService(String serviceName, String description, String url, String businessName)
            throws RegistrationException, ServiceAlreadyExistsException, DiscoveryException, BusinessNotFoundException, RemoteException {
        if (businessName == null) businessName = BUSINESS_NAME;
        // Get business key
        String businessKey = this.client.discoverBusinessKey(
                SearchObject.builder()
                        .searchString(businessName)
                        .build()
        );
        // Create service
        String serviceKey = this.client.registerService(
                Service.builder()
                        .name(serviceName)
                        .description(description)
                        .businessKey(businessKey)
                        .build());
        // Create binding
        return this.client.bindService(
                Service.builder()
                        .key(serviceKey)
                        .name(serviceName)
                        .description(description)
                        .businessKey(businessKey)
                        .url(url)
                        .build()
        );
    }

    /**
     * Get service WSDL url.
     *
     * @param businessName Name of business entity.
     * @param serviceName Name of service entity.
     * @return service.url.
     * @throws DiscoveryException if got an error from jUDDI.
     * @throws ServiceNotFoundException if service did not find.
     * @throws BusinessNotFoundException if business did not find.
     * @throws RemoteException if got an error from jUDDI.
     */
    @Override
    public String getServiceUrl(String serviceName, String businessName)
            throws DiscoveryException, ServiceNotFoundException, BusinessNotFoundException, RemoteException {
        if (businessName == null) businessName = BUSINESS_NAME;
        // Find service
        String serviceKey = findServices(serviceName, businessName).getFirst().getServiceKey();
        BindingDetail bd = this.client.discoverBindingInfo(serviceKey);
        String serviceUrl = "";
        if (bd != null) {
            List<BindingTemplate> bindingTemplates = bd.getBindingTemplate();

            for (BindingTemplate template : bindingTemplates) {
                AccessPoint accessPoint = template.getAccessPoint();
                if (accessPoint.getUseType().equalsIgnoreCase(AccessPointType.WSDL_DEPLOYMENT.toString())) {
                    serviceUrl = accessPoint.getValue();
                }
            }
        }
        return serviceUrl;
    }
}
