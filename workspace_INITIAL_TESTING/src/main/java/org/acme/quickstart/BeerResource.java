package org.acme.quickstart;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/beer")
public class BeerResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBeer(@Valid Beer beer) {
        System.out.println(beer);
        // devuelve un 200
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Beer getBeer() {
        return new Beer("Alambra", 300);
    }
    
}