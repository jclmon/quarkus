package org.acme.quickstart;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;

/**
 * Consumidor
 */
@ApplicationScoped
public class ConsumerData {

    /**
     * Recibe los mensajes del stream del productor
     */
    @Incoming("my-in-memory")
    public void printRandom(int randomNumber) {
        System.out.println(randomNumber);
    }

}