package ksrve.proxy.exceptions;

import lombok.Getter;

/**
 * Enum representing different error codes for application exceptions.
 * Each error code corresponds to a unique error scenario within the application.
 */
@Getter
public enum ErrorCode {

    /**
     * Error code indicating that the requested entity already exists.
     * This error may occur when attempting to create a resource that
     * is already present in the system.
     */
    ENTITY_ALREADY_EXISTS("ERR001", "Entity already exists."),

    /**
     * Error code indicating that the requested entity was not found.
     * This error is thrown when attempting to access a resource that
     * does not exist in the system.
     */
    ENTITY_NOT_FOUND("ERR002", "Entity not found."),

    /**
     * Error code indicating a validation failure.
     * This error is returned when input data does not meet the required
     * criteria or format.
     */
    VALIDATION_ERROR("ERR003", "Validation error."),

    /**
     * Error code indicating a missing of required field in request.
     * This error is request when input data does not meet the required
     * criteria or format.
     */
    FIELD_ERROR("ERR004", "Field required error.");

    /**
     *  The unique error code.
     */
    private final String code;
    /**
     *  The description of the error.
     */
    private final String description;

    /**
     * Constructor for the ErrorCode enum.
     *
     * @param code        The unique error code.
     * @param description A brief description of the error.
     */
    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
