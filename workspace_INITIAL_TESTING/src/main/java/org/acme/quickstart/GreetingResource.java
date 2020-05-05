package org.acme.quickstart;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@Path("/hello")
public class GreetingResource {

    Logger logger = Logger.getLogger(GreetingResource.class);

    @ConfigProperty(name = "greeting.message")
    String message;

    @Inject
    GreetingsService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        logger.debug("Hello from debug log");
        return service.toUpperCase(message);
    }
    
}