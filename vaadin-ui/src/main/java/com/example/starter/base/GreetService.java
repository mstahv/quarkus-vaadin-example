package com.example.starter.base;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;

/**
 * A service class for greeting the user.
 */
@ApplicationScoped
public class GreetService {

    public String greet(String name) {
        if (name == null || name.isEmpty()) {
            return "Hello anonymous user";
        } else {
            return "Hello " + name;
        }
    }
}
