package ksrve.utils;

import jakarta.ws.rs.WebApplicationException;
import ksrve.proxy.exceptions.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class ExceptionHandler {

    private static final Logger logger = LogManager.getLogger(ExceptionHandler.class);

    public static void handleException(final Exception e) {
        ErrorResponse error = null;
        if (e instanceof WebApplicationException err) {
            error = err.getResponse().readEntity(ErrorResponse.class);
        } else handleGeneralException(e);

        switch (Objects.requireNonNull(error).getCode()) {
            case "ERR001" -> handleEntityAlreadyExistsException(error);
            case "ERR002" -> handleEntityNotFound(error);
            case "ERR003" -> handleValidationException(error);
            case "ERR004" -> handleFieldRequiredException(error);
            default -> handleGeneralException(error);
        }
    }

    private static void handleEntityNotFound(final ErrorResponse e) {
        logger.error("{}: {} -> {}", e.getCode(), e.getMessage(), e.getDetail());
    }

    private static void handleValidationException(final ErrorResponse e) {
        logger.error("{}: {} -> {}", e.getCode(), e.getMessage(), e.getDetail());
    }

    private static void handleEntityAlreadyExistsException(final ErrorResponse e) {
        logger.error("{}: {} -> {}", e.getCode(), e.getMessage(), e.getDetail());
    }

    private static void handleFieldRequiredException(final ErrorResponse e) {
        logger.error("{}: {} -> {}", e.getCode(), e.getMessage(), e.getDetail());
    }

    private static void handleGeneralException(ErrorResponse e) {
        logger.error("{}: {} -> {}", e.getCode(), e.getMessage(), e.getDetail());
    }

    private static void handleGeneralException(Exception e) {
        logger.error(e.getMessage());
    }
}