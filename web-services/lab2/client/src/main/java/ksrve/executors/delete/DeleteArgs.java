package ksrve.executors.delete;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import ksrve.executors.common.Args;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The {@code DeleteArgs} class represents the command-line arguments required
 * for deleting an employee record based on a specified identifier. It extends
 * the {@link Args} class, which may contain common argument features.
 *
 * <p>
 * This class is annotated with JCommander annotations to facilitate command-line
 * argument parsing. The following parameter is defined:
 * </p>
 *
 * <ul>
 *   <li>{@code --id}: Represents the identifier of the employee to be deleted.
 *       This parameter is mandatory and must be provided when the command is executed.</li>
 * </ul>
 *
 * <p>
 * Example usage in a command-line context:
 * <pre>
 * java -jar your-application.jar delete --id 1
 * </pre>
 * This line would execute the delete operation for the employee with identifier 1.
 * </p>
 *
 * <p>
 * The class utilizes Lombok annotations for boilerplate reduction. It automatically
 * generates getters, setters, equals, hashCode, and toString methods.
 * </p>
 *
 * @see Args
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Parameters(commandDescription = "Delete data based on specified identifier.")
public class DeleteArgs extends Args {
    @Parameter(
            names = "--id",
            description = "Identifier of employee to delete. Example: --id 1",
            required = true)
    private Integer id;
}