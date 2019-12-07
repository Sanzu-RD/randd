package com.souchy.randd.deathshadows.opal.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("api/ping/")
public class Ping {

	private String pong = "pong";
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String ping() {
		return pong;
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public String pingXml() {
		return "<?xml version=\"1.0\"?>" + "<ping>" + pong + "</ping>";
	} 

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String pingHtml() {
		return "<html><title>" + pong + "</title>"
				+ "<body><h1>" + pong + "</h1></body></html> ";
	}
	
}