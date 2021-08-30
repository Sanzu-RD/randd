package com.souchy.randd.deathshadows.coral.main;

import java.util.function.Supplier;

import com.souchy.randd.commons.coral.draft.ChangeTurn;
import com.souchy.randd.commons.tealwaters.commons.ActionPipeline.BaseAction;
import com.souchy.randd.jade.matchmaking.Lobby;

public class OnTurnEndAction extends BaseAction { // implements Supplier<Boolean> {
	
	private final Lobby lobby;
	private final int turn;
	
	public OnTurnEndAction(Lobby lobby) {
		this.lobby = lobby;
		this.turn = lobby.turn();
	}

	@Override
	public boolean canApply() {
		if(lobby.turn() != turn) return false;
		return true;
	}

	@Override
	public void apply() {
		// passe au prochain tour et envoie message
		var nextturn = turn + 1;
		lobby.turn(nextturn);
		
		var msg = new ChangeTurn(nextturn);
		Coral.broadcast(lobby, msg);
		
	}
	
}