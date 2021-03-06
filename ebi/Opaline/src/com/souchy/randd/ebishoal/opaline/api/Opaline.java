package com.souchy.randd.ebishoal.opaline.api;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.souchy.randd.commons.opal.serialization.ObjectIdReader;
import com.souchy.randd.commons.opal.serialization.UserReader;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.EbiShoalHTTP;

public class Opaline { //extends EbiShoalHTTP { //extends EbiCore {
	
	private static final OpalineConf conf;
	private static final EbiShoalHTTP http;

	// static init opaline before creating the modules below (news, auth..)
	static {
		conf = JsonConfig.readExternal(OpalineConf.class, "./modules/");
		Log.info("Start Opaline on : " + conf.getTarget());
		http = new EbiShoalHTTP(conf.getTarget(), conf.connectionTimeout, conf.readTimeout);
		http.client.register(UserReader.class);
		http.client.register(ObjectIdReader.class);
	}
	
	public static final Test test = new Test();
	public static final News news = new News();
	public static final Authentication auth = new Authentication();
	
	
	
//	public Opaline(String target, int connectionTimeout, int readTimeout) {
//		super(target, connectionTimeout, readTimeout);
//	}
	
	public static boolean isOnline() {
		return http.isOnline();
	}
	
	static <T> T get(WebTarget target, Class<T> c) {
		if(conf == null) Log.critical("Impossible to create an Opaline config.");
		return http.get(target, c);
	}

	static <T> T get(String target, Class<T> c) {
		if(conf == null) Log.critical("Impossible to create an Opaline config.");
		return http.get(target, c);
	}
	
	static <T> T post(WebTarget target, Entity<?> entity, Class<T> c, MediaType... mediatypes) {
		if(conf == null) Log.critical("Impossible to create an Opaline config.");
		return http.post(target, entity, c, mediatypes);
	}
	
	static WebTarget target(String path){
//		Log.info("root vs conf.getTarget() : " + inst.root + ", " + conf.getTarget() + ", path : " + path);
		return Opaline.http.client.target(conf.getTarget()).path(path);
	}
	
	
	
}
