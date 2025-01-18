package ksrve.exceptions;

import lombok.Getter;

/**
 * This exception is thrown when an entity is already exists.
 */
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
}
