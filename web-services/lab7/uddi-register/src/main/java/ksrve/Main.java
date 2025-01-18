package ksrve;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import ksrve.common.ArgsValidator;
import ksrve.util.EntityPrinter;
import lombok.NoArgsConstructor;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.juddi.v3.client.transport.TransportException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Objects;

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
    public static void main(String[] argv) throws ConfigurationException, TransportException {
        Args jArgs = new Args();
        JCommander jCommander = JCommander.newBuilder().programName("discovery.jar").addObject(jArgs).build();
        try {
            jCommander.parse(argv);
        }catch (Exception e){
            logger.error(e.getMessage());
            jCommander.usage();
            return;
        }

        if (jArgs.getHelp()) {
            jCommander.usage();
            return;
        }

        // Validate data parameter
        ArgsValidator validator = new ArgsValidator();
        HashMap<String, String> dataMap = new HashMap<>();
        try {
            dataMap = validator.validate(jArgs);
        } catch (ParameterException e) {
            logger.error(e.getMessage());
            return;
        }

        try {
            // Create client
            JuddiClientImpl client = new JuddiClientImpl(
                    jArgs.getUser(),
                    jArgs.getPassword()
            );

            // Create provider
            UDDIProviderImpl provider = new UDDIProviderImpl(client);

            // Create printer
            EntityPrinter entityPrinter = new EntityPrinter();

            switch (jArgs.getCommand()) {
                case "register":
                    if (Objects.equals(jArgs.getType(), "service")) {
                        if (dataMap.get("serviceUrl") != null) {
                            provider.registerService(
                                    dataMap.get("serviceName"),
                                    dataMap.get("serviceDescription"),
                                    dataMap.get("serviceUrl"),
                                    dataMap.get("businessName")
                            );
                        } else {
                            provider.registerService(
                                    dataMap.get("serviceName"),
                                    dataMap.get("serviceDescription"),
                                    dataMap.get("businessName")
                            );
                        }
                    } else if (Objects.equals(jArgs.getType(), "business")) {
                        provider.registerBusiness(
                                dataMap.get("businessName"),
                                dataMap.get("businessDescription")
                        );
                    }
                    break;
                case "discover":
                    if (Objects.equals(jArgs.getType(), "service")) {
                        entityPrinter.printServiceInfos(
                                provider.findServices(
                                    dataMap.get("serviceName"),
                                    dataMap.get("businessName")
                                )
                        );
                    } else if (Objects.equals(jArgs.getType(), "business")) {
                        entityPrinter.printBusinessInfos(
                                provider.findBusinesses(
                                        dataMap.get("businessName")
                                )
                        );
                    }
                    break;
                default:
                    System.out.println("Unknown command: " + jArgs.getCommand());
                    return;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }
}