package org.acme.quickstart;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

/**
 * Se incluye en el contexto CDI
 */
@ApplicationScoped
public class WorldClockChecks {

    @Produces
    @ApplicationScoped
    @Readiness
    HealthCheck readiness() {
        return() -> HealthCheckResponse.named("Readiness injection with CDI").up().build();
    }

    @Produces
    @ApplicationScoped
    @Liveness
    HealthCheck liveness() {
        return() -> HealthCheckResponse.named("Liveness injection with CDI").state(isWorldClockAPIUp()).build();
    }

    boolean isWorldClockAPIUp() {
        try(Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("worldclockapi.com", 80), 1000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
 
    /**
     * Si quisieramos el mismo proceso para readiness y liveness
     */
    @Produces
    @ApplicationScoped
    @Readiness
    @Liveness
    HealthCheck readinessLiveness() {
        return() -> HealthCheckResponse.named("Readiness and Liveness injection with CDI").state(isWorldClockAPIUp()).build();
    }

}