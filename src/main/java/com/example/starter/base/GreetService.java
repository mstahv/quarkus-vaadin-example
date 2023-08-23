package com.example.starter.base;

import jakarta.enterprise.context.Dependent;

/**
 * A service class for greeting the user. Used via
 * CDI directly in the Vaadin views & in the JAX-RS resource.
 */
@Dependent
public class GreetService {

    public String greet(String name) {
        if (name == null || name.isEmpty()) {
            return "Hello anonymous user";
        } else {
            return "Hello " + name;
        }
    }
}
