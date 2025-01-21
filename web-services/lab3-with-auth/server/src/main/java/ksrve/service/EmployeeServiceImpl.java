package ksrve.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import ksrve.exceptions.*;
import ksrve.model.dto.EmployeeDTO;
import ksrve.model.dto.EmployeeUpdateDTO;
import ksrve.model.dto.FilterDTO;
import ksrve.model.entity.Employee;
import ksrve.repository.EmployeeRepository;

import lombok.NoArgsConstructor;

/**
 * Implementation of Employee service.
 */
@WebService(serviceName = "EmployeeService")
@NoArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    @Inject
    private EmployeeRepository employeeRepository;

    @Resource
    private WebServiceContext context;

    private boolean isValidUser(String username, String password) {
        // Hardcoded
        return "admin".equals(username) && "password".equals(password);
    }

    private void authenticate() throws UnauthorizedAccessException {
        MessageContext mc = this.context.getMessageContext();
        Map<String, List<String>>http_headers = (Map<String, List<String>>) mc.get(MessageContext.HTTP_REQUEST_HEADERS);
        List<String> users = http_headers.get("Username");
        List<String> passwords = http_headers.get("Password");

        if (users == null || passwords == null ||
                !isValidUser(users.getFirst(), passwords.getFirst())) {
            throw new UnauthorizedAccessException("Unauthorized access: Invalid credentials.");
        }
    }

    /**
     * Retrieves a list of employees based on the specified filters.
     *
     * @param filter A list of filters to apply to the employee search.
     * @throws EntityNotFoundException if no employee with the specified ID is found.
     * @return A list of EmployeeDTO objects that match the provided filters.
     */
    @Override
    @WebMethod
    public List<EmployeeDTO> find(@WebParam(name = "filter") List<FilterDTO> filter)
            throws EntityNotFoundException, UnauthorizedAccessException {
        authenticate();
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
    @WebMethod
    public Integer create(@WebParam(name = "data") EmployeeDTO employeeDTO)
            throws ValidationException, FieldRequiredException, EntityAlreadyExistsException, UnauthorizedAccessException {
        authenticate();
        return employeeRepository.create(EmployeeDTO.mapFromDTO(employeeDTO));
    }

    /**
     * Deletes an existing employee by their ID.
     *
     * @param id The ID of the employee to be deleted.
     * @throws EntityNotFoundException if no employee with the specified ID is found.
     * @return The ID of the deleted employee.
     */
    @Override
    @WebMethod
    public Integer delete(@WebParam(name = "id") Integer id)
            throws EntityNotFoundException, UnauthorizedAccessException {
        authenticate();
        Employee employee = employeeRepository.readById(id);
        return employeeRepository.delete(id);
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
    @WebMethod
    public EmployeeDTO update(
            @WebParam(name = "id") Integer id,
            @WebParam(name = "value") EmployeeUpdateDTO employeeDTO)
            throws EntityNotFoundException, FieldRequiredException,
            ValidationException, EntityAlreadyExistsException, UnauthorizedAccessException {
        authenticate();
        Employee employee = employeeRepository.readById(id);
        employeeDTO.updateEntity(employee);
        return employeeRepository.update(employee).mapToDTO();
    }

}
