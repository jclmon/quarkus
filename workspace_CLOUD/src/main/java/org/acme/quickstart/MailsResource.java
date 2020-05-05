package org.acme.quickstart;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.ReactiveMailer;

@Path("/mail")
public class MailsResource {

    /**
     * 
     */
    @Inject
    Mailer mailer;

    /**
     * Devuelve CompletionStage lo hace reactivo
     */
    @Inject
    ReactiveMailer reactiveMailer;

    @Inject
    @RestClient
    WorldClockService worldClockService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public WorldClock clock() {
        WorldClock wc = worldClockService.getNow();
        mailer.send(Mail.withText("clock@sample.com", "Han consultado el tiempo", String.format("La hora cet es: %s", wc.getCurrentDateTime())));
        return wc;
    }
   
}