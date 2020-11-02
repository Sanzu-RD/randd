package com.souchy.randd.deathshadows.coral.main;

import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.deathshadow.core.DeathShadowCore;
import com.souchy.randd.deathshadow.core.DeathShadowTCP;
import com.souchy.randd.deathshadow.core.smoothrivers.SelfIdentify;
import com.souchy.randd.deathshadow.core.smoothrivers.SmoothRivers;
import com.souchy.randd.jade.matchmaking.GameQueue;
import com.souchy.randd.jade.matchmaking.Lobby;

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
		
		this.port = 7000;
		this.port += queue.ordinal();
		this.queue = GameQueue.mock;
		
		if(args.length > 0) port = Integer.parseInt(args[0]); // override default port
		if(args.length > 1) queue = GameQueue.valueOf(args[1]); // queue
		
		
		Log.info("Coral port " + port + " queue " + queue.toString());

		// init elements
		Elements.values(); 
		// init creatures & spells models
		DiamondModels.instantiate("com.souchy.randd.data.s1");
		
		// start match making thread loops
		var engine = new CoralEngine();
		
		// start server
		server = new DeathShadowTCP(port, this);
		// register the engine on the authenticationfilter handler bus to receive new/terminated client connections
		server.auth.bus.register(engine);
		

		rivers.connect(port);
		SmoothRivers.sendPearl(new SelfIdentify(this));
		
		
		server.block(); 
	} 
	
	/**
	 * Broadcast a message to all users in a lobby
	 */
	public static void broadcast(Lobby lobby, BBMessage msg) {
		for (var uid : lobby.users)
			Coral.coral.server.users.get(uid).writeAndFlush(msg);
	}
	
	@Override
	protected String[] getRootPackages() {
		return new String[]{ 
				"com.souchy.randd.commons.deathebi.msg", 
				"com.souchy.randd.deathshadows.nodes.pearl.messaging",
				"com.souchy.randd.deathshadows.nodes.pearl.messaging.moonstone",
				"com.souchy.randd.deathshadows.coral",
				"com.souchy.randd.commons.coral"
		};
	}
	
	
	
}
