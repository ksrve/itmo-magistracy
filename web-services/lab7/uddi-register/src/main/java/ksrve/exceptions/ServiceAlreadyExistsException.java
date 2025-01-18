package ksrve.exceptions;

/**
 * The {@code ServiceAlreadyExistsException} class is a custom exception that is thrown
 * to indicate a failure during a service registration process within the application.
 */
public class ServiceAlreadyExistsException extends Exception {

    /**
     * Constructs a new {@code ServiceAlreadyExistsException} with the specified
     * detail message.
     *
     */
    public ServiceAlreadyExistsException() {
        super("Service with such name already exists!");
    }
}