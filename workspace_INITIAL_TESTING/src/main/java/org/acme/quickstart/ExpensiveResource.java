package org.acme.quickstart;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/expensive")
public class ExpensiveResource {

    @Inject
    ExpensiveService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int hello() {
        return service.calculate();
    }
    
}