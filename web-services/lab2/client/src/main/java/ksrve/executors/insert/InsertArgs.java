package ksrve.executors.insert;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import ksrve.executors.common.Args;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * The {@code InsertArgs} class is a representation of the command-line arguments
 * for the insert operation in the application.
 *
 * <p>
 * This class extends the {@link Args} class and is used to specify parameters required
 * for inserting data into a specified target. The parameters must be provided in a
 * key-value format according to the following structure:
 * </p>

 * <p>
 * The data for insertion must be passed as a list of strings, where each string
 * represents a different parameter to be inserted. An example of valid input is:
 * </p>
 *
 * <blockquote>
 * <code>--data 'firstName=Lucas, lastName=Lee, email=lucas.lee@example.com, hireDate=2019-04-07, departmentId=101'</code>
 * </blockquote>

 * @see Args
 * @see InsertFilterValidator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Parameters(commandDescription = "Insert data based on specified parameters.")
public class InsertArgs extends Args {

    @Parameter(
            names = "--data",
            description = "Parameters for insertion must be specified in the format 'parameter_name=value'. " +
                    "Example: --data 'firstName=Lucas, lastName=Lee, email=lucas.lee@example.com, hireDate=2019-04-07, departmentId=101'",
            required = true,
            validateWith = InsertFilterValidator.class)
    private List<String> data;
}