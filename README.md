# Quarkus }> examples

This is a slightly more complex starting point for Quarkus based Vaadin apps. In addition to the core example, this has:

 * Top level navigation (via Viritin add-on, automatically pics available views to the menu)
 * Couple of views, of which only one is show by default
 * A simple authentication and authorization functionality with the Quarkus OpenID Connect integration
   * Implemented with KeyCloak as it is super handy with Quarkus automatically launching a KeyCloak instance for you for testing. Should be swappable to any OIDC provider.
   * Configuring ViewAccessChecker that takes care of redirecting to login view if user is not logged in when trying to access a view that requires authentication.
   * TODO Accessing microservice/single-sign-on example: propagate token to an accessed service with the authenticated user's credentials. Here the service is a simple REST service in the same Quarkus app, but in a real world case most likely a separate service using the same OIDC service.

This project can be used as a starting point to create your own Vaadin Flow application for Quarkus. It contains all the necessary configuration with some placeholder files to get you started.

Quarkus 3.0+ requires Java 17.

## Running the Application

Import the project to the IDE of your choosing as a Maven project. 

Run application with `mvn quarkus-dev` (CLI or IDE).

Quarkus will also launch a KeyCloak instance for you with Docker. Thus, the first startup can take a while.

Open [http://localhost:8080/](http://localhost:8080/) in browser.

If you want to run your app locally in production mode, declare OIDC server location (as env variable or in applicaiton.properties), call `mvn package -Pproduction` (Windows), or `./mvn package -Pproduction` (Mac & Linux)
and then
```
java -jar target/quarkus-app/quarkus-run.jar
```

### Including vaadin-jandex for Pro components
If you are using Pro components such GridPro you need to provide the Jandex index for them as well. 
Although, this can be achieved by adding their names one-by-one in the `application.properties` similar to the following example:
```properties
quarkus.index-dependency.vaadin-grid-pro.group-id=com.vaadin
quarkus.index-dependency.vaadin-grid-pro.artifact-id=vaadin-grid-pro-flow
```
Vaadin recommends using the official Jandex index for the Pro components which is published as part of the platform:
```xml
<dependency>
    <groupId>com.vaadin</groupId>
    <artifactId>vaadin-jandex</artifactId>
</dependency>
```
The above dependency has already added to the `pom.xml` and all you need to do is uncomment it when if needed. 
