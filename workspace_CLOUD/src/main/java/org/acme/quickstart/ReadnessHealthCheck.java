package org.acme.quickstart;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

@Readiness
public class ReadnessHealthCheck implements HealthCheck {


    @Override
    public HealthCheckResponse call() {     
        HealthCheckResponseBuilder hrb = HealthCheckResponse.named("Greetings");        
        if(databaseIsUP()) {
            hrb.up();
        }else{
            hrb.down();
            hrb.withData("Fallo", "Base de datos no arrancada");
        }        
        return hrb.build();
    }

    private boolean databaseIsUP() {
        return true;
    }

}