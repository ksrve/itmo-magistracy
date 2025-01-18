package ksrve.executors.insert;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.util.regex.Pattern;

/**
 * The {@code InsertFilterValidator} class implements the {@link IParameterValidator}
 * interface to validate data parameters for the insert command in the application.
 *
 * <p>
 * If a parameter does not meet these requirements, a {@link ParameterException} is thrown
 * with a descriptive error message indicating the correct usage.
 * </p>
 *
 * @see IParameterValidator
 */
public class InsertFilterValidator implements IParameterValidator {

    private static final Pattern DATA_PATTERN = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*=[^\\s]+$");

    /**
     * Validates the format of a data parameter.
     *
     * @param name the name of the parameter being validated.
     * @param value the value of the parameter to validate.
     * @throws ParameterException if the parameter value does not match the expected format.
     *                             The exception message specifies the correct format.
     */
    @Override
    public void validate(String name, String value) throws ParameterException {
        String[] pairs = value.split(",\\s*");

        for (String pair : pairs) {
            if (!DATA_PATTERN.matcher(pair).matches()) {
                throw new ParameterException("Parameter " + name + " should be in the format " +
                        "'key=value'. For example: firstName=Lucas, lastName=Lee, email=lucas.lee@example.com");
            }

            String[] keyValue = pair.split("=", 2);
            String key = keyValue[0].trim();
            String valuePart = keyValue[1].trim();

            if (key.equals("email") && !isValidEmail(valuePart)) {
                throw new ParameterException("Parameter " + key + " should have a valid email format.");
            }
        }
    }

    /**
     * Validates the format of email.
     *
     * @param email the email to be validated.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*%-+]+(\\.[a-zA-Z0-9_+&*%-+]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,})$";
        return email.matches(emailRegex);
    }
}