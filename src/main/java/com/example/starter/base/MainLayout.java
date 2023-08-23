package com.example.starter.base;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.server.auth.ViewAccessChecker;
import com.vaadin.quarkus.annotation.UIScoped;
import io.quarkus.oidc.OidcSession;
import io.quarkus.oidc.runtime.OidcUtils;
import io.quarkus.security.runtime.SecurityIdentityAssociation;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.servlet.http.Cookie;
import org.vaadin.firitin.appframework.NavigationItem;
import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.util.BrowserCookie;

@UIScoped
public class MainLayout extends org.vaadin.firitin.appframework.MainLayout {

    VerticalLayout sessionlayout = new VerticalLayout();

    @Inject
    SecurityIdentityAssociation identity;

    @Inject
    OidcSession oidcSession;

    @Override
    protected String getDrawerHeader() {
        return "Quarkus }> samples";
    }

    @PostConstruct
    void postConstruct() {
        addToDrawer(sessionlayout);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        sessionlayout.removeAll();
        if(identity == null || identity.getIdentity().getPrincipal().getName() == null || identity.getIdentity().getPrincipal().getName().isEmpty()) {
            sessionlayout.add(
                new VButton("Login", e -> {
                    // Forward with full page reload to an
                    // URL handled by Quarkus OIDC extension (see application.properties)
                    // TODO figure out how to redirect directly to the OIDC server,
                    // would be cleaner approach
                    UI.getCurrent().getPage().setLocation("/" + BasicView.PATH);
                }).withTooltip("You'll be redirected to to OIDC server to login")
            );
        } else {
            sessionlayout.add(
                    new Paragraph("Current user:" + identity.getIdentity().getPrincipal().getName()),
                    new VButton("Logout", e-> {
                        // This url will be intercepted by
                        // Quarkus OIDC extension and logout will be performed
                        UI.getCurrent().getPage().setLocation("/logout");
                    })
            );

        }

    }

    AccessAnnotationChecker accessAnnotationChecker = new AccessAnnotationChecker();

    @Override
    protected boolean checkAccess(NavigationItem navigationItem) {
        return accessAnnotationChecker.hasAccess(navigationItem.getNavigationTarget());
    }
}
