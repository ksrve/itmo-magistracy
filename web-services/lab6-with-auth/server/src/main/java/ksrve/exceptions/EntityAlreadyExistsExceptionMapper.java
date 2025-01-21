package ksrve.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Exception mapper for handling EntityAlreadyExistsException.
 * This class converts the exception into a HTTP response with a
 * 400 Bad Request status, including relevant error information.
 */
@Provider
public class EntityAlreadyExistsExceptionMapper implements ExceptionMapper<EntityAlreadyExistsException> {
    /**
     * Converts the EntityAlreadyExistsException to a Response object.
     *
     * @param e The EntityAlreadyExistsException to be converted.
     * @return A Response object containing the error details.
     */
    @Override
    public Response toResponse(final EntityAlreadyExistsException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorResponse.builder()
                        .code(ErrorCode.ENTITY_ALREADY_EXISTS.getCode())
                        .message(ErrorCode.ENTITY_ALREADY_EXISTS.getDescription())
                        .detail(e.getFaultInfo())
                        .build())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
