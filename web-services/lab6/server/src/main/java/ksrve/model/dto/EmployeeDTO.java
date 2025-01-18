package ksrve.model.dto;

import ksrve.model.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Data Transfer Object (DTO) representing an Employee.
 * This class serves as a container for employee data, facilitating the transfer
 * of employee-related information between layers of an application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDTO implements Serializable {

    /** Unique identifier of the employee. */
    private Integer id;

    /** First name of the employee. */
    private String firstName;

    /** Last name of the employee. */
    private String lastName;

    /** Email address of the employee. */
    private String email;

    /** Department where the employee works. */
    private String department;

    /** Date when the employee was hired. */
    private Date hireDate;

    /**
     * Constructs an EmployeeDTO object from an Employee entity.
     * This constructor copies the fields from the specified Employee
     * to the new EmployeeDTO instance.
     *
     * @param employee the Employee entity to convert
     */
    public EmployeeDTO(final Employee employee) {
        this.id = employee.getId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.email = employee.getEmail();
        this.hireDate = employee.getHireDate();
        this.department = employee.getDepartment();
    }

    /**
     * Maps the contents of an EmployeeDTO to a new Employee entity.
     * This method takes the data from the DTO and creates a corresponding
     * Employee object, allowing for the conversion of data between these
     * two representations.
     *
     * @param dto the EmployeeDTO instance containing employee data
     * @return a new Employee instance populated with the DTO's data
     */
    public static Employee mapFromDTO(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setHireDate(dto.getHireDate());
        return employee;
    }
}