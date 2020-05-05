package org.acme.quickstart;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

/**
 * Utiliza reintentos en el servicio WorldClockService
 */
@Path("/clock")
public class FaultToleranceResource {

    Logger logger = Logger.getLogger(FaultToleranceResource.class);

    @Inject
    @RestClient
    WorldClockService worldClockService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<WorldClock> clock() {
        return worldClockService.getNow("cet");
    }
   
}