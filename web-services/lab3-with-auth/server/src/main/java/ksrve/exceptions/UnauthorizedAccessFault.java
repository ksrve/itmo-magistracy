package ksrve.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Custom fault class that represents unauthorized access.
 * This class extends AbstractFault to provide additional
 * context for validation failures.
 */
@Getter
@Setter
public class UnauthorizedAccessFault extends AbstractFault{

    /**
     * Default error message for validation failures.
     * This message is used when no specific error message is provided.
     */
    public static final String DEFAULT_MESSAGE = "Unauthorized access";

    /**
     * Constructs a new UnauthorizedAccessFault with the specified message,
     * that caused the error.
     *
     * @param message a descriptive message outlining the validation fault.
     */
    public UnauthorizedAccessFault(String message) {
        super(message);
    }

}
