package ksrve;

import com.beust.jcommander.JCommander;
import ksrve.executors.common.Args;
import ksrve.executors.common.CommandExecutor;
import ksrve.executors.delete.DeleteCommandExecutor;
import ksrve.executors.find.FindCommandExecutor;
import ksrve.executors.insert.InsertCommandExecutor;
import ksrve.executors.update.UpdateCommandExecutor;
import ksrve.proxy.EmployeeService;
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

    /** Logger for logging application events and errors */
    private static Logger logger = LogManager.getLogger(Main.class);

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
                return;
        }

//        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
//        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
//        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
//        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
//        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "999999");

        try {
            commandExecutor.parseArgs(argv);

            // Create client
            JuddiClientImpl client = new JuddiClientImpl(
                    jArgs.getUser(),
                    jArgs.getPassword()
            );

            // Create provider
            UDDIProviderImpl provider = new UDDIProviderImpl(client);

            // Find service
            String serviceUrl = provider.getServiceUrl(EmployeeService.class.getSimpleName(), null);
            URL endpoint = new URL(serviceUrl);
            logger.info("Connecting to service on endpoint {}", endpoint);
            EmployeeService service = new EmployeeService(endpoint);
            commandExecutor.run(service);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}