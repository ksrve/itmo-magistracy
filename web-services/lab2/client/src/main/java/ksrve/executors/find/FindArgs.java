package ksrve.executors.find;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import ksrve.executors.common.Args;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code FindArgs} class represents the arguments used for the find command in
 * the application. It extends the {@link Args} class and is designed to collect
 * command-line parameters that specify filters for data retrieval.
 *
 * <p>
 * This class uses the JCommander library to facilitate command-line argument parsing.
 * A user can specify multiple filter parameters to refine the search for specific data
 * based on defined criteria.
 * </p>
 *
 * <p>
 * Each filter parameter must adhere to the format:
 * <pre>
 *    'parameter_name operation value'.
 * </pre>
 * For example, to filter employees based on age and status, the following command can be used:
 * <pre>
 *    --filter age >= 30 --filter status = active
 * </pre>
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Parameters(commandDescription = "Find data based on specified filter parameters.")
public class FindArgs extends Args {
    @Parameter(
            names = "--filter",
            description = "Filter parameters must be specified in the format 'parameter_name operation value'. " +
                    "Example: --filter age >= 30 --filter status = active. You can specify multiple filters.",
            validateWith = FindFilterValidator.class)
    private List<String> filter = new ArrayList<>();
}