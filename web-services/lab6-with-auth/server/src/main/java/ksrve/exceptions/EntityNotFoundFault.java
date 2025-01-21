package ksrve.exceptions;

/**
 * Custom fault that is thrown when an entity is not found in the application.
 * This exception extends the AbstractFault class and allows for handling
 * specific cases where a requested entity does not exist.
 */
public class EntityNotFoundFault extends AbstractFault {

    /**
     * Default error message used when no specific message is provided.
     * This message indicates that no entity could be found.
     */
    public static final String DEFAULT_MESSAGE = "No entity found";

    /**
     * Constructs a new EntityNotFoundFault with a specified detail message
     * and the identifier of the entity that was not found.
     *
     * @param message a detailed error message providing additional information
     *                about the entity not found.
     * @param id the identifier of the entity that could not be located.
     */
    public EntityNotFoundFault(String message, Integer id) {
        super(message, id);
    }
}