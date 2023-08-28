package com.example.starter.base;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.vaadin.firitin.appframework.NavigationItem;
import org.vaadin.firitin.components.button.VButton;

@Dependent
public class MainLayout extends org.vaadin.firitin.appframework.MainLayout {

    VerticalLayout sessionlayout = new VerticalLayout();

    @Inject
    JsonWebToken accessToken;

    public JsonWebToken getAccessToken() {
        /*
         * There seems to be some timing issue in some cases
         * with Vaadin CDI & Quarkus dev mode,
         * where the MainLayout is not treated as a CDI bean.
         * This is a workaround if the accessToke happens
         * to be null (instantiated by Vaadin instead of CDI).
         */
        if (accessToken == null) {
            accessToken = CDI.current().select(JsonWebToken.class).get();
        }
        return accessToken;
    }

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

        if (getAccessToken().getName() != null) {
            sessionlayout.add(
                    new Paragraph("Current user:" + getAccessToken().getName()),
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
