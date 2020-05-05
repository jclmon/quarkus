package org.acme.quickstart;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@Path("/clock")
public class WorldClockResource {

    Logger logger = Logger.getLogger(WorldClockResource.class);

    @Inject
    @RestClient
    WorldClockService worldClockService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public WorldClock clock() {
        logger.debug("World Clock Access");
        WorldClockHeaders wch = new WorldClockHeaders();
        wch.logger = "DEBUG";
        return worldClockService.getNow(wch);
        // return worldClockService.getNowWhere("cet");
    }

    // /**
    //  * Sin usar la especificacion de rest con interfaces se utiliza a bajo nivel de forma directa
    //  * @return
    //  */
    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // public WorldClock clock2() {
    //     return ClientBuilder.newClient().target("http://worldclockapi.com")
    //     .path("/api/json/cet/now")
    //     .request().get(WorldClock.class);
    // }
    
}