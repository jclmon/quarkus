package org.acme.quickstart;

import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * Al hacerlo requestscoped los atributos son propios de una request, esto se
 * hace para que cada petición tome su propio token
 */
@Path("/hello")
@RequestScoped
public class JWTResource {

    @Inject
    JsonWebToken token;

    @Inject
    @Claim(standard = Claims.sub)
    String sub;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Set<String> hello() {
        return token.getGroups();
    }
 
    /**
     * Role Base Access Control
     * 
     * Subscriber es uno de los grupos para los que se ha generado el token
     * [Echoer, Group2MappedRole, Tester, Subscriber, group2]
     */
    @GET
    @Path("/roles")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("Subscriber")
    public String helloRoles() {
        return sub;
    }

}