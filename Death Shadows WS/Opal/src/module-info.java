module com.souchy.randd.deathshadows.opal {
	exports com.souchy.randd.deathshadows.opal;
	exports com.souchy.randd.deathshadows.opal.api;
	exports com.souchy.randd.deathshadows.opal.api.data;

	opens com.souchy.randd.deathshadows.opal.api;
	opens com.souchy.randd.deathshadows.opal.api.authentication;
	
	
	requires transitive com.souchy.randd.commons.Jade;
	requires transitive com.souchy.randd.deathshadows.iolite;
	requires transitive com.souchy.randd.commons.TealWaters;

	requires transitive java.ws.rs;
	requires transitive jersey.common;
	requires transitive jersey.container.netty.http;
	requires transitive jersey.server;
	requires transitive netty.all;
	requires transitive java.xml;
	requires transitive jersey.mvc;
	requires transitive jersey.mvc.freemarker;
	
//	requires jakarta.activation;
//	requires jakarta.servlet.api;
//	requires jakarta.ws.rs.api;
	
	//requires jersey.container.servlet;
	requires transitive jersey.container.servlet.core;
	requires transitive jersey.media.jaxb;
	
//	requires java.json;
	requires transitive java.json.bind;
	requires transitive jdk.httpserver;
	requires transitive freemarker;
	requires transitive jakarta.servlet.api;
	
	requires transitive jersey.container.grizzly2.http;
	requires transitive grizzly.http.server;
	requires transitive java.annotation;
	requires transitive grizzly.framework;
	requires transitive jakarta.inject;
	
	requires transitive com.souchy.randd.commons.opal;
	requires transitive java.xml.bind;
	requires transitive com.google.common;
	requires transitive org.mongodb.bson;
	requires transitive com.souchy.randd.deathshadow.core;
	requires reflections;
}