package ksrve;

import ksrve.entity.Business;
import ksrve.entity.SearchObject;
import ksrve.entity.Service;
import ksrve.exceptions.*;
import org.uddi.api_v3.AuthToken;
import org.uddi.api_v3.BindingDetail;
import org.uddi.api_v3.BusinessInfo;
import org.uddi.api_v3.ServiceInfo;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;


/**
 * The {@code JuddiClient} interface is responsible for interacting with a JUDDI registry.
 * It allows for the registration of businesses, services, and the discovery of services
 * within the JUDDI registry.
 */
public interface JuddiClient {

    /**
     * Provide auth token for JUDDI registry.
     *
     * @return auth token
     * @throws RemoteException if it fails
     */
    AuthToken provideToken() throws RemoteException;

    /**
     * Registers a business entity with the JUDDI registry.
     *
     * @param business object with name and description
     * @throws RegistrationException if the registration fails
     */
    String registerBusiness(Business business)
            throws RegistrationException, BusinessAlreadyExistsException;

    /**
     * Discovers business that are registered in the JUDDI registry based on given criteria.
     *
     * @param searchCriteria a SearchObject representing the criteria for searching
     * @return list of business infos that match the search criteria
     * @throws DiscoveryException if the discovery process fails
     * @throws BusinessNotFoundException if there is no business found
     */
    List<BusinessInfo> discoverBusinesses(SearchObject searchCriteria)
            throws DiscoveryException, BusinessNotFoundException;

    /**
     * Discovers business key that are registered in the JUDDI registry based on given criteria.
     *
     * @param searchCriteria a SearchObject representing the criteria for searching
     * @return key of first business object that matched the search criteria
     * @throws DiscoveryException if the discovery process fails
     * @throws BusinessNotFoundException if there is no business found
     */
    String discoverBusinessKey(SearchObject searchCriteria)
            throws DiscoveryException, BusinessNotFoundException;

    /**
     * Registers a service entity with the JUDDI registry.
     *
     * @param service object of service
     * @throws RegistrationException if the registration fails
     */
    String registerService(Service service)
            throws RegistrationException, ServiceAlreadyExistsException;

    /**
     * Discovers service that are registered in the JUDDI registry based on given criteria.
     *
     * @param businessKey key of the business
     * @param searchCriteria a SearchObject representing the criteria for searching
     * @return list of service infos that match the search criteria
     * @throws DiscoveryException if the discovery process fails
     * @throws ServiceNotFoundException if there is no business found
     */
    List<ServiceInfo> discoverServices(String businessKey, SearchObject searchCriteria)
            throws DiscoveryException, ServiceNotFoundException;

    /**
     * Discovers service key that are registered in the JUDDI registry based on given criteria.
     *
     * @param businessKey key of the business
     * @param searchCriteria a SearchObject representing the criteria for searching
     * @return service info that match the search criteria
     * @throws DiscoveryException if the discovery process fails
     * @throws ServiceNotFoundException if there is no business found
     */
    ServiceInfo discoverServiceInfo(String businessKey, SearchObject searchCriteria)
            throws DiscoveryException, ServiceNotFoundException;

    /**
     * Bind service url to exist service.
     *
     * @param service object of service
     * @return binding details
     * @throws RemoteException if got an error from jUDDI
     */
    BindingDetail bindService(Service service)
            throws RemoteException;

    /**
     * Discovers service binding.
     *
     * @param serviceKey key of the service
     * @return binding details
     * @throws RemoteException  if got an error from jUDDI
     */
    BindingDetail discoverBindingInfo(String serviceKey)
            throws RemoteException;
}