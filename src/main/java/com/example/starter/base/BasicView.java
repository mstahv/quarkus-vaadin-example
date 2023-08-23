package com.example.starter.base;

import com.vaadin.flow.router.PageTitle;
import io.quarkus.security.runtime.SecurityIdentityAssociation;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
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

    public BasicView(SecurityIdentityAssociation identity) {
        add("This view is shown if logged in, the name is taken from the security context.");
        TextField textField = new TextField("Your name");
        textField.setValue(identity.getIdentity().getPrincipal().getName());

        // Button click listeners can be defined as lambda expressions
        Button button = new Button("Say hello", e -> {
            add(new Paragraph(greetService.greet(textField.getValue())));
        });

        add(textField, button);


        Button b = new Button("Greet via REST API", e -> {


        });

    }
}
