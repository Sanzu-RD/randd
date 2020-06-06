package com.souchy.randd.deathshadows.nodes.moonstone.black;

import java.util.Arrays;

import com.souchy.randd.deathshadow.core.DeathShadowCore;
import com.souchy.randd.deathshadow.core.DeathShadowTCP;

public class BlackMoonstone extends DeathShadowCore {
	
	public final DeathShadowTCP server;
	
	public static void main(String[] args) throws Exception {
		new BlackMoonstone(args);
	}
	
	public BlackMoonstone(String[] args) throws Exception {
		super(args);
		int port = 443; // port changeant pour chaque node instance
		if(args.length > 0) port = Integer.parseInt(args[0]);
		server = new DeathShadowTCP(port, this);
		if(!Arrays.asList(args).contains("async"))
			server.block();
	}

	@Override
	protected String[] getRootPackages() {
		return new String[]{ "com.souchy.randd.commons.deathebi.msg", "com.souchy.randd.moonstone", "com.souchy.randd.deathshadows.nodes.moonstone.black"  };
	}

}
