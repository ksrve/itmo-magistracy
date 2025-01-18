package ksrve.service;

import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import ksrve.model.dto.EmployeeDTO;
import ksrve.model.dto.FilterDTO;
import ksrve.model.entity.Employee;
import ksrve.repository.EmployeeRepository;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of Employee service.
 */
@WebService(serviceName = "EmployeeService", targetNamespace = "http://service.ksrve")
@NoArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

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
        return employeeRepository.find(filter).stream().map(Employee::mapToDTO).collect(Collectors.toList());
    }

}
