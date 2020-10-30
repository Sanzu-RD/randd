package com.souchy.randd.deathshadows.opal;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadow.core.DeathShadowCore;
import com.souchy.randd.deathshadow.core.DeathShadowHTTP;

/**
 * 
 * HTTP API Server.
 * 
 * Used to fetch db data and authenticate users 
 * 
 * @author Blank
 *
 */
public final class Opal extends DeathShadowCore {

	public final DeathShadowHTTP server;
	
	public static void main(String args[]) throws Exception {
		new Opal(args);
	}
	
	public Opal(String[] args) throws Exception {
		super(args);
		String ip = "localhost";
		int port = 8000;
		if(args.length > 0) ip = args[0];
		if(args.length > 1) port = Integer.parseInt(args[0]);
		
		Log.info("Start Opal on : " + ip + ":" + port);
		server = new DeathShadowHTTP(ip, port, getRootPackages());
		server.block();
	}
	
	@Override
	protected String[] getRootPackages() {
		return new String[] {
				"com.souchy.randd.deathshadows.opal",
				"com.souchy.randd.commons.deathebi.msg", 
				"com.souchy.randd.deathshadow.core.handlers", 
				"com.souchy.randd.deathshadow.core.smoothrivers", 
				
		};
	}

	
}
