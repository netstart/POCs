package org.keycloak.examples.rest.movie;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/")
@Consumes({"application/xml", "application/json"})
@Produces({"application/xml", "application/json"})
public interface ServicesInterface {

	@GET
	String get();

	@GET
	@Path("/v2/{id}")
	String getById(@PathParam("id") String id);

}