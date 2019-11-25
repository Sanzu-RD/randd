package com.souchy.randd.ebishoal.opaline.api;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientProperties;

import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class Opaline { //extends EbiCore {
	
	public static final Client client = ClientBuilder.newClient();
	
	private static final OpalineConf conf;
	
	static {
		conf = JsonConfig.readExternal(OpalineConf.class);
		client.property(ClientProperties.CONNECT_TIMEOUT, conf.connectionTimeout);
		client.property(ClientProperties.READ_TIMEOUT, conf.readTimeout);
	}
	
	public static boolean isOnline() {
		try {
			return target("ping").request().get() != null;
		} catch(ProcessingException e) {
			Log.info("Opaline.isOnline : offline");
			return false;
		}
	}
	
//	@Override
//	public void init() throws Exception {
//		
//	}
//	
//	@Override
//	public void start() {
//		
//	}
//	
//	@Override
//	protected String[] getRootPackages() {
//		return null;
//	}

	/**
	 * 
	 * @param <T> what we read the response as (.readEntity(c);)
	 * @param path
	 * @param c
	 * @return
	 */
	static <T> T get(WebTarget target, Class<T> c) {
		if(conf == null) Log.critical("Impossible to create an Opaline config.");
		return target.request().get().readEntity(c);
	}

	/**
	 * 
	 * @param <T> what we read the response as (.readEntity(c);)
	 * @param path
	 * @param entity - the entity the send
	 * @param c
	 * @return
	 */
	static <T> T post(WebTarget target, Entity<?> entity, Class<T> c, MediaType... mediatypes) {
		if(conf == null) Log.critical("Impossible to create an Opaline config.");
		var response = target.request(mediatypes).post(entity);
		Log.info("Opaline.post : response = " + response);
		if(response.hasEntity())
			return response.readEntity(c);
		else
			return null;
	}
	
	static WebTarget target(String path){
		return Opaline.client.target(conf.getTarget()).path(path);
	}
	
	public static final Test test = new Test();
	public static final News news = new News();
	public static final Authentication auth = new Authentication();
	
	
}
