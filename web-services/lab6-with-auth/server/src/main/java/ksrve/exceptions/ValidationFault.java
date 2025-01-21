package ksrve.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Custom fault class that represents validation errors when an entity
 * already exists. This class extends AbstractFault to provide additional
 * context for validation failures.
 */
@Getter
@Setter
public class ValidationFault extends AbstractFault {

    /**
     * Default error message for validation failures.
     * This message is used when no specific error message is provided.
     */
    public static final String DEFAULT_MESSAGE = "Validation for entity failed";

    /**
     * The field that caused the validation error.
     * This attribute stores the name of the field associated with the
     * validation fault.
     */
    public final String field;

    /**
     * Constructs a new ValidationFault with the specified message, entity ID,
     * and field name that caused the validation error.
     *
     * @param message a descriptive message outlining the validation fault.
     * @param id the identifier of the entity that triggered the validation
     *           error.
     * @param field the specific field that caused the validation failure.
     */
    public ValidationFault(String message, Integer id, String field) {
        super(message, id);
        this.field = field;
    }

}