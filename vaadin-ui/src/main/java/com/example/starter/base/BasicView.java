package com.example.starter.base;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.quarkus.security.runtime.SecurityIdentityAssociation;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.vaadin.firitin.appframework.MenuItem;

/**
 * The main view contains a button and a click listener.
 */
@Route(value = BasicView.PATH, layout = MainLayout.class)
@RolesAllowed("user")
@MenuItem(title = "Basic view")
public class BasicView extends VerticalLayout {
    public static final String PATH = "basic";

    @Inject
    SecurityIdentityAssociation identity;

    @Inject
    GreetService greetService;

    @RestClient
    RestGreetServiceClient restGreetServiceClient;

    public BasicView(SecurityIdentityAssociation identity) {
        add("This view is shown if logged in, the name is taken from the security context. There is also a button to call a REST API (aka microservice), to which the JWT token is passed.");
        TextField textField = new TextField("Your name");
        textField.setValue(identity.getIdentity().getPrincipal().getName());

        // Button click listeners can be defined as lambda expressions
        Button button = new Button("Say hello", e -> {
            add(new Paragraph(greetService.greet(textField.getValue())));
        });

        add(textField, button);


        Button b = new Button("Greet via REST API (aka calling a microservice)", e -> {
            try {
                add(new Paragraph(restGreetServiceClient.hello()));
            } catch (Exception ex) {
                add("Error: " + ex.getMessage());
                add(new Paragraph("Make sure the REST API is running! Check README!"));
            }
        });

        add(b);

    }
}
