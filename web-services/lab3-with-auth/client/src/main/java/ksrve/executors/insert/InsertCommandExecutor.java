package ksrve.executors.insert;

import com.beust.jcommander.JCommander;
import ksrve.executors.common.CommandExecutor;
import ksrve.proxy.EmployeeDTO;
import ksrve.proxy.EmployeeService;
import ksrve.proxy.EmployeeServiceImpl;
import ksrve.utils.ExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * The {@code InsertCommandExecutor} class implements the {@link CommandExecutor}
 * interface to manage the insertion of employee data into a remote service.
 * This class parses employee data from command-line arguments and handles the
 * communication with the {@link EmployeeService}.
 *
 * <p>
 * This class is responsible for converting the entered data into an {@link EmployeeDTO}
 * object, which is then sent to the employee service for insertion. It also
 * employs logging to capture the status of insert operations.
 * </p>
 *
 * <p>
 * The class utilizes {@link JCommander} for command-line argument parsing,
 * and handles exceptions using the {@link ExceptionHandler} utility.
 * </p>
 */
public class InsertCommandExecutor implements CommandExecutor {

    private final InsertArgs args;
    private static final Logger logger = LogManager.getLogger(InsertCommandExecutor.class);

    /**
     * Constructs an instance of {@code InsertCommandExecutor} and initializes
     * the arguments for command parsing.
     */
    public InsertCommandExecutor() {
        this.args = new InsertArgs();
    }

    /**
     * Parses a list of string entries representing employee data and creates
     * an {@link EmployeeDTO} object.
     *
     * <p>
     * Each entry should be in the format {@code key=value}. The valid keys
     * include department, firstName, lastName, email, and hireDate. The method
     * will parse each entry, handle potential parsing exceptions, and set
     * the respective fields in the {@code EmployeeDTO}.
     * </p>
     *
     * @param entries a {@link List} of strings containing employee data entries.
     * @return an {@link EmployeeDTO} object containing the parsed employee data.
     */
    public static EmployeeDTO parseData(List<String> entries) {
        EmployeeDTO employee = new EmployeeDTO();

        for (String entry : entries) {
            String[] keyValue = entry.split("=");

            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                try {
                    switch (key) {
                        case "department":
                            employee.setDepartment(value);
                            break;
                        case "firstName":
                            employee.setFirstName(value);
                            break;
                        case "lastName":
                            employee.setLastName(value);
                            break;
                        case "email":
                            employee.setEmail(value);
                            break;
                        case "hireDate":
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            java.util.Date date = dateFormat.parse(value);

                            GregorianCalendar gregorianCalendar = new GregorianCalendar();
                            gregorianCalendar.setTime(date);

                            XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
                            employee.setHireDate(xmlGregorianCalendar);
                            break;
                        default:
                            // Ignore unknown keys
                            break;
                    }
                } catch (NumberFormatException e) {
                    // Handle exceptions for number parsing
                    System.err.println("Error parsing number for key: " + key + " with value: " + value);
                } catch (Exception e) {
                    // Handle any other exceptions
                    System.err.println("Error processing entry: " + entry + " - " + e.getMessage());
                }
            }
        }
        return employee;
    }

    /**
     * Parses command-line arguments to populate the {@link InsertArgs} instance.
     *
     * @param argv an array of command-line arguments.
     */
    @Override
    public void parseArgs(String[] argv) {
        JCommander jCommander = JCommander.newBuilder()
                .addObject(args)
                .build();
        jCommander.parse(argv);
    }

    /**
     * Executes the insertion of an employee by retrieving the employee service
     * implementation and calling the create method with the parsed employee data.
     *
     * <p>
     * If the insertion is successful, the employee ID is logged. If an error
     * occurs during the process, the error is handled and logged.
     * </p>
     *
     * @param employeeService an instance of {@link EmployeeServiceImpl} to interact with the employee data service.
     */
    @Override
    public void run(EmployeeServiceImpl employeeService) {
        try {
            Integer result = employeeService.create(parseData(args.getData()));
            if (result == -1) {
                logger.error("Error occurred while inserting employee");
            } else {
                logger.info("Successfully inserted employee with id {}", result);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }
}