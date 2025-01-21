package ksrve;

import com.beust.jcommander.JCommander;
import ksrve.executors.common.Args;
import ksrve.executors.common.CommandExecutor;
import ksrve.executors.delete.DeleteCommandExecutor;
import ksrve.executors.find.FindCommandExecutor;
import ksrve.executors.insert.InsertCommandExecutor;
import ksrve.executors.update.UpdateCommandExecutor;
import ksrve.proxy.EmployeeApiClient;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

/**
 * The Main class is the entry point of the client application. It parses command-line arguments
 * to determine which operation (find, create, delete, update) to execute on the EmployeeService.
 * It utilizes the JCommander library for command-line argument parsing and Log4j for logging.
 */
@NoArgsConstructor
public final class Main {

    /**
     * The logger.
     */
    private static final Logger logger = LogManager.getLogger(Main.class);

    /**
     * The main method that initializes the application, parses command-line arguments,
     * and executes the appropriate command based on the provided arguments.
     *
     * @param argv Command-line arguments passed to the program.
     */
    public static void main(String[] argv) {
        Args jArgs = new Args();
        JCommander jCommander = JCommander.newBuilder().programName("client.jar").addObject(jArgs).build();

        try {
            // Parse arguments
            jCommander.parse(argv);
        } catch (Exception ex) {
            // Handle parsing exception (could be improved by logging the error)
        }

        if (jArgs.getHelp()) {
            jCommander.usage();
            return; // Exit if help is requested
        }

        CommandExecutor commandExecutor;
        String command = jArgs.getCommand();

        if (command == null) {
            System.out.println("Command to execute is null");
            jCommander.usage();
            return; // Exit if no command
        }

        // Determine the command to execute based on user input
        switch (command) {
            case "find":
                commandExecutor = new FindCommandExecutor();
                break;
            case "create":
                commandExecutor = new InsertCommandExecutor();
                break;
            case "delete":
                commandExecutor = new DeleteCommandExecutor();
                break;
            case "update":
                commandExecutor = new UpdateCommandExecutor();
                break;
            default:
                System.out.println("Unknown command: " + jArgs.getCommand());
                return; // Exit if command is unknown
        }

        // Try to execute the command
        try {
            commandExecutor.parseArgs(argv);

            // Construct the service endpoint URL based on user arguments
            URL endpoint;
            if (jArgs.getStandalone()){
                endpoint = new URL(String.format("http://%s:%s", jArgs.getHost(), jArgs.getPort()));
            } else {
                endpoint = new URL(String.format("http://%s:%s/server", jArgs.getHost(), jArgs.getPort()));
            }
            EmployeeApiClient client = new EmployeeApiClient(endpoint, jArgs.getUsername(), jArgs.getPassword());
            logger.info("Initialized employee api client to {}", client.getBaseUri());
            commandExecutor.run(client);
        } catch (Exception e) {
            logger.error(e.getMessage(), e); // Print error messages
        }
    }
}