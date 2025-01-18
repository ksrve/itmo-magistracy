package ksrve.executors.delete;

import com.beust.jcommander.JCommander;
import ksrve.executors.common.CommandExecutor;
import ksrve.proxy.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code DeleteCommandExecutor} class is responsible for executing the
 * delete command for an employee record. It implements the {@link CommandExecutor}
 * interface, enabling it to parse command-line arguments and perform the delete
 * operation through the EmployeeService.
 *
 * <p>
 * This class utilizes JCommander for command-line argument parsing, particularly
 * to configure and handle a mandatory employee identifier that specifies which employee
 * record should be deleted.
 * </p>
 *
 * <p>
 * The class maintains a reference to {@link DeleteArgs}, which holds the command-line
 * arguments and provides necessary accessors. It also employs Apache Log4j for logging
 * operations, allowing for informative message outputs during execution.
 * </p>
 *
 * @see CommandExecutor
 * @see DeleteArgs
 */
public class DeleteCommandExecutor implements CommandExecutor {

    private final DeleteArgs args;
    private static final Logger logger = LogManager.getLogger(DeleteCommandExecutor.class);

    /**
     * Constructs a {@code DeleteCommandExecutor} and initializes the
     * command-line arguments holder {@code DeleteArgs}.
     */
    public DeleteCommandExecutor() {
        this.args = new DeleteArgs();
    }

    /**
     * Parses the command-line arguments using JCommander.
     *
     * @param argv an array of strings representing command-line arguments
     * @throws com.beust.jcommander.ParameterException if the parsing fails
     */
    @Override
    public void parseArgs(String[] argv) {
        JCommander jCommander = JCommander.newBuilder()
                .addObject(args)
                .build();
        jCommander.parse(argv);
    }

    /**
     * Executes the delete operation by interacting with the provided
     * {@link EmployeeService}. It retrieves the employee service implementation
     * and attempts to delete the employee with the identifier specified in
     * the command-line arguments.
     *
     * @param service the {@link EmployeeService} instance used to communicate
     *                with the employee management system
     */
    @Override
    public void run(EmployeeService service) {
        var employeeService = service.getEmployeeServiceImplPort();
        try {
            Integer result = employeeService.delete(args.getId());
            if (result == -1) {
                logger.error("Error occurred while deleting employee with id {}", args.getId());
            } else if (result == -2) {
                logger.error("There is no employee with id {}", args.getId());
            } else {
                logger.info("Successfully deleted employee with id {}", args.getId());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}