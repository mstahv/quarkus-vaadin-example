package com.example.starter.base;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.auth.ViewAccessChecker;
import jakarta.annotation.security.PermitAll;
import org.vaadin.firitin.appframework.MenuItem;

/**
 * This is a dummy view to help in login process.
 * It is actually never shown. Quarkus OIDC integration
 * redirects to OIDC server for actual login, but this
 * view is used to redirect to the original URL after login.
 */
@Route
@PermitAll
@MenuItem(enabled = false)
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    public static final String PATH = "login";

    public LoginView() {
        add("Hello :-)");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        System.err.println("Redirecting to original view...");
        var session = VaadinRequest.getCurrent().getWrappedSession();
        // ViewAccessChecker saves the original route to session
        // restore that when we are returned form OIDC server
        Object origView = session.getAttribute(ViewAccessChecker.SESSION_STORED_REDIRECT);
        if(origView != null) {
            event.forwardTo(origView.toString());
        } else {
            // This should never happen :-)
            // But happens if you manually enter login while already
            // logged in.
            event.forwardTo(BasicView.class);
        }
    }
}
