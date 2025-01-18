package ksrve.repository;

import jakarta.data.repository.Repository;
import ksrve.exceptions.EntityAlreadyExistsException;
import ksrve.exceptions.EntityNotFoundException;
import ksrve.exceptions.FieldRequiredException;
import ksrve.exceptions.ValidationException;
import ksrve.model.dto.FilterDTO;
import ksrve.model.entity.Employee;

import java.util.List;

/**
 * Repository interface for managing {@link Employee} entities.
 */
@Repository
public interface EmployeeRepository {

    /**
     * Retrieves a list of employees that match the given filters.
     *
     * @param filters A list of filters to apply for retrieving employees.
     * @return A list of {@link Employee} objects that match the specified filters.
     * @throws EntityNotFoundException If no employees are found that match the filters.
     */
    List<Employee> read(final List<FilterDTO> filters)
            throws EntityNotFoundException;

    /**
     * Creates a new employee entity in the repository.
     *
     * @param employee The {@link Employee} object to be created.
     * @return The ID of the created employee.
     * @throws ValidationException If the employee entity does not satisfy the validation constraints.
     * @throws FieldRequiredException If any required fields are missing in the employee data.
     * @throws EntityAlreadyExistsException If an employee with the same identifier already exists in the repository.
     */
    Integer create(final Employee employee)
            throws ValidationException, FieldRequiredException, EntityAlreadyExistsException;

    /**
     * Deletes an employee entity based on the provided ID.
     *
     * @param id The ID of the employee to be deleted.
     * @return The number of entities deleted (1 if successful, 0 if no entity found).
     * @throws EntityNotFoundException If no employee with the specified ID exists.
     */
    Integer delete(final Integer id)
            throws EntityNotFoundException;

    /**
     * Retrieves an employee entity by its ID.
     *
     * @param id The ID of the employee to be retrieved.
     * @return The {@link Employee} object corresponding to the specified ID.
     * @throws EntityNotFoundException If no employee with the specified ID exists.
     */
    Employee readById(final Integer id)
            throws EntityNotFoundException;

    /**
     * Updates an existing employee entity in the repository.
     *
     * @param employee The {@link Employee} object containing updated data.
     * @return The updated {@link Employee} object.
     * @throws EntityNotFoundException If no employee with the specified ID exists.
     * @throws ValidationException If the updated employee entity does not satisfy the validation constraints.
     * @throws FieldRequiredException If any required fields are missing in the employee data.
     * @throws EntityAlreadyExistsException If an employee with the same identifier exists in the repository.
     */
    Employee update(final Employee employee)
            throws EntityNotFoundException, ValidationException,
            FieldRequiredException, EntityAlreadyExistsException;
}