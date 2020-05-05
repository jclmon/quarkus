package org.acme.quickstart;

import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class CounterBean {

    private AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * Se programa el cron cada 3 segundos
     */
    //@Scheduled(every= "3s")
    /**
     * 
     */
    // @Scheduled(cron = "0 15 10 * * ?")
    @Scheduled(every= "{increment.every}")
    void print() {
        System.out.println(atomicInteger.incrementAndGet());
    }
    
}