package org.acme.quickstart;

import javax.enterprise.context.ApplicationScoped;

/**
 * Es un servicio y le damos un scope
 * 
 * ApplicationScoped es un sigleton
 * RequestScoped es un objeto por cada request
 * SessionScoped es un objeto por sesión
 */
@ApplicationScoped
public class GreetingsService {

    public String toUpperCase(String msg) {
        return msg.toUpperCase();
    }

}