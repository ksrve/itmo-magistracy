package ksrve.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import ksrve.model.dto.EmployeeDTO;
import ksrve.model.dto.EmployeeUpdateDTO;
import ksrve.model.dto.FilterDTO;

import java.util.List;

/**
 * Service Interface for managing Employee-related operations.
 * This interface defines methods for creating, updating, deleting,
 * and retrieving employee data.
 */
@WebService(
        serviceName = "EmployeeService",
        targetNamespace = "http://service.ksrve"
)
public interface EmployeeService {

    /**
     * Retrieves a list of employees based on the specified filters.
     *
     * @param filter A list of filters to apply to the employee search.
     * @return A list of EmployeeDTO objects that match the provided filters.
     */
    @WebMethod
    List<EmployeeDTO> find(@WebParam(name = "filter") List<FilterDTO> filter);

    /**
     * Creates a new employee with the provided data.
     *
     * @param employeeDTO The data transfer object containing information about the employee to be created.
     * @return The ID of the created employee.
     */
    @WebMethod
    Integer create(@WebParam(name = "data") EmployeeDTO employeeDTO);

    /**
     * Updates the details of an existing employee.
     *
     * @param id         The ID of the employee to be updated.
     * @param employeeDTO The data transfer object containing the updated information of the employee.
     * @return The ID of the updated employee.
     */
    @WebMethod
    Integer update(@WebParam(name = "id") Integer id, @WebParam(name = "data") EmployeeUpdateDTO employeeDTO);

    /**
     * Deletes an existing employee by their ID.
     *
     * @param id The ID of the employee to be deleted.
     * @return The ID of the deleted employee.
     */
    @WebMethod
    Integer delete(@WebParam(name = "id") Integer id);
}
