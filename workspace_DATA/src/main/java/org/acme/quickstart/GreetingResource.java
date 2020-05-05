package org.acme.quickstart;

import java.net.URI;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/dev")
public class GreetingResource {

    @Inject
    EntityManager em;
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Developer getDev(@PathParam("id")  Long id) {
        return em.find(Developer.class, id);
    }

    @POST    
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createDev(Developer dev) {
        em.persist(dev);
        return Response.created(URI.create("/dev/" + dev.getId())).build();
    }

}