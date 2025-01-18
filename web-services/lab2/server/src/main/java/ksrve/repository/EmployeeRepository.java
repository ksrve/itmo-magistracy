package ksrve.repository;

import jakarta.data.repository.Repository;
import ksrve.model.dto.FilterDTO;
import ksrve.model.entity.Employee;

import java.util.List;

/**
 * Repository interface for managing {@link Employee} entities.
 */
@Repository
public interface EmployeeRepository {

    /**
     * Reads a list of employees that match the given filters.
     *
     * @param filters A list of {@link FilterDTO} to filter employees by specific criteria.
     * @return A list of {@link Employee} entities that match the specified filters.
     */
    List<Employee> read(final List<FilterDTO> filters);

    /**
     * Creates a new employee entity in the database.
     *
     * @param employee The {@link Employee} entity to be created.
     * @return The ID of the newly created employee.
     */
    Integer create(final Employee employee);

    /**
     * Deletes an employee entity from the database by their unique ID.
     *
     * @param id The unique identifier of the employee to be deleted.
     * @return An integer indicating the result of the deletion operation (0 for success).
     */
    Integer delete(final Integer id);


    /**
     * Reads an employee by their unique ID.
     *
     * @param id The unique identifier of the employee to be retrieved.
     * @return An {@link Employee} entity associated with the given ID.
     */
    Employee readById(final Integer id);

    /**
     * Updates an existing employee entity in the database.
     *
     * @param employee The {@link Employee} entity with updated information.
     * @return The updated {@link Employee} entity.
     */
    Integer update(final Employee employee);
}
