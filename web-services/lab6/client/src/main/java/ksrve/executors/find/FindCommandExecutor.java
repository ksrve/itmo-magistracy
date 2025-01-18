package ksrve.executors.find;

import com.beust.jcommander.JCommander;
import ksrve.executors.common.CommandExecutor;
import ksrve.proxy.EmployeeApiClient;
import ksrve.proxy.model.dto.FilterDTO;
import ksrve.utils.EntityPrinter;
import ksrve.utils.ExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code FindCommandExecutor} class implements the {@link CommandExecutor} interface and
 * is responsible for executing the find command in the application. It handles the
 * parsing of command-line arguments and the execution of filter-based queries against the
 * employee data.
 *
 * <p>
 * This class uses the JCommander library for command-line argument parsing and works with
 * a service interface {@link EmployeeApiClient} to perform operations on employee data.
 * The execution flow includes parsing filters, retrieving employee data based on these filters,
 * and printing the results to the console.
 * </p>
 *
 * <p>
 * The main responsibilities of this class include:
 * <ul>
 *   <li>Parsing command-line arguments to extract filter conditions.</li>
 *   <li>Converting filter strings into {@link FilterDTO} objects for querying.</li>
 *   <li>Executing the query against the employee service and handling any exceptions that may arise.</li>
 *   <li>Printing the retrieved employee data using the {@link EntityPrinter} class.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 *    String[] args = {"--filter", "age >= 30", "--filter", "status = active"};
 *    FindCommandExecutor executor = new FindCommandExecutor();
 *    executor.parseArgs(args);
 *    executor.run(someEmployeeService);
 * </pre>
 * </p>
 */
public class FindCommandExecutor implements CommandExecutor {

    private final FindArgs args;
    private static final Logger logger = LogManager.getLogger(FindCommandExecutor.class);

    /**
     * Constructs a new {@code FindCommandExecutor} instance, initializing the arguments.
     */
    public FindCommandExecutor() {
        this.args = new FindArgs();
    }

    /**
     * Parses the given list of filter strings into a list of {@link FilterDTO} objects.
     *
     * @param filterStrings the list of filter strings to parse
     * @return a list of {@link FilterDTO} instances representing the parsed filters
     * @throws IllegalArgumentException if any filter string is not in the expected format
     */
    public static List<FilterDTO> parseFilters(List<String> filterStrings) {
        List<FilterDTO> filters = new ArrayList<>();

        for (String filterString : filterStrings) {
            // Разбиваем строку на части по пробелу
            String[] parts = filterString.split(" ", 3);
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid filter format: " + filterString +
                        ". Expected format: 'parameter_name operation value'.");
            }

            FilterDTO filter = new FilterDTO();
            filter.setKey(parts[0]);
            filter.setOperation(parts[1]);
            filter.setValue(parts[2]);

            filters.add(filter);
        }

        return filters;
    }

    /**
     * Parses the command-line arguments provided in the form of a string array.
     *
     * @param argv the command-line arguments to parse
     */
    @Override
    public void parseArgs(String[] argv){
        JCommander jCommander = JCommander.newBuilder()
                .addObject(args)
                .build();
        jCommander.parse(argv);

    }

    /**
     * Executes the find command by querying employee data based on the parsed filters
     * and printing the results.
     *
     * @param service the {@link EmployeeApiClient} instance used to perform queries
     */
    @Override
    public void run(EmployeeApiClient service) {
        try {
            printer.printData(service.getApi().find(parseFilters(args.getFilter())));
        }catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }
}