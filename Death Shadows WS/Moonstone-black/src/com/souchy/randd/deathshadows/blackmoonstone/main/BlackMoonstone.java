package com.souchy.randd.deathshadows.blackmoonstone.main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.souchy.randd.deathshadow.core.DeathShadowCore;
import com.souchy.randd.deathshadow.core.DeathShadowTCP;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.SelfIdentify;
import com.souchy.randd.jade.meta.User;
import com.souchy.randd.jade.meta.UserLevel;

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
		
		// create a mock fight for tests
		if(Arrays.asList(args).contains("mock")) {
			var mock = MockFight.createFight();
			fights.put(mock.id, mock);
		}
		
		// register node on pearl
		// rivers.consume("idmaker" () -> {
			int nodeid = 0; // get nodeid from idmaker queue
	//		rivers.send("pearl", new SelfIdentify(nodeid));
		//});
		
		// block here to not just exit the program
		if(!Arrays.asList(args).contains("async"))
			server.block();
	}

	@Override
	protected String[] getRootPackages() {
		return new String[]{ 
				"com.souchy.randd.commons.deathebi.msg", 
				"com.souchy.randd.moonstone", 
				"com.souchy.randd.deathshadows.nodes.moonstone.black",
				"com.souchy.randd.deathshadows.blackmoonstone.handlers",
				"com.souchy.randd.deathshadows.blackmoonstone.riverhandlers"
				};
	}

}
