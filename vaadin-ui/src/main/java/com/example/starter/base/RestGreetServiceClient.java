package com.example.starter.base;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/hello")
@RegisterRestClient
@RegisterProvider(RestClientBearerTokenFilter.class)
public interface RestGreetServiceClient {

        @GET
        String hello();

}
