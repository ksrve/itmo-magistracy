package ksrve.executors.common;

import ksrve.proxy.EmployeeApiClient;
import ksrve.utils.EntityPrinter;

/**
 * The CommandExecutor interface defines the contract for command executors
 * that perform operations on an EmployeeApiClient. Implementing classes are
 * expected to provide the logic for executing commands and parsing command-line
 * arguments.
 * <p>
 * This interface allows for the decoupling of command execution from the
 * underlying implementation of the operations on the employee data. Classes
 * implementing this interface can define specific commands such as insert,
 * delete, find, or update.
 * </p>
 */
public interface CommandExecutor {

    /**
     * A utility instance of EntityPrinter for printing entity information.
     */
    EntityPrinter printer = new EntityPrinter();

    /**
     * Executes the command using the provided EmployeeApiClient service.
     *
     * @param service the EmployeeApiClient instance used to perform operations
     *                related to employee data.
     */
    void run(EmployeeApiClient service);

    /**
     * Parses the provided command-line arguments and sets up the command's
     * parameters accordingly.
     *
     * @param argv the command-line arguments to parse.
     * @throws Exception if any error occurs during argument parsing.
     */
    void parseArgs(String[] argv) throws Exception;
}