package ksrve.utils;

import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.MessageContext;
import ksrve.proxy.EmployeeService;
import ksrve.proxy.EmployeeServiceImpl;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Authorizer {
    private final BindingProvider bindingProvider;
    private final EmployeeServiceImpl port;
    private final URL endpoint;

    public Authorizer(URL endpoint, EmployeeService service) {
        this.endpoint = endpoint;
        this.port = service.getEmployeeServiceImplPort();
        this.bindingProvider = (BindingProvider) this.port;
    }

    public EmployeeServiceImpl getAuthenticatedServicePort(String username, String password) {
        try {
            Map<String, Object> req_ctx = this.bindingProvider.getRequestContext();
            req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, String.valueOf(this.endpoint));

            Map<String, List<String>> headers = new HashMap<>();
            headers.put("Username", Collections.singletonList(username));
            headers.put("Password", Collections.singletonList(password));
            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.port;
    }
}