package ksrve.service;

import java.util.List;

import ksrve.exceptions.EntityAlreadyExistsException;
import ksrve.exceptions.EntityNotFoundException;
import ksrve.exceptions.FieldRequiredException;
import ksrve.exceptions.ValidationException;
import ksrve.model.dto.EmployeeDTO;
import ksrve.model.dto.EmployeeUpdateDTO;
import ksrve.model.dto.FilterDTO;

/**
 * Service Interface for managing Employee-related operations.
 * This interface defines methods for creating, updating, deleting,
 * and retrieving employee data.
 */
public interface EmployeeService {

    /**
     * Retrieves a list of employees based on the specified filters.
     *
     * @param filter A list of filters to apply to the employee search.
     * @throws EntityNotFoundException if no employee with the specified ID is found.
     * @return A list of EmployeeDTO objects that match the provided filters.
     */
    List<EmployeeDTO> find(List<FilterDTO> filter)
            throws EntityNotFoundException;

    /**
     * Creates a new employee with the provided data.
     *
     * @param employeeDTO The data transfer object containing information about the employee to be created.
     * @throws ValidationException If there are validation constraints that the employee entity does not satisfy.
     * @throws FieldRequiredException If any required fields are missing in the employee data.
     * @throws EntityAlreadyExistsException If an employee with the same unique identifier already exists.
     * @return The ID of the created employee.
     */
    Integer create(EmployeeDTO employeeDTO)
            throws ValidationException, FieldRequiredException, EntityAlreadyExistsException;

    /**
     * Updates the details of an existing employee.
     *
     * @param id         The ID of the employee to be updated.
     * @param employeeDTO The data transfer object containing the updated information of the employee.
     * @throws EntityNotFoundException if no employee with the specified ID is found.
     * @throws EntityAlreadyExistsException If an employee with the same unique identifier already exists.
     * @throws ValidationException If there are validation constraints that the employee entity does not satisfy.
     * @throws FieldRequiredException If any required fields are missing in the employee data.
     * @return Updated employee.
     */
    EmployeeDTO update(
            Integer id,
            EmployeeUpdateDTO employeeDTO)
            throws EntityNotFoundException, ValidationException, FieldRequiredException, EntityAlreadyExistsException;

    /**
     * Deletes an existing employee by their ID.
     *
     * @param id The ID of the employee to be deleted.
     * @throws EntityNotFoundException if no employee with the specified ID is found.
     * @return The ID of the deleted employee.
     */
    Integer delete(Integer id)
            throws EntityNotFoundException;
}