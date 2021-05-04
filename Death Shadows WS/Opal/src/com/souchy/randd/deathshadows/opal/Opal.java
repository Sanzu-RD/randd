package com.souchy.randd.deathshadows.opal;

import com.souchy.randd.commons.opal.serialization.ObjectIdWriter;
import com.souchy.randd.commons.opal.serialization.UserWriter;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadow.core.DeathShadowCore;
import com.souchy.randd.deathshadow.core.DeathShadowHTTP;
import com.souchy.randd.deathshadow.core.smoothrivers.SelfIdentify;
import com.souchy.randd.deathshadow.core.smoothrivers.SmoothRivers;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.deathshadows.opal.api.data.News;
import com.souchy.randd.deathshadows.opal.api.data.Shop;

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
		this.port = 8000;
		
		if(args.length > 0) port = Integer.parseInt(args[0]);
		if(args.length > 1) ip = args[1];
		
		Log.info("Start Opal on : " + ip + ":" + port);
		server = new DeathShadowHTTP(ip, port, getRootPackages(), News.class, Shop.class, UserWriter.class, ObjectIdWriter.class);
		
		rivers.connect(port);
		SmoothRivers.sendPearl(new SelfIdentify(this));
		
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
