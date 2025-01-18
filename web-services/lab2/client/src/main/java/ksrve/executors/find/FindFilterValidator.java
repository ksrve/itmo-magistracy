package ksrve.executors.find;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.util.regex.Pattern;

/**
 * The {@code FindFilterValidator} class implements the {@link IParameterValidator}
 * interface to validate filter parameters for the find command in the application.
 *
 * <p>
 * If a parameter does not meet these requirements, a {@link ParameterException} is thrown
 * with a descriptive error message indicating the correct usage.
 * </p>
 *
 * @see IParameterValidator
 */
public class FindFilterValidator implements IParameterValidator {

    private static final Pattern FILTER_PATTERN = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*\\s*(==|!=|>=|<=|>|<|LIKE)\\s*[^\\s]+$");

    /**
     * Validates the format of a filter parameter.
     *
     * @param name the name of the parameter being validated.
     * @param value the value of the parameter to validate.
     * @throws ParameterException if the parameter value does not match the expected format.
     *                             The exception message specifies the correct format.
     */
    @Override
    public void validate(String name, String value) throws ParameterException {
        // Проверка соответствия значения фильтра регулярному выражению
        if (!FILTER_PATTERN.matcher(value).matches()) {
            throw new ParameterException("Parameter " + name + " should be in the format " +
                    "'parameter_name operation value'. For example: --filter age >= 30");
        }
    }
}