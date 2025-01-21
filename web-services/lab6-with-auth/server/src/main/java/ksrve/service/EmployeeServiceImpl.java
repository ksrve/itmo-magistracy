package ksrve.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import ksrve.exceptions.EntityAlreadyExistsException;
import ksrve.exceptions.EntityNotFoundException;
import ksrve.exceptions.FieldRequiredException;
import ksrve.exceptions.ValidationException;
import ksrve.model.dto.EmployeeDTO;
import ksrve.model.dto.EmployeeUpdateDTO;
import ksrve.model.dto.FilterDTO;
import ksrve.model.entity.Employee;
import ksrve.repository.EmployeeRepository;

/**
 * Implementation of Employee service.
 */
@ApplicationScoped
public class EmployeeServiceImpl implements EmployeeService {

    @Inject
    private EmployeeRepository employeeRepository;

    /**
     * Retrieves a list of employees based on the specified filters.
     *
     * @param filter A list of filters to apply to the employee search.
     * @throws EntityNotFoundException if no employee with the specified ID is found.
     * @return A list of EmployeeDTO objects that match the provided filters.
     */
    @Override
    public List<EmployeeDTO> find(List<FilterDTO> filter) throws EntityNotFoundException {
        return employeeRepository.read(filter).stream().map(Employee::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Creates a new employee with the provided data.
     *
     * @param employeeDTO The data transfer object containing information about the employee to be created.
     * @throws ValidationException If there are validation constraints that the employee entity does not satisfy.
     * @throws FieldRequiredException If any required fields are missing in the employee data.
     * @throws EntityAlreadyExistsException If an employee with the same unique identifier already exists.
     * @return The ID of the created employee.
     */
    @Override
    public Integer create(EmployeeDTO employeeDTO)
            throws ValidationException, FieldRequiredException, EntityAlreadyExistsException {
        return employeeRepository.create(EmployeeDTO.mapFromDTO(employeeDTO));
    }

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
    @Override
    public EmployeeDTO update(
            Integer id,
            EmployeeUpdateDTO employeeDTO)
            throws EntityNotFoundException, ValidationException,
            FieldRequiredException, EntityAlreadyExistsException {
        Employee employee = employeeRepository.readById(id);
        employeeDTO.updateEntity(employee);
        return employeeRepository.update(employee).mapToDTO();
    }

    /**
     * Deletes an existing employee by their ID.
     *
     * @param id The ID of the employee to be deleted.
     * @throws EntityNotFoundException if no employee with the specified ID is found.
     * @return The ID of the deleted employee.
     */
    @Override
    public Integer delete(Integer id) throws EntityNotFoundException {
        Employee employee = employeeRepository.readById(id);
        return employeeRepository.delete(id);
    }
}
