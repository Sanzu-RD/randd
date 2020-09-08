package com.souchy.randd.deathshadows.coral.main;

import java.util.function.Supplier;

import org.bson.types.ObjectId;

import com.souchy.randd.commons.coral.draft.ChangeTurn;
import com.souchy.randd.jade.matchmaking.Lobby;

public class OnTurnEndAction implements Supplier<Boolean> {
	
	private final Lobby lobby;
	private final int playerTurn;
	
	public OnTurnEndAction(Lobby lobby, int turn) {
		this.lobby = lobby;
		this.playerTurn = turn;
	}
	@Override
	public Boolean get() {
		if(lobby.playerTurn() != playerTurn) return false;
		
		
		var msg = new ChangeTurn();
		Coral.broadcast(lobby, msg);
		
		return true;
	}
}