package com.example.starter.base;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/greet")
public class RestService {

    @Inject
    JsonWebToken accessToken;

    @Inject
    GreetService greetService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String greet() {
        String name = accessToken.getName();
        return "RestService says: " + greetService.greet(name);
    }
}
