package ksrve.proxy;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

import jakarta.ws.rs.core.HttpHeaders;

import jakarta.ws.rs.core.Response;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

/**
 * Class for employee API client.
 */
@Getter
public class EmployeeApiClient {

    /**
     * Logger.
     */
    private static final Logger logger = LogManager.getLogger(EmployeeApiClient.class);

    /**
     * Api.
     */
    private final EmployeeApi api;

    /**
     * Base URI of application.
     */
    private final URL baseUri;

    /**
     * Constructor for initializing the EmployeeApiClient with a base URI.
     *
     * @param baseUri The base URI of the Employee API.
     */
    public EmployeeApiClient(final URL baseUri, final String username, final String password) {
        this.baseUri = baseUri;

        ResteasyWebTarget target;
        Client client = ClientBuilder.newClient();
        client.register(ResteasyJackson2Provider.class);
        client.register(new BasicAuthProvider(username, password));

        target = (ResteasyWebTarget) client.target(String.valueOf(baseUri));

        this.api = target.proxy(EmployeeApi.class);
    }
}