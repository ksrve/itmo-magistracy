package ksrve.executors.update;

import com.beust.jcommander.JCommander;
import ksrve.executors.common.CommandExecutor;
import ksrve.proxy.EmployeeService;
import ksrve.proxy.EmployeeUpdateDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * The {@code UpdateCommandExecutor} class implements the {@link CommandExecutor} interface
 * for executing update operations on employee records. It is responsible for parsing
 * command-line arguments and updating employee information within a given service.
 *
 * <p>
 * This class employs JCommander to process command-line arguments. It specifically
 * handles the parsing of employee data updates and communicates with the underlying
 * employee service to perform the update action.
 * </p>
 */
public class UpdateCommandExecutor implements CommandExecutor {

    private final UpdateArgs args;
    private static final Logger logger = LogManager.getLogger(UpdateCommandExecutor.class);

    /**
     * Constructs an {@code UpdateCommandExecutor} instance, initializing the {@link UpdateArgs}
     * object to hold command-line arguments.
     */
    public UpdateCommandExecutor() {
        this.args = new UpdateArgs();
    }

    /**
     * Parses a list of key-value string entries into an {@link EmployeeUpdateDTO}.
     * Each entry is expected to be in the format 'key=value', where the key corresponds
     * to an employee attribute. Supported attributes include department, firstName, lastName,
     * email, and hireDate.
     *
     * @param entries A list of string entries representing employee data in key-value format.
     * @return An {@link EmployeeUpdateDTO} populated with the parsed employee data.
     */
    public static EmployeeUpdateDTO parseData(List<String> entries) {
        EmployeeUpdateDTO employee = new EmployeeUpdateDTO();

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
                            Date date = dateFormat.parse(value);

                            GregorianCalendar gregorianCalendar = new GregorianCalendar();
                            gregorianCalendar.setTime(date);

                            XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
                            employee.setHireDate(xmlGregorianCalendar);
                            break;
                        default:
                            break;
                    }
                } catch (NumberFormatException e) {
                    logger.error("Error parsing number for key: {} with value: {}", key, value);
                } catch (Exception e) {
                    logger.error("Error processing entry: {} - {}", entry, e.getMessage());
                }
            }
        }
        return employee;
    }

    /**
     * Parses the command-line arguments provided to the executor.
     *
     * @param argv The command-line arguments as an array of strings to parse.
     */
    @Override
    public void parseArgs(String[] argv) {
        JCommander jCommander = JCommander.newBuilder()
                .addObject(args)
                .build();
        jCommander.parse(argv);
    }

    /**
     * Executes the update operation by calling the employee service to update
     * the specified employee. It retrieves updated employee data and handles
     * any exceptions that may arise during the process.
     *
     * @param service The {@link EmployeeService} instance used to interact with
     *                employee records.
     */
    @Override
    public void run(EmployeeService service) {
        var employeeService = service.getEmployeeServiceImplPort();
        try {
            Integer result = employeeService.update(args.getId(), parseData(args.getData()));
            switch (result) {
                case -1:
                    System.out.println("Error occurred while updating employee. Maybe there is no such employee");
                    break;
                default:
                    System.out.println("Successfully updated employee with id " + result);
                    break;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}