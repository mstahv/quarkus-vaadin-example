package com.example.starter.base;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.auth.ViewAccessChecker;
import jakarta.enterprise.event.Observes;

public class ViewAccessCheckerInit {
    private final ViewAccessChecker viewAccessChecker;

    public ViewAccessCheckerInit() {
        viewAccessChecker = new ViewAccessChecker();
    }

    public void serviceInit(@Observes ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addUIInitListener(
                uiInitEvent -> uiInitEvent.getUI().addBeforeEnterListener(viewAccessChecker)
        );
    }
}
