package org.acme.quickstart;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import io.smallrye.reactive.messaging.annotations.Emitter;
import io.smallrye.reactive.messaging.annotations.Stream;

@Path("/message")
public class MessageResource {

    /**
     * Enviará mensajes
     */
    @Inject @Stream("generated-temperature")
    Emitter<Integer> emitter;

    @GET
    @Path("/emit/{dato}")
    public void emit(@PathParam("dato") Integer dato) {
        emitter.send(dato);
    }

}