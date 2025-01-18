package ksrve.executors.common;

import ksrve.proxy.EmployeeService;
import ksrve.utils.EntityPrinter;

/**
 * The {@code CommandExecutor} interface defines a contract for executing commands
 * related to employee services. Implementing classes should provide specific
 * functionality by implementing the defined methods.
 * <p>
 * This interface includes methods for executing a command using an
 * {@link EmployeeService} instance and for parsing command-line arguments.
 *
 * <p>
 * Additionally, an instance of {@link EntityPrinter} is provided for printing
 * entities, which can be utilized by implementing classes to enhance output
 * representation.
 */
public interface CommandExecutor {

    /** An instance of {@link EntityPrinter} for printing entities. */
    EntityPrinter printer = new EntityPrinter();

    /**
     * Executes the command using the specified {@link EmployeeService}.
     *
     * @param service the {@link EmployeeService} to be used for executing the command
     */
    void run(EmployeeService service);

    /**
     * Parses the command-line arguments provided to the executor.
     *
     * @param argv an array of strings representing command-line arguments
     * @throws Exception if an error occurs during argument parsing
     */
    void parseArgs(String[] argv) throws Exception;
}