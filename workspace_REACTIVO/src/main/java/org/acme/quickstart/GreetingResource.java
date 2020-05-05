package org.acme.quickstart;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;
import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.annotations.Emitter;
import io.smallrye.reactive.messaging.annotations.Stream;

@Path("/hello")
public class GreetingResource {

    private AtomicInteger atomicInteger = new AtomicInteger();

    @Inject @Stream("my-in-memory")
    Emitter<Integer> emitter;

    @GET
    @Path("/emit/{dato}")
    public void emit(@PathParam("dato") Integer dato) {
        emitter.send(dato);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<String> hello() {
        return ReactiveStreams.of("h","e","l","l","o")
            .map(String::toUpperCase)
            .toList()
            .run()
            .thenApply(list -> list.toString());
    }

    /**
     * Publica eventos, cada 500 milisegundos publicará un entero autoincrementado
     */
    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<String> publish() {
        return Flowable.interval(500, TimeUnit.MILLISECONDS)
            .map(s -> atomicInteger.getAndIncrement())
            .map(i -> Integer.toString(i));

    }



}