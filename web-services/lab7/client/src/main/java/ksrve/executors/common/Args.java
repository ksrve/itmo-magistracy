package ksrve.executors.common;

import com.beust.jcommander.Parameter;
import lombok.Data;

/**
 * The Args class serves as a data structure for command-line arguments
 * related to server configuration and command execution. It utilizes
 * the JCommander library annotations to facilitate parsing and validation
 * of input parameters.
 * <p>
 * This class encapsulates the necessary parameters that can be provided
 * when running a command-line application. Each parameter has a default
 * value where applicable, and the class ensures that required parameters
 * are noted and enforced.
 * </p>
 */
@Data
public class Args {

    /**
     * The server host parameter, specifying the hostname of the server.
     * Defaults to "localhost".
     */
    @Parameter(
            names = "--host",
            description = "Server hostname")
    private String host = "localhost";

    /**
     * The server port parameter, specifying the port number on which
     * the server is listening. Defaults to "8080".
     */
    @Parameter(
            names = "--port",
            description = "Server port")
    private String port = "8080";

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
            names = "--password",
            description = "JUDDI password")
    private String password = "da_password1";

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
