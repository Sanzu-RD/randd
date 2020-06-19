package com.souchy.randd.ebishoal.commons;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientProperties;

import com.souchy.randd.commons.tealwaters.logging.Log;


public class EbiShoalHTTP {

	public final Client client = ClientBuilder.newClient();
	
	public String root;
	
	/**
	 * 
	 */
	public EbiShoalHTTP(String target, int connectionTimeout, int readTimeout) {
		this.root = target; //"http://" + ip + ":" + port;
		client.property(ClientProperties.CONNECT_TIMEOUT, connectionTimeout); // conf.
		client.property(ClientProperties.READ_TIMEOUT, readTimeout); // conf.
	}
	
	public WebTarget target (String path) {
		return client.target(root).path(path);
	}

	public boolean isOnline() {
		try {
			var t = target("ping");
			//Log.info("target : " + t.getUri());
			return t.request().get() != null;
		} catch(ProcessingException e) {
			Log.info("EbiShoalHTTP.isOnline : false"); //, e);
			return false;
		}
	}
	
	/**
	 * 
	 * @param <T> what we read the response as (.readEntity(c);)
	 * @param path
	 * @param c
	 * @return
	 */
	public <T> T get(WebTarget target, Class<T> c) {
		// if(conf == null) Log.critical("Impossible to create an Opaline config.");
		return target.request().get().readEntity(c);
	}
	public <T> T get(String path, Class<T> c) {
		// if(conf == null) Log.critical("Impossible to create an Opaline config.");
		return target(path).request().get().readEntity(c);
	}

	/**
	 * 
	 * @param <T> what we read the response as (.readEntity(c);)
	 * @param path
	 * @param entity - the entity the send
	 * @param c
	 * @return
	 */
	public <T> T post(WebTarget target, Entity<?> entity, Class<T> c, MediaType... mediatypes) {
		//if(conf == null) Log.critical("Impossible to create an Opaline config.");
		var response = target.request(mediatypes).post(entity);
		Log.info("EbiShoalHTTP.post : response = " + response);
		if(response.hasEntity())
			return response.readEntity(c);
		else
			return null;
	}
	
}
