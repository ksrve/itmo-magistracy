package ksrve.exceptions;

/**
 * The {@code ServiceNotFoundException} class is a custom exception that is thrown
 * to indicate a failure during a business discovery process within the application.
 */
public class ServiceNotFoundException extends Exception {

    /**
     * Constructs a new {@code BusinessNotFound} with the specified
     * detail message.
     *
     */
    public ServiceNotFoundException() {
        super("No service for search were found!");
    }
}