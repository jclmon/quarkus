package org.acme.quickstart;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.reactivex.Flowable;

/**
 * Productor asincrono
 */
@ApplicationScoped
public class ProducerData {


    Random random = new Random();

    /**
     * Salida
     * la salida es de tipo reactivo
     */
    // @Outgoing("my-in-memory")
    // public Flowable<Integer> generate() {
        
    //     return Flowable.interval(5, TimeUnit.MILLISECONDS)
    //     .map(tick -> random.nextInt(100));

    // }


}