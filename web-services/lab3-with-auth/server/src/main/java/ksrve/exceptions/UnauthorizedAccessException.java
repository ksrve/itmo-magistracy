package ksrve.exceptions;

import jakarta.xml.ws.WebFault;
import lombok.Getter;

/**
 * This exception is thrown when a user tries to access a resource without appropriate permissions.
 */
@WebFault(faultBean = "ksrve.exceptions.UnauthorizedAccessFault")
@Getter
public class UnauthorizedAccessException extends Exception {
    /**
     * Fault information.
     */
    private final UnauthorizedAccessFault faultInfo;

    /**
     * Constructor for UnauthorizedAccess exception.
     *
     * @param message the detail message explaining why access is not authorized.
     */
    public UnauthorizedAccessException(String message) {
        super(message);
        this.faultInfo = new UnauthorizedAccessFault(message);
    }
}