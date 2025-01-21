package ksrve.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Exception mapper for handling EntityNotFoundException.
 * This class converts the exception into a HTTP response with a
 * 400 Bad Request status, including relevant error information.
 */
@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {

    /**
     * Converts the EntityNotFoundException to a Response object.
     *
     * @param e The EntityNotFoundException to be converted.
     * @return A Response object containing the error details.
     */
    @Override
    public Response toResponse(final EntityNotFoundException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorResponse.builder()
                        .code(ErrorCode.ENTITY_NOT_FOUND.getCode())
                        .message(ErrorCode.ENTITY_NOT_FOUND.getDescription())
                        .detail(e.getFaultInfo())
                        .build())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
