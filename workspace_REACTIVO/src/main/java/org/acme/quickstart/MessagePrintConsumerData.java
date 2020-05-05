package org.acme.quickstart;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;

/**
 * Consumidor, detecta el cambio conectandose a la cola de mensajes e imprime este cambio
 */
@ApplicationScoped
public class MessagePrintConsumerData {

    /**
     * Recibe los mensajes de kafka
     */
    @Incoming("temperature")
    public void printRandom(int temperatura) {
        System.out.println("La temperatura de la recibida desde la cola de mensajes es: " + temperatura);
    }

}