package org.acme.quickstart;

import java.util.HashMap;
import java.util.Map;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class GreetingsQuarkusTestResourceLifecycleManager implements QuarkusTestResourceLifecycleManager {

    /**
     * Sobreescribe alguna de las propiedades
     */
    @Override
    public Map<String, String> start() {
        System.out.println("Se van a ejectuar los test");
        Map<String, String> conf = new HashMap<>();
        conf.put("greetings.message", "MEMORY HELLO");
        return conf;
    }

    @Override
    public void stop() {
        System.out.println("Ya se han ejecutado los test");
    }

}