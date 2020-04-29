package org.keycloak.examples.rest.movie;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public interface ServicesInterface {
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	String get();
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	String getById(@PathParam("id") String id);

}