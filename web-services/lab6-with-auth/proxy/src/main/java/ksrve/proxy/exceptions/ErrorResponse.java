package ksrve.proxy.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a structured error response to be sent to clients
 * when an error occurs within the application.
 * <p>
 * This class holds essential information about the error, including
 * an error code, a human-readable message, and any additional
 * details that may help clients understand the context of the error.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    /**
     * A unique code representing the error type.
     */
    private String code;

    /**
     * A human-readable message describing the error.
     */
    private String message;

    /**
     * Additional details regarding the error.
     */
    private Object detail;
}
