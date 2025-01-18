package ksrve.exceptions;

/**
 * The {@code DiscoveryException} class is a custom exception that is thrown
 * to indicate a failure during a discovery process within the application.
 */
public class DiscoveryException extends Exception {

    /**
     * Constructs a new {@code DiscoveryException} with the specified
     * detail message.
     *
     * @param message the detail message which is saved for later retrieval
     *                by the {@code getMessage()} method. This message
     *                provides information about the cause of the exception.
     */
    public DiscoveryException(String message) {
        super(message);
    }
}