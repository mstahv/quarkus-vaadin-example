package com.example.starter.base;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.auth.ViewAccessChecker;
import jakarta.enterprise.event.Observes;

public class ViewAccessCheckerInit {
    private final ViewAccessChecker viewAccessChecker;

    public ViewAccessCheckerInit() {
        viewAccessChecker = new ViewAccessChecker();
        // Setting the login view with string forwards to
        // a URL intercepted by Quarkus OIDC extension
        // (see application.properties). A second redirect
        // is then made to the OIDC server for actual login page.
        // TODO figure out if there is a clean way to get
        // the OIDC server login URL, could go straight there
        viewAccessChecker.setLoginView("/" + LoginView.PATH);
    }

    public void serviceInit(@Observes ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addUIInitListener(
                uiInitEvent -> uiInitEvent.getUI().addBeforeEnterListener(viewAccessChecker)
        );
    }
}
