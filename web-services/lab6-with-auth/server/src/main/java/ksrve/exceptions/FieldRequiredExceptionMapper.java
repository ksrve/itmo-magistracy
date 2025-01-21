package ksrve.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Exception mapper for handling FieldRequiredException.
 * This class converts the exception into a HTTP response with a
 * 400 Bad Request status, including relevant error information.
 */
@Provider
public class FieldRequiredExceptionMapper implements ExceptionMapper<FieldRequiredException> {
    /**
     * Converts the FieldRequiredException to a Response object.
     *
     * @param e The FieldRequiredException to be converted.
     * @return A Response object containing the error details.
     */
    @Override
    public Response toResponse(final FieldRequiredException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorResponse.builder()
                        .code(ErrorCode.FIELD_ERROR.getCode())
                        .message(ErrorCode.FIELD_ERROR.getDescription())
                        .detail(e.getFaultInfo())
                        .build())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
