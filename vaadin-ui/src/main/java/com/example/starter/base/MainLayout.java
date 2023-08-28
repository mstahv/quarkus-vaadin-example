package com.example.starter.base;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.vaadin.firitin.appframework.NavigationItem;
import org.vaadin.firitin.components.button.VButton;

@Dependent
public class MainLayout extends org.vaadin.firitin.appframework.MainLayout implements RouterLayout {

    VerticalLayout sessionlayout = new VerticalLayout();

    @Inject
    JsonWebToken accessToken;

    @Override
    protected String getDrawerHeader() {
        return "Quarkus }> samples";
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        if (!sessionlayout.isAttached()) {
            addToDrawer(sessionlayout);
        }
        sessionlayout.removeAll();

        if (accessToken.getName() != null) {
            sessionlayout.add(
                    new Paragraph("Current user:" + accessToken.getName()),
                    new VButton("Logout", e -> {
                        // This url will be intercepted by
                        // Quarkus OIDC extension and logout will be performed
                        UI.getCurrent().getPage().setLocation("/logout");
                    })
            );
        } else {
            sessionlayout.add(
                    new VButton("Login", e -> {
                        UI.getCurrent().navigate(BasicView.class);
                    }).withTooltip("You'll be redirected to to OIDC server to login")
            );
        }
    }

    AccessAnnotationChecker accessAnnotationChecker = new AccessAnnotationChecker();

    @Override
    protected boolean checkAccess(NavigationItem navigationItem) {
        return accessAnnotationChecker.hasAccess(navigationItem.getNavigationTarget());
    }
}
