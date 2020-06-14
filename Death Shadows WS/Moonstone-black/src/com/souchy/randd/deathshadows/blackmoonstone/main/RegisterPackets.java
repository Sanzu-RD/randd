package com.souchy.randd.deathshadows.blackmoonstone.main;

public class RegisterPackets {

	
	/* Register receivers :
	 * 
	 * 
	 * ConnectionHandler ->  FightID + Username + Password then check if PlayerID is actually in the Fight
	 * ActionHandler -> ActionID + CellID, get player from session
	 * Passturn -> rien
	 * 
	 * 
	 */
	
	
	/* Register senders :
	 * 
	 * 
	 * AcknowledgeAction -> ActionID + CellID + True/False, propagate to everyone in the fight
	 * Update/Synch -> send whole board state (fightcontext, every entity, statuses,)
	 * 
	 */
	
	
}
