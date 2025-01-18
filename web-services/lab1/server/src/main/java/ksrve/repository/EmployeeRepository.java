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
    List<Employee> find(final List<FilterDTO> filters);
}
