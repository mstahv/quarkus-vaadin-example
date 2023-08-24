package com.example.starter.base;

import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * Adds current users token to outgoing REST calls.
 * Quarkus also has extensions to do this token
 * propagation automatically.
 */
@Provider
public class RestClientBearerTokenFilter implements ClientRequestFilter {

    @Inject
    JsonWebToken accessToken;

    @Override
    public void filter(ClientRequestContext requestContext) {
        requestContext.getHeaders().add("Authorization", "Bearer " + accessToken.getRawToken());
    }

}