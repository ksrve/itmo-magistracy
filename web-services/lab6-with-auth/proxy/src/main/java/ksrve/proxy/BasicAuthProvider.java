package ksrve.proxy;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuthProvider implements ClientRequestFilter {

    private final String authHeader;

    public BasicAuthProvider(String username, String password) {
        String auth = username + ":" + password;
        this.authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        requestContext.getHeaders().putSingle(HttpHeaders.AUTHORIZATION, authHeader);
    }
}