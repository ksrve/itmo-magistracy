package ksrve.exceptions;

import jakarta.xml.ws.WebFault;
import lombok.Getter;

/**
 * This exception is thrown when an entity is already exists.
 */
@WebFault(faultBean = "ksrve.exceptions.ValidationFault")
@Getter
public class FieldRequiredException extends Exception{
    /**
     * Fault information.
     */
    private final ValidationFault faultInfo;

    /**
     * Constructor for FieldRequiredException exception.
     *
     * @param message the detail message.
     * @param faultInfo fault information.
     */
    public FieldRequiredException(String message, ValidationFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * Constructor for FieldRequiredException exception.
     *
     * @param message the detail message.
     * @param id the ID of the entity that was not found.
     * @param field field name.
     */
    public FieldRequiredException(String message, Integer id, String field) {
        super(message);
        this.faultInfo = new ValidationFault(message, id, field);
    }
}
