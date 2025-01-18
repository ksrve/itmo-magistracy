package ksrve.exceptions;

/**
 * The {@code RegistrationException} class is a custom exception that is thrown
 * when there is an issue with the registration process in the application.
 */
public class RegistrationException extends Exception {

    /**
     * Constructs a new {@code RegistrationException} with the specified
     * detail message.
     *
     * @param message the detail message which is saved for later retrieval
     *                by the {@code getMessage()} method.
     */
    public RegistrationException(String message) {
        super(message);
    }
}