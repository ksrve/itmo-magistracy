package ksrve.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Abstract representation of a fault that can occur in the application.
 * This class serves as a base class for defining specific faults,
 * encapsulating the common properties relevant to handling errors.
 */
@Getter
@Setter
abstract public class AbstractFault {
    /**
     * Default error message used when a specific message is not provided.
     */
    public static final String DEFAULT_MESSAGE = "Error occurred while doing something";

    /**
     * A detailed error message that describes the fault.
     * This message can be customized to provide specific information
     * about the error that occurred.
     */
    public final String message;

    /**
     * An identifier for the entity related to the fault.
     * This may refer to a specific object in the application
     * that caused the error or is associated with it.
     */
    public final Integer id;

    /**
     * Constructor that initializes the fault with a custom error message
     * and an entity identifier.
     *
     * @param message the detailed error message.
     * @param id the identifier of the entity related to the fault.
     */
    public AbstractFault(final String message, final Integer id) {
        this.message = message;
        this.id = id;
    }

    /**
     * Constructor that initializes the fault with the default error message
     * and an entity identifier.
     *
     * @param id the identifier of the entity related to the fault.
     */
    public AbstractFault(Integer id) {
        this.message = DEFAULT_MESSAGE;
        this.id = id;
    }
}