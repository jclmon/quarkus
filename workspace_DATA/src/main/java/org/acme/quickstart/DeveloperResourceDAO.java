package org.acme.quickstart;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/dao")
public class DeveloperResourceDAO {

    @Inject
    DeveloperRepository repository;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Developer getDev(@PathParam("id")  Long id) {
        return repository.findById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Developer> getAll() {
        return repository.findAll().list();
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Developer getByName(@PathParam("name")  String name) {
        //Panache construirá la query con estos datos
        return repository.findByName(name);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(Developer dev) {
        repository.persist(dev);
        return Response.created(URI.create("/dev/" + dev.getId())).build();
    }

}