package ksrve;

import com.beust.jcommander.Parameter;
import lombok.Data;

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
     * Server host parameter.
     * <p>
     * This parameter defines the hostname of the server. The default
     * value is "localhost".
     * </p>
     */
    @Parameter(
            names = "--host",
            description = "Server hostname")
    private String host = "localhost";

    /**
     * Server port parameter.
     * <p>
     * This parameter specifies the port on which the server will listen.
     * The default value is "8080".
     * </p>
     */
    @Parameter(
            names = "--port",
            description = "Server port")
    private String port = "8080";

    /**
     * Database host parameter.
     * <p>
     * This parameter defines the hostname of the database server. The
     * default value is "localhost".
     * </p>
     */
    @Parameter(
            names = {"--dbhost"},
            description = "Database hostname")
    private String dbHost;

    /**
     * Database port parameter.
     * <p>
     * This parameter specifies the port on which the database server
     * listens. The default value is "5432".
     * </p>
     */
    @Parameter(
            names = {"--dbport"},
            description = "Database port")
    private String dbPort;

    /**
     * Database name parameter.
     * <p>
     * This parameter defines the name of the database to connect to.
     * The default value is "db".
     * </p>
     */
    @Parameter(
            names = {"--dbname"},
            description = "Database name")
    private String dbName;

    /**
     * Database username parameter.
     * <p>
     * This parameter specifies the username required to connect to
     * the database. The default value is "admin".
     * </p>
     */
    @Parameter(
            names = {"--dbuser"},
            description = "Database username")
    private String dbUsername;

    /**
     * Database password parameter.
     * <p>
     * This parameter defines the password for the database user. The
     * default value is "admin".
     * </p>
     */
    @Parameter(
            names = {"--dbpass"},
            description = "Database password")
    private String dbPassword;

    /**
     * Help parameter.
     * <p>
     * This flag indicates whether to print usage information for the
     * application. If set to true, usage information will be displayed.
     * The default value is false.
     * </p>
     */
    @Parameter(
            names = "--help",
            description = "Print usage",
            help = true)
    private Boolean help = false;
}
