package ksrve.service;

import java.util.List;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import ksrve.exceptions.*;
import ksrve.model.dto.EmployeeDTO;
import ksrve.model.dto.EmployeeUpdateDTO;
import ksrve.model.dto.FilterDTO;

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
     * @throws EntityNotFoundException if no employee with the specified ID is found.
     * @return A list of EmployeeDTO objects that match the provided filters.
     */
    @WebMethod
    List<EmployeeDTO> find(@WebParam(name = "filter") List<FilterDTO> filter)
            throws EntityNotFoundException, UnauthorizedAccessException;

    /**
     * Creates a new employee with the provided data.
     *
     * @param employeeDTO The data transfer object containing information about the employee to be created.
     * @throws ValidationException If there are validation constraints that the employee entity does not satisfy.
     * @throws FieldRequiredException If any required fields are missing in the employee data.
     * @throws EntityAlreadyExistsException If an employee with the same unique identifier already exists.
     * @return The ID of the created employee.
     */
    @WebMethod
    Integer create(@WebParam(name = "data") EmployeeDTO employeeDTO)
            throws ValidationException, FieldRequiredException, EntityAlreadyExistsException, UnauthorizedAccessException;

    /**
     * Updates the details of an existing employee.
     *
     * @param id         The ID of the employee to be updated.
     * @param employeeDTO The data transfer object containing the updated information of the employee.
     * @throws EntityNotFoundException if no employee with the specified ID is found.
     * @throws EntityAlreadyExistsException If an employee with the same unique identifier already exists.
     * @throws ValidationException If there are validation constraints that the employee entity does not satisfy.
     * @throws FieldRequiredException If any required fields are missing in the employee data.
     * @return The ID of the updated employee.
     */
    @WebMethod
    EmployeeDTO update(
            @WebParam(name = "id") Integer id,
            @WebParam(name = "data") EmployeeUpdateDTO employeeDTO)
            throws EntityNotFoundException, ValidationException, FieldRequiredException, EntityAlreadyExistsException, UnauthorizedAccessException;

    /**
     * Deletes an existing employee by their ID.
     *
     * @param id The ID of the employee to be deleted.
     * @throws EntityNotFoundException if no employee with the specified ID is found.
     * @return The ID of the deleted employee.
     */
    @WebMethod
    Integer delete(@WebParam(name = "id") Integer id)
            throws EntityNotFoundException, UnauthorizedAccessException;
}