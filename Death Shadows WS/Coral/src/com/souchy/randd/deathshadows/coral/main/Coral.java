package com.souchy.randd.deathshadows.coral.main;

import com.souchy.randd.deathshadow.core.DeathShadowCore;
import com.souchy.randd.deathshadow.core.DeathShadowTCP;
import com.souchy.randd.jade.matchmaking.GameQueue;

/**
 * Coral is the match making server
 * 
 * @author Blank
 * @date 25 déc. 2019
 */
public final class Coral extends DeathShadowCore {
	
	public static Coral coral;

	public final DeathShadowTCP server;
	public GameQueue queue;
	
	
	public static void main(String[] args) throws Exception {
		new Coral(args);
	}
	
	private Coral(String[] args) throws Exception {
		super(args);
		coral = this;
		int port = 7000;
		queue = GameQueue.draft;
		if(args.length > 0) port = Integer.parseInt(args[0]);
		if(args.length > 1) queue = GameQueue.valueOf(args[1]);
		
		// start match making thread loops
		var engine = new CoralEngine();
		
		// start server
		server = new DeathShadowTCP(port, this);
		// register the engine on the authenticationfilter handler bus to receive new/terminated client connections
		server.auth.bus.register(engine);
		server.block(); 
	} 
	
	@Override
	protected String[] getRootPackages() {
		return new String[]{ "com.souchy.randd.deathshadows.coral" };
	}
	
	
	
}
