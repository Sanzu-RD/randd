package com.souchy.randd.deathshadows.coral;

import static com.mongodb.client.model.Filters.eq;

import com.souchy.randd.deathshadow.core.DeathShadowCore;
import com.souchy.randd.deathshadow.core.DeathShadowTCP;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.jade.meta.User;
import com.souchy.randd.jade.mm.Queuee;

/**
 * Coral is the match making server
 * 
 * @author Blank
 * @date 25 déc. 2019
 */
public final class Coral extends DeathShadowCore { //extends Core { //extends DeathShadowCoreServer {

	public final DeathShadowTCP server;
	
	public static void main(String[] args) throws Exception {
		new Coral(args);
	}
	
	public Coral(String[] args) throws Exception {
		super(args);
		int port = 7000;
		if(args.length > 0) port = Integer.parseInt(args[0]);
		
		// start match making thread loops
		new CoralEngine();
		// start server
		server = new DeathShadowTCP(port, this); 
		server.block(); 
	} 
	
	@Override
	protected String[] getRootPackages() {
		return new String[]{ "com.souchy.randd.deathshadows.coral" };
	}


	public static final Queuee getDraftQueuee(User client) {
		return Emerald.queue_simple_draft().find(eq(Queuee.name_userid, client)).first();
	}
	public static final Queuee getBlindQueuee(User client) {
		return Emerald.queue_simple_blind().find(eq(Queuee.name_userid, client)).first();
	}

	
}
