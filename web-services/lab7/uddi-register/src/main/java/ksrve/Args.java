package ksrve;

import com.beust.jcommander.Parameter;
import lombok.Data;

import java.util.List;

/**
 * Represents the command-line arguments utilized for server configuration
 * and database connection settings.
 * <p>
 * The {@code Args} class encapsulates various parameters that can be
 * passed to the application via the command line. It utilizes the
 * JCommander library to parse these parameters, providing a simple
 * interface for configuring server and database connection details.
 * </p>
 *
 * <p>
 * Default values are provided for each parameter, ensuring that the
 * application can run with sensible defaults if no arguments are specified.
 * </p>
 */
@Data
public class Args {

    /**
     * Juddi username parameter.
     */
    @Parameter(
            names = "--user",
            description = "JUDDI user")
    private String user = "uddiadmin";

    /**
     * Juddi password parameter.
     */
    @Parameter(
            names = "--port",
            description = "JUDDI password")
    private String password = "da_password1";

    /**
     * The command parameter, which specifies the command that
     * the application will execute. This parameter is required.
     */
    @Parameter(
            names = "--command",
            description = "Command to execute",
            required = true)
    private String command;

    /**
     * The type parameter, which specifies the type of entity
     * to work with ( business or service ). This parameter is required.
     */
    @Parameter(
            names = "--type",
            description = "Type of UDDI entity",
            required = true)
    private String type;

    @Parameter(
            names = "--data",
            description = "Data for discovery or registration of services of business. " +
                    "Example: --data 'name=EmployeeService, description=Employee Service Description, " +
                    "url=http://localhost:8080/EmployeeService?wsdl'"
            )
    private List<String> data;

    /**
     * The help parameter, which, when set, will print usage information
     * for the command-line application. Defaults to false.
     */
    @Parameter(
            names = "--help",
            description = "Print usage",
            help = true)
    private Boolean help = false;
}
