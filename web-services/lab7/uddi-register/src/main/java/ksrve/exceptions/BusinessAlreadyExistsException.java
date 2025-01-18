package ksrve.exceptions;

/**
 * The {@code BusinessExistsException} class is a custom exception that is thrown
 * to indicate a failure during a business registration process within the application.
 */
public class BusinessAlreadyExistsException extends Exception {

    /**
     * Constructs a new {@code BusinessExistsException} with the specified
     * detail message.
     *
     */
    public BusinessAlreadyExistsException() {
        super("Business with such name already exists!");
    }
}