package ksrve.exceptions;

import jakarta.xml.ws.WebFault;
import lombok.Getter;

/**
 * This exception is thrown when an entity is already exists.
 */
@Getter
@WebFault(faultBean = "ksrve.exceptions.ValidationFault")
public class EntityAlreadyExistsException extends Exception {
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
    public EntityAlreadyExistsException(String message, ValidationFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }
}