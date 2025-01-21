package ksrve.exceptions;

import jakarta.xml.ws.WebFault;
import lombok.Getter;

/**
 * This exception is thrown when an entity is not found.
 */
@WebFault(faultBean = "ksrve.exceptions.EntityNotFoundFault")
@Getter
public class EntityNotFoundException extends Exception {
    /**
     * Fault information.
     */
    private final EntityNotFoundFault faultInfo;

    /**
     * Constructor for EntryNotFound exception.
     *
     * @param message the detail message.
     * @param id the ID of the entity that was not found.
     */
    public EntityNotFoundException(String message, Integer id) {
        super(message);
        this.faultInfo = new EntityNotFoundFault(message, id);
    }

    /**
     * Constructor for EntryNotFound exception.
     *
     * @param id the ID of the entity that was not found.
     */
    public EntityNotFoundException(Integer id) {
        super(EntityNotFoundFault.DEFAULT_MESSAGE);
        this.faultInfo = new EntityNotFoundFault(EntityNotFoundFault.DEFAULT_MESSAGE, id);
    }
}