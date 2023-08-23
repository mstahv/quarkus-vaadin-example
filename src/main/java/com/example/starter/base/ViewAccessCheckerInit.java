package com.example.starter.base;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.auth.ViewAccessChecker;

public class ViewAccessCheckerInit implements VaadinServiceInitListener {
    private final ViewAccessChecker viewAccessChecker;

    public ViewAccessCheckerInit() {
        viewAccessChecker = new ViewAccessChecker();
        // Set the path to the core login URI for redirects if the user is not authenticated
        //viewAccessChecker.setLoginView("/login");
    }

    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addUIInitListener(
                uiInitEvent -> uiInitEvent.getUI().addBeforeEnterListener(viewAccessChecker)
        );
    }
}
