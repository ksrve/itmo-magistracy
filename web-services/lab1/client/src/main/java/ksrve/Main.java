package ksrve;

import com.beust.jcommander.JCommander;

import ksrve.executors.common.Args;
import ksrve.executors.common.CommandExecutor;
import ksrve.executors.find.FindCommandExecutor;

import ksrve.proxy.EmployeeService;

import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

/**
 * The Main class is the entry point of the client application. It parses command-line arguments
 * to determine which operation (find) to execute on the EmployeeService.
 * It utilizes the JCommander library for command-line argument parsing and Log4j for logging.
 */
@NoArgsConstructor
public final class Main {

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
            jCommander.parse(argv);
        }catch (Exception ex){

        }

        if (jArgs.getHelp()) {
            jCommander.usage();
            return;
        }

        CommandExecutor commandExecutor;

        String command = jArgs.getCommand();

        if (command == null) {
            System.out.println("Command to execute is null");
            jCommander.usage();
            return;
        }

        switch (command) {
            case "find":
                commandExecutor = new FindCommandExecutor();
                break;
            default:
                System.out.println("Unknown command: " + jArgs.getCommand());
                return;
        }

        try {
            commandExecutor.parseArgs(argv);
            URL endpoint;
            if (jArgs.getStandalone()){
                endpoint = new URL(String.format("http://%s:%s/EmployeeService?wsdl", jArgs.getHost(), jArgs.getPort()));
            }else {
                endpoint = new URL(String.format("http://%s:%s/server/EmployeeService?wsdl", jArgs.getHost(), jArgs.getPort()));
            }
            logger.info("Connecting to {}", endpoint);
            EmployeeService service = new EmployeeService(endpoint);
            logger.info("Connected OK");
            commandExecutor.run(service);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}