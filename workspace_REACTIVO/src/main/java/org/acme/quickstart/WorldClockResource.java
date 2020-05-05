package org.acme.quickstart;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@Path("/clock")
public class WorldClockResource {

    Logger logger = Logger.getLogger(WorldClockResource.class);

    @Inject
    @RestClient
    WorldClockService worldClockService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<List<WorldClock>> clock() {
        // hacer ambas llamadas de forma asincrona
        CompletionStage<WorldClock> cet = worldClockService.getNow("cet");
        // cuando los dos terminen ejecutandolos en paralelo
        return cet.thenCombineAsync(worldClockService.getNow("gmt"), (cetResult, gmtResult) -> Arrays.asList(cetResult, gmtResult));
    }
   
}