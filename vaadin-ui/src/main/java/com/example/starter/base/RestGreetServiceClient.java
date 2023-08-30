package com.example.starter.base;

import io.quarkus.oidc.token.propagation.AccessTokenRequestFilter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/hello")
@RegisterRestClient
/* This filter from quarkus-oidc-token-propagation
 * takes the current users access token and adds
 * "Authorization: Bearer token" header to the REST calls.
 */
@RegisterProvider(AccessTokenRequestFilter.class)
public interface RestGreetServiceClient {

        @GET
        String hello();

}
