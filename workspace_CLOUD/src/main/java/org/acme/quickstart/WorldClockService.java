package org.acme.quickstart;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletionStage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api")
@RegisterRestClient
public interface WorldClockService {

    
    /**
     * Al incluir CompletionStage ya lo hacemos asincrono
     */
    @GET
    @Path("/json/cet/now")
    @Produces(MediaType.APPLICATION_JSON)
    WorldClock getNow();

    /**
     * Al incluir CompletionStage ya lo hacemos asincrono
     */
    @GET
    @Path("/json/{where}/now")
    @Produces(MediaType.APPLICATION_JSON)
    // @Retry(maxRetries = 3, delay = 5, delayUnit = ChronoUnit.SECONDS)
    @Retry(maxRetries = 3)
    @Fallback(WorldClockFallBack.class)
    CompletionStage<WorldClock> getNow(@PathParam("where") String where);

    @GET
    @Path("/json/cet/now")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * requestVolumeThreshold numero de las ultimas peticiones tenemos que supervisar
     * failureRatio porcentaje de las que pueden fallar para que el circuito se tiene que abrir
     */
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
    @Fallback(WorldClockFallBack.class)
    CompletionStage<WorldClock> getNowCetCircuitBreaker();

}