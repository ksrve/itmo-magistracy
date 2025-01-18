package ksrve;

import com.beust.jcommander.JCommander;
import jakarta.xml.ws.Endpoint;
import ksrve.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;


/**
 * Command Line Interface for the Standalone application.
 * <p>
 * The {@code Main} class initializes and starts a Soap server.
 * It parses command-line arguments using JCommander.
 * </p>
 */
public class Main {

    /** Logger for logging application events and errors */
    private static Logger logger = LogManager.getLogger(Main.class);

    /**
     * Default empty constructor to prevent instantiation.
     * This class is designed to be used statically.
     */
    private Main() {
        // empty constructor
    }

    /**
     * Initializes and starts the Soap server.
     *
     * @param args The command-line arguments containing server and
     *             database configuration options.
     * @throws NumberFormatException if the port cannot be parsed as an Integer.
     */
    public static void initialize_sever(final Args args){
        Weld weld = new Weld();
        try {
            WeldContainer container = weld.initialize();

            // Setting properties from arguments
            if (args.getHost() != null) {
                System.setProperty("hostname", args.getHost());
            }
            if (args.getPort() != null) {
                System.setProperty("port", args.getPort());
            }
            if (args.getDbHost() != null) {
                System.setProperty("db.host", args.getDbHost());
            }
            if (args.getDbPort() != null) {
                System.setProperty("db.port", args.getDbPort());
            }
            if (args.getDbUsername() != null) {
                System.setProperty("db.user", args.getDbUsername());
            }
            if (args.getDbPassword() != null) {
                System.setProperty("db.password", args.getDbPassword());
            }
            if (args.getDbName() != null) {
                System.setProperty("db.name", args.getDbName());
            }

            EmployeeService service = container.select(EmployeeService.class).get();
            String url = String.format("http://%s:%s/EmployeeService", args.getHost(), args.getPort());

            Endpoint.publish(url, service);
            logger.info("Service started at {}", url);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * The main method, which serves as the entry point for the application.
     * <p>
     * This method parses command-line arguments, initializes the server,
     * and logs any errors encountered during these processes.
     * </p>
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Parse command line arguments
        Args jArgs = new Args();
        JCommander jCommander = JCommander.newBuilder().programName("server.jar").addObject(jArgs).build();
        try {
            jCommander.parse(args);
        } catch (Exception e) {
            logger.error(e.getMessage());
            jCommander.usage();
            return;
        }
        // Check if print should be printed
        if (jArgs.getHelp()) {
            jCommander.usage();
            return;
        }

        // Publish server endpoint
        initialize_sever(jArgs);
    }
}
