package com.example.starter.base;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.inject.Inject;
import org.vaadin.firitin.appframework.MenuItem;

/**
 * The main view contains a button and a click listener.
 */
@Route(value = "", layout = MainLayout.class)
@AnonymousAllowed
@MenuItem(title = "About (public)", order = MenuItem.BEGINNING)
public class AboutView extends VerticalLayout {

    @Inject
    GreetService greetService;

    public AboutView() {
        add(new H1("Quarkus }> samples"));
        add("This view is shown even without login. More views for bob:bob or alice:alice (admin), if you login via the sidebar.");
        TextField textField = new TextField("Your name");

        // Button click listeners can be defined as lambda expressions
        Button button = new Button("Say hello", e -> {
            add(new Paragraph(greetService.greet(textField.getValue())));
        });

        add(textField, button);
    }
}
