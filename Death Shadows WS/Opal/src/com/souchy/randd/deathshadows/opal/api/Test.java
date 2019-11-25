package com.souchy.randd.deathshadows.opal.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class Test {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String index() {
		System.out.println("index test");
		return "hello";
	}
	
	
}
