package ksrve.utils;

import ksrve.proxy.model.dto.EmployeeDTO;

import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The {@code EntityPrinter} class provides functionality for printing
 * employee data in a formatted manner.
 *
 * <p>
 * This class contains a method to output a list of {@link EmployeeDTO}
 * objects to the console in a tabular format. The printed information
 * includes the employee's ID, first name, last name, email, hire date,
 * and department ID. If no employees are found, a corresponding message
 * is displayed.
 * </p>
 *
 * <p>
 * The hire date is converted from {@link XMLGregorianCalendar} to a string
 * format (yyyy-MM-dd) for readability.
 * </p>
 */
public class EntityPrinter {

    /**
     * Prints the details of the provided list of employees.
     *
     * <p>
     * If the input list is null or empty, a message indicating that no
     * employees were found is printed. Otherwise, the method outputs
     * the header row followed by the details of each employee in a
     * formatted manner.
     * </p>
     *
     * @param employees a {@link List} of {@link EmployeeDTO} objects to be printed.
     */
    public void printData(List<EmployeeDTO> employees) {
        if (employees == null || employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.printf("%-5s %-10s %-10s %-30s %-15s %-10s %n", "Id", "First Name", "Last Name", "Email", "Hire Date", "Department ID");
        System.out.println("--------------------------------------------------------------------------------------------------------------");

        for (EmployeeDTO employee : employees) {
            System.out.printf("%-5s %-10s %-10s %-30s %-15s %-10s %n",
                    employee.getId(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getEmail(),
                    employee.getHireDate(),
                    employee.getDepartment());
        }
    }

    /**
     * Converts an {@link XMLGregorianCalendar} object to a string representation in the format yyyy-MM-dd.
     *
     * <p>
     * If the provided {@link XMLGregorianCalendar} object is null, "N/A" will be returned.
     * </p>
     *
     * @param xmlGregorianCalendar the {@link XMLGregorianCalendar} instance to be converted.
     * @return a {@link String} representing the date in the format yyyy-MM-dd, or "N/A" if null.
     */
    private String convertXMLGregorianCalendarToString(XMLGregorianCalendar xmlGregorianCalendar) {
        if (xmlGregorianCalendar == null) {
            return "N/A";
        }

        Date date = xmlGregorianCalendar.toGregorianCalendar().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}