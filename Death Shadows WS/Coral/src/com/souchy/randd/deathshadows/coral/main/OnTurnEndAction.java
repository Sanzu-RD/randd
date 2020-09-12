package com.souchy.randd.deathshadows.coral.main;

import java.util.function.Supplier;

import com.souchy.randd.commons.coral.draft.ChangeTurn;
import com.souchy.randd.jade.matchmaking.Lobby;

public class OnTurnEndAction implements Supplier<Boolean> {
	
	private final Lobby lobby;
	private final int turn;
	
	public OnTurnEndAction(Lobby lobby) {
		this.lobby = lobby;
		this.turn = lobby.turn();
	}
	
	@Override
	public Boolean get() {
		if(lobby.turn() != turn) return false;
		
		// passe au prochain tour et envoie message
		var nextturn = turn + 1;
		lobby.turn(nextturn);
		
		var msg = new ChangeTurn(nextturn);
		Coral.broadcast(lobby, msg);
		
		return true;
	}
	
}