package ksrve;

import ksrve.entity.Service;
import ksrve.exceptions.*;
import org.uddi.api_v3.BindingDetail;
import org.uddi.api_v3.BusinessInfo;
import org.uddi.api_v3.ServiceInfo;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;

public interface UDDIProvider {

    /**
     * Find services by business name and service name.
     *
     * @param businessName Name of business entity.
     * @param serviceName Name of service entity.
     * @return list of service info.
     * @throws DiscoveryException if got an error from jUDDI.
     * @throws ServiceNotFoundException if service did not find.
     * @throws BusinessNotFoundException if business did not find.
     */
    List<ServiceInfo> findServices(String serviceName, String businessName)
            throws DiscoveryException, ServiceNotFoundException, BusinessNotFoundException;

    /**
     * Find services by business name and service name.
     *
     * @param businessName Name of business entity.
     * @return list of business info.
     * @throws DiscoveryException if got an error from jUDDI.
     * @throws BusinessNotFoundException if business did not find.
     */
    List<BusinessInfo> findBusinesses(String businessName)
            throws DiscoveryException, BusinessNotFoundException;

    /**
     * Register business.
     *
     * @param name Name of business entity.
     * @param description Description of business entity.
     * @return identifier of created business.
     * @throws RegistrationException if got an error from jUDDI.
     * @throws BusinessAlreadyExistsException if business already exists.
     */
    String registerBusiness(String name, String description)
            throws RegistrationException, BusinessAlreadyExistsException;

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
    String registerService(String serviceName, String description, String businessName)
            throws RegistrationException, ServiceAlreadyExistsException, DiscoveryException, BusinessNotFoundException;

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
    BindingDetail registerService(String serviceName, String description, String url, String businessName)
            throws RegistrationException, ServiceAlreadyExistsException, DiscoveryException, BusinessNotFoundException, RemoteException;

    /**
     * Get service WSDL url.
     *
     * @param businessName Name of business entity.
     * @param serviceName Name of service entity.
     * @return service url..
     * @throws DiscoveryException if got an error from jUDDI.
     * @throws ServiceNotFoundException if service did not find.
     * @throws BusinessNotFoundException if business did not find.
     * @throws RemoteException if got an error from jUDDI.
     */
    String getServiceUrl(String serviceName, String businessName)
            throws DiscoveryException, ServiceNotFoundException, BusinessNotFoundException, RemoteException;
}
