package ksrve.executors.update;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import ksrve.executors.common.Args;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * The {@code UpdateArgs} class represents the command-line arguments
 * required for updating employee data within a system. This class extends
 * the {@link Args} superclass, leveraging its properties while introducing
 * specific parameters needed for the update operation.
 *
 * <p>
 * The class is annotated with {@link Parameters}, which provides a description
 * of the command that utilizes this argument class. It employs JCommander
 * for argument parsing, enabling easy integration of command-line arguments
 * into Java applications.
 * </p>
 *
 * <p>
 * This class contains two main parameters:
 * <ul>
 *   <li>
 *     {@code --id}: The identifier of the employee to update. This
 *     parameter is required for determining which employee record
 *     should be modified.
 *   </li>
 *   <li>
 *     {@code --data}: A list of key-value pairs representing the fields
 *     to update, formatted as 'parameter_name=value'. This parameter
 *     is also required and validated by {@link UpdateFilterValidator}.
 *   </li>
 * </ul>
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 *   java -jar myapp.jar update --id 1 --data 'firstName=Lucas, lastName=Lee, email=lucas.lee@example.com, hireDate=2019-04-07, departmentId=101'
 * </pre>
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Parameters(commandDescription = "Update data based on specified identifier.")
public class UpdateArgs extends Args {

    /**
     * The unique identifier of the employee to update. This field
     * is required and must be provided by the user when executing
     * the update command.
     *
     * @see #data
     */
    @Parameter(
            names = "--id",
            description = "Identifier of employee to update. Example: --id 1",
            required = true)
    private Integer id;

    /**
     * The list of parameters specifying the fields to be updated.
     * Each entry must be in the format 'parameter_name=value'.
     * This parameter is required and validates the format using
     * {@link UpdateFilterValidator} to ensure correctness.
     *
     * @see #id
     */
    @Parameter(
            names = "--data",
            description = "Parameters for update must be specified in the format 'parameter_name=value'. " +
                    "Example: --data 'firstName=Lucas, lastName=Lee, email=lucas.lee@example.com, hireDate=2019-04-07, departmentId=101'",
            required = true,
            validateWith = UpdateFilterValidator.class)
    private List<String> data;
}