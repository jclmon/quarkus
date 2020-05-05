package org.acme.quickstart;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/entity")
public class DeveloperEntityResource {

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DeveloperEntity getDev(@PathParam("id")  Long id) {
        return DeveloperEntity.findById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DeveloperEntity> getAll() {
        return DeveloperEntity.findAll().list();
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public DeveloperEntity getByName(@PathParam("name")  String name) {
        //Panache construirá la query con estos datos
        return DeveloperEntity.find("name", name).firstResult();
    }

    @GET
    @Path("{name}/{age}")
    @Produces(MediaType.APPLICATION_JSON)
    public DeveloperEntity getByNameAndAge(@NotNull @PathParam("name")  String name, 
                                           @NotNull @PathParam("age")  Integer age) {
        //Panache construirá la query con estos datos
        return DeveloperEntity.find("name = ?1 and age =?2", name, age).firstResult();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createDev(DeveloperEntity dev) {
        dev.persist();
        return Response.created(URI.create("/dev/" + dev.id)).build();
    }

}