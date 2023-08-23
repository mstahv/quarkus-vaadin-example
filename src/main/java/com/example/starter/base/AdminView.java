package com.example.starter.base;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.quarkus.security.runtime.SecurityIdentityAssociation;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import org.vaadin.firitin.appframework.MenuItem;

/**
 * The main view contains a button and a click listener.
 */
@Route(layout = MainLayout.class)
@RolesAllowed("admin")
@MenuItem(title = "Admin view", order = MenuItem.END)
@PageTitle("Admin view")
public class AdminView extends VerticalLayout {

    @Inject
    SecurityIdentityAssociation identity;

    @Inject
    GreetService greetService;

    public AdminView(SecurityIdentityAssociation identity) {
        add(new H1("This view is for admins only"));
        add("Same but different :-)");

        TextField textField = new TextField("Your name");
        textField.setValue(identity.getIdentity().getPrincipal().getName());

        Button button = new Button("Say hello", e -> {
            add(new Paragraph(greetService.greet(textField.getValue())));
        });

        add(textField, button);
    }
}
