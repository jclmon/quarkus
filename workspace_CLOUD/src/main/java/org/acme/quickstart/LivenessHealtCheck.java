package org.acme.quickstart;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;

@Liveness
public class LivenessHealtCheck implements HealthCheck {

    /**
     * Va a hacer un ping a la api worldclock
     */
    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder hcrb = HealthCheckResponse.named("WorldClockAPIHealtCheck");
        try(Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("worldclockapi.com", 80), 1000);
            hcrb.up();
        } catch (IOException e) {
            hcrb.down();
        }
        return hcrb.build();
    }

}