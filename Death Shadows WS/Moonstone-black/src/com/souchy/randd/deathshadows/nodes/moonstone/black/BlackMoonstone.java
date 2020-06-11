package com.souchy.randd.deathshadows.nodes.moonstone.black;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.souchy.randd.deathshadow.core.DeathShadowCore;
import com.souchy.randd.deathshadow.core.DeathShadowTCP;

import gamemechanics.models.Fight;

public class BlackMoonstone extends DeathShadowCore {
	
	public static BlackMoonstone moon;
	
	public final DeathShadowTCP server;
	public Map<Integer, Fight> fights;
	
	public static void main(String[] args) throws Exception {
		new BlackMoonstone(args);
	}
	
	public BlackMoonstone(String[] args) throws Exception {
		super(args);
		moon = this;
		int port = 443; // port changeant pour chaque node instance
		if(args.length > 0) port = Integer.parseInt(args[0]);
		server = new DeathShadowTCP(port, this);
		fights = new HashMap<>();
		if(!Arrays.asList(args).contains("async"))
			server.block();
	}

	@Override
	protected String[] getRootPackages() {
		return new String[]{ "com.souchy.randd.commons.deathebi.msg", "com.souchy.randd.moonstone", "com.souchy.randd.deathshadows.nodes.moonstone.black"  };
	}

}
