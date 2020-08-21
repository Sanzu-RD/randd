package com.souchy.randd.deathshadows.greenberyl;

import com.souchy.randd.deathshadow.core.DeathShadowCore;

import tigase.server.XMPPServer;

public class GreenBeryl extends DeathShadowCore {

	public static GreenBeryl beryl;

	public static void main(String[] args) throws Exception {
		new GreenBeryl(args);
	}
	
	
	public GreenBeryl(String[] args) throws Exception {
		super(args);
		beryl = this;
		
		XMPPServer.main(args);
	}


	@Override
	protected String[] getRootPackages() {
		return new String[] { 
					"com.souchy.randd.commons.deathebi.msg", 
					"com.souchy.randd.deathshadows.nodes.pearl.messaging",
					"com.souchy.randd.deathshadows.nodes.pearl.messaging.moonstone",
					"com.souchy.randd.deathshadows.greenberyl"
				};
	}
	
	
}
