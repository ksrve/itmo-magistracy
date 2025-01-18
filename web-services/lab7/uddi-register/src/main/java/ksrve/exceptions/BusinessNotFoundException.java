package ksrve.exceptions;

import java.text.MessageFormat;

/**
 * The {@code BusinessNotFound} class is a custom exception that is thrown
 * to indicate a failure during a business discovery process within the application.
 */
public class BusinessNotFoundException extends Exception {

    /**
     * Constructs a new {@code BusinessNotFoundException} with the specified
     * detail message.
     *
     */
    public BusinessNotFoundException() {
        super("No business for search were found!");
    }

    /**
     * Constructs a new {@code BusinessNotFoundException} with the specified
     * detail message and businessName.
     *
     * @param businessName name of the business
     */
    public BusinessNotFoundException(String businessName) {
        super(MessageFormat.format("No business with name {0} were found!", businessName));
    }
}