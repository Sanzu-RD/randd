package com.souchy.randd.situationtest.models.org;

import java.util.List;

import com.souchy.randd.jade.api.AEntity;
import com.souchy.randd.situationtest.models.Client;

public class Team {
	
	
	public List<AEntity> entities; // contains players and summons, traps, glyphs, etc
	
	public List<Client> players; // player clients
	
	
	/*
	 * broadcast most events to all clients
	 * 
	 * Send specific events to specific clients, ex : your turn start/stop, 
	 * 
	 * L'application client peut s'occuper de 
	 * 
	 */
	
}
