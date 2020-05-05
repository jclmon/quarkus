package org.acme.quickstart;

import javax.ws.rs.HeaderParam;

/**
 * No es necesario pero podemos usar un POJO para agrupar datos del HEADER
 */
public class WorldClockHeaders {

    @HeaderParam("X-Logger")
    String logger;

}