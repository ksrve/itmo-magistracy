package ksrve.utils;

import ksrve.proxy.EntityAlreadyExistsException;
import ksrve.proxy.EntityNotFoundException;
import ksrve.proxy.FieldRequiredException;
import ksrve.proxy.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code ExceptionHandler} class provides centralized exception handling for
 * specific application-related exceptions. It logs errors with appropriate messages
 * for each type of exception, facilitating easier debugging and maintenance.
 *
 * <p>
 * This utility class primarily handles exceptions that occur during entity operations,
 * such as looking up, validating, or creating entities. Each method in this class
 * is dedicated to handling a specific type of exception, logging relevant information
 * to help diagnose issues.
 * </p>
 */
public class ExceptionHandler {

    private static final Logger logger = LogManager.getLogger(ExceptionHandler.class);

    /**
     * Handles the provided exception by determining its type and executing
     * the appropriate handling method.
     *
     * @param e the exception to be handled, which could be of several types including
     *          {@link EntityNotFoundException}, {@link ValidationException},
     *          {@link EntityAlreadyExistsException}, or {@link FieldRequiredException}.
     */
    public static void handleException(final Exception e) {
        if (e instanceof EntityNotFoundException) {
            handleEntityNotFound((EntityNotFoundException) e);
        } else if (e instanceof ValidationException) {
            handleValidationException((ValidationException) e);
        } else if (e instanceof EntityAlreadyExistsException) {
            handleEntityAlreadyExistsException((EntityAlreadyExistsException) e);
        } else if (e instanceof FieldRequiredException) {
            handleFieldRequiredException((FieldRequiredException) e);
        } else {
            handleGeneralException(e);
        }
    }

    /**
     * Handles an {@link EntityNotFoundException}, logging an error message that
     * includes the exception's message and the ID of the entity that was not found.
     *
     * @param e the {@code EntityNotFoundException} to be handled.
     */
    private static void handleEntityNotFound(final EntityNotFoundException e) {
        logger.error("{} with such id {}", e.getMessage(), e.getFaultInfo().getId());
    }

    /**
     * Handles a {@link ValidationException}, logging an error message that includes
     * the exception's message and the ID of the entity that failed validation.
     *
     * @param e the {@code ValidationException} to be handled.
     */
    private static void handleValidationException(final ValidationException e) {
        logger.error("{} with such id {}", e.getMessage(), e.getFaultInfo().getId());
    }

    /**
     * Handles an {@link EntityAlreadyExistsException}, logging an error message that
     * includes the exception's message, the ID of the conflicting entity, and the
     * field that caused the conflict.
     *
     * @param e the {@code EntityAlreadyExistsException} to be handled.
     */
    private static void handleEntityAlreadyExistsException(final EntityAlreadyExistsException e) {
        logger.error("{} with such id {}: {}", e.getMessage(),
                e.getFaultInfo().getId(),
                e.getFaultInfo().getField());
    }

    /**
     * Handles a {@link FieldRequiredException}, logging an error message that
     * includes the exception's message, the ID of the entity, and the required
     * field that is missing.
     *
     * @param e the {@code FieldRequiredException} to be handled.
     */
    private static void handleFieldRequiredException(final FieldRequiredException e) {
        logger.error("{} with such id {}: {}", e.getMessage(),
                e.getFaultInfo().getId(),
                e.getFaultInfo().getField());
    }

    /**
     * Handles general exceptions not specifically accounted for, logging the
     * exception message to the console and the stack trace to the logger.
     *
     * @param e the general exception to be handled.
     */
    private static void handleGeneralException(Exception e) {
        System.out.println("Error occurred: " + e.getMessage());
        logger.error(e);
    }
}