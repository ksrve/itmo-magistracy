package ksrve;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;
import ksrve.exceptions.ErrorCode;
import ksrve.exceptions.ErrorResponse;

import java.io.IOException;
import java.util.Base64;
import java.util.StringTokenizer;

@Provider
public class BasicAuthFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_PREFIX = "Basic ";
    private static final String USERNAME = "admin"; // Замените на ваше имя пользователя
    private static final String PASSWORD = "password"; // Замените на ваш пароль

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(AUTHORIZATION_HEADER);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.NOT_AUTHORIZED.getCode())
                .message(ErrorCode.NOT_AUTHORIZED.getDescription())
                .build();

        if (authHeader == null || !authHeader.startsWith(AUTHORIZATION_PREFIX)) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(errorResponse)
                            .build());
            return;
        }

        String encodedCredentials = authHeader.substring(AUTHORIZATION_PREFIX.length());
        String decodedCredentials = new String(Base64.getDecoder().decode(encodedCredentials));
        StringTokenizer tokenizer = new StringTokenizer(decodedCredentials, ":");

        if (tokenizer.countTokens() != 2) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(errorResponse)
                            .build());
            return;
        }

        String username = tokenizer.nextToken();
        String password = tokenizer.nextToken();

        if (!USERNAME.equals(username) || !PASSWORD.equals(password)) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(errorResponse)
                            .build());
        }
    }
}