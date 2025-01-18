package ksrve.exceptions;

import lombok.Getter;
import org.hibernate.exception.ConstraintViolationException;

/**
 * This exception is thrown when validation fails due to constraint violations
 * in the data being processed. It extends the standard Exception class.
 */
@Getter
public class ValidationException extends Exception {

    /**
     * Contains detailed information about the validation fault.
     */
    private final ValidationFault faultInfo;

    /**
     * Constructs a new ValidationException based on the provided
     * ConstraintViolationException. It analyzes the message from the
     * violation exception to determine if the violation is due to
     * a required field being null or if it is a duplicate key violation.
     *
     * @param violationException the original ConstraintViolationException
     *                           that caused this validation exception.
     * @param id the identifier of the entity that triggered the validation error.
     * @throws FieldRequiredException if the violation indicates that a
     *                                 required field is missing (i.e., null).
     * @throws EntityAlreadyExistsException if the violation indicates
     *                                       that a duplicate entity already exists.
     */
    public ValidationException(ConstraintViolationException violationException, Integer id)
            throws FieldRequiredException, EntityAlreadyExistsException {
        String message = violationException.getMessage();
        String[] lines = message.split("\n");
        if (message.contains("violates not-null constraint")) {
            this.faultInfo = new ValidationFault("Value violates not-null constraint", id, lines[0]);
            throw new FieldRequiredException("Value violates not-null constraint", this.faultInfo);
        } else if (message.contains("duplicate key value violates unique constraint")) {
            this.faultInfo = new ValidationFault("Duplicate key violates unique constraint", id, lines[0]);
            throw new EntityAlreadyExistsException("Duplicate key value violates unique constraint", this.faultInfo);
        } else {
            this.faultInfo = new ValidationFault("Unknown: " + message, id, null);
        }
    }
}