package ksrve.proxy.model.dto;

import ksrve.proxy.model.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Optional;

/**
 * Data Transfer Object for updating employee information.
 * This class contains fields corresponding to the attributes of an Employee
 * and provides a method to update an existing Employee entity using this DTO.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateDTO {

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
     * Updates the provided Employee entity with non-null fields
     * from the EmployeeUpdateDTO instance.
     *
     * @param employee the Employee entity to be updated
     */
    public void updateEntity(final Employee employee) {
        // Use Optional to safely set the first name if it's not null
        Optional.ofNullable(firstName).ifPresent(employee::setFirstName);
        // Use Optional to safely set the last name if it's not null
        Optional.ofNullable(lastName).ifPresent(employee::setLastName);
        // Use Optional to safely set the email if it's not null
        Optional.ofNullable(email).ifPresent(employee::setEmail);
        // Use Optional to safely set the department if it's not null
        Optional.ofNullable(department).ifPresent(employee::setDepartment);
        // Use Optional to safely set the hire date if it's not null
        Optional.ofNullable(hireDate).ifPresent(employee::setHireDate);
    }
}