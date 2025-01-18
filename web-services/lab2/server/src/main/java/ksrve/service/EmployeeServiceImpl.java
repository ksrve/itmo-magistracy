package ksrve.service;

import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import ksrve.model.dto.EmployeeDTO;
import ksrve.model.dto.EmployeeUpdateDTO;
import ksrve.model.dto.FilterDTO;
import ksrve.model.entity.Employee;
import ksrve.repository.EmployeeRepository;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of Employee service.
 */
@WebService(serviceName = "EmployeeService")
@NoArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);

    @Inject
    private EmployeeRepository employeeRepository;

    /**
     * Retrieves a list of employees based on the specified filters.
     *
     * @param filter A list of filters to apply to the employee search.
     * @return A list of EmployeeDTO objects that match the provided filters.
     */
    @Override
    @WebMethod
    public List<EmployeeDTO> find(@WebParam(name = "filter") List<FilterDTO> filter){
        return employeeRepository.read(filter).stream().map(Employee::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Creates a new employee with the provided data.
     *
     * @param employeeDTO The data transfer object containing information about the employee to be created.
     * @return The ID of the created employee.
     */
    @Override
    @WebMethod
    public Integer create(@WebParam(name = "data") EmployeeDTO employeeDTO) {
        return employeeRepository.create(EmployeeDTO.mapFromDTO(employeeDTO));
    }

    /**
     * Deletes an existing employee by their ID.
     *
     * @param id The ID of the employee to be deleted.
     * @return The ID of the deleted employee.
     */
    @Override
    @WebMethod
    public Integer delete(@WebParam(name = "id") Integer id) {
        return employeeRepository.delete(id);
    }

    /**
     * Updates the details of an existing employee.
     *
     * @param id         The ID of the employee to be updated.
     * @param employeeDTO The data transfer object containing the updated information of the employee.
     * @return The ID of the updated employee.
     */
    @Override
    @WebMethod
    public Integer update(@WebParam(name = "id") Integer id, @WebParam(name = "value") EmployeeUpdateDTO employeeDTO) {
        Employee employee = employeeRepository.readById(id);
        if (employee != null) {
            employeeDTO.updateEntity(employee);
            return employeeRepository.update(employee);
        }
        return -1;
    }

}
