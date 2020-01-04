package com.souchy.randd.deathshadows.nodes.moonstone.black;

import com.souchy.randd.deathshadow.core.DeathShadowCore;
import com.souchy.randd.deathshadow.core.DeathShadowTCP;

public class BlackMoonstone extends DeathShadowCore {
	
	public final DeathShadowTCP server;
	
	public static void main(String[] args) throws Exception {
		new BlackMoonstone(args);
	}
	
	public BlackMoonstone(String[] args) throws Exception {
		super(args);
		int port = 443;
		if(args.length > 0) port = Integer.parseInt(args[0]);
		server = new DeathShadowTCP(port, this);
		server.block();
	}

	@Override
	protected String[] getRootPackages() {
		return new String[]{ "com.souchy.randd.deathshadows.nodes.moonstone.black" };
	}

}
