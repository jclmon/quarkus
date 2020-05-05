package org.acme.rest;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/clock")
public class MetricsResource {

    private long maxTemperature = 55L;

    @Gauge(name = "MaxTemp", description = "Max Temperature", unit = MetricUnits.NONE)
    public Long getMaxTemp() {
        return maxTemperature;
    }
    
    @Inject
    @RestClient
    WorldClockService worldClockService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(name = "CheckTimeGetNow", description = "Time of get current time in milliseconds", unit = MetricUnits.MILLISECONDS)
    @Counted(name = "Name of Get Time", description = "Numbers of calls")
    public CompletionStage<WorldClock> clock() {
        return worldClockService.getNow("cet");
    }
   
}