package ksrve.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import ksrve.model.dto.EmployeeDTO;
import ksrve.model.dto.FilterDTO;

import java.util.List;

/**
 * Service Interface for managing Employee-related operations.
 * This interface defines methods for creating, updating, deleting,
 * and retrieving employee data.
 */
@WebService(serviceName = "EmployeeService", targetNamespace = "http://service.ksrve")
public interface EmployeeService {

    /**
     * Retrieves a list of employees based on the specified filters.
     *
     * @param filter A list of filters to apply to the employee search.
     * @return A list of EmployeeDTO objects that match the provided filters.
     */
    @WebMethod
    List<EmployeeDTO> find(@WebParam(name = "filter") List<FilterDTO> filter);

}
