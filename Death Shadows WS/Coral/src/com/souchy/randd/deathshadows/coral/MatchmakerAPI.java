package com.souchy.randd.deathshadows.coral;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import org.bson.types.ObjectId;

import com.mongodb.client.model.Filters;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.jade.meta.JadeCreature;
import com.souchy.randd.jade.meta.User;
import com.souchy.randd.jade.mm.GameQueue;
import com.souchy.randd.jade.mm.Lobby;
import com.souchy.randd.jade.mm.QueueAnswer;
import com.souchy.randd.jade.mm.Queuee;

/**
 * Opal module for matchmaking
 * 
 * @author Blank
 * @date 24 déc. 2019
 */
public class MatchmakerAPI {

	static final Queuee getDraftQueuee(User client) {
		return Emerald.queue_simple_draft().find(eq(Queuee.name_userid, client)).first();
	}
	static final Queuee getBlindQueuee(User client) {
		return Emerald.queue_simple_blind().find(eq(Queuee.name_userid, client)).first();
	}

	/** Get a user's lobby */
	public Lobby getLobby(User user) {
		return Emerald.lobbies().find(Filters.in(Lobby.name_clients, user._id)).first(); 
	}
	
	/*
	 * Player hits queue -> sendRequest queueBlind
	 * Server creates a Queuee and sends back confirmation
	 * 
	 * Loop Player sends GetLobby over and over
	 * 
	 * 	- Servers sends back a lobby object if there is one
	 * 	- First time the player sees a lobby, he has to send his answer (accept/deny)
	 * 	- Then player does an action based on answers in the lobby
	 * 		- accept/accept -> go to lobby screen
	 * 		- deny/x -> go back to queue
	 * 		- unanswered/x -> wait 20 seconds to have both players answers
	 * 	
	 * Loop Player sends GetLobby even when in lobby to update the teams display
	 * 
	 */
	
	public void subscribeToEvents() {
		
	}
	
	/** returns only when an opponent is found */
	public Object queueBlind(User client) { //, Object jadeteam) {
		
		Queuee user = getBlindQueuee(client);
		if(user == null) getDraftQueuee(client);
		if(user != null) {
			return "already in a queue";
		} else {
			user = new Queuee();
			user.userid = client._id;
			user.mmr = client.mmr;
			user.timeQueued = System.currentTimeMillis();
			user.queue = GameQueue.blind;
			Emerald.queue_simple_blind().insertOne(user);
			return "inserted in blind queue";
		}
	}
	
	/** returns only when an opponent is found */
	public Object queueDraft(User client) {
		Queuee user = getDraftQueuee(client);
		if(user == null) getBlindQueuee(client);
		
		if(user != null) {
			//Emerald.queue_simple_draft().replaceOne(eq(Queuee.name_userid, user));
			//return "updated queued";
			return "already in a queue";
		} else {
			user = new Queuee();
			user.userid = client._id;
			user.mmr = client.mmr;
			user.timeQueued = System.currentTimeMillis();
			user.queue = GameQueue.draft;
			Emerald.queue_simple_draft().insertOne(user);
			return "inserted in draft queue";
		}
	}
	
	/**  */
	public Object queueMock(Object client) {
		return null;
	}

	
	/** to make sure both parties accept the queue, return only when they both select their answer (accept/deny) */
	public boolean acceptQueue(User client, boolean accept) {
		Lobby lobby = getLobby(client); 
		lobby.answers.put(client._id, QueueAnswer.accepted);
		
		while(!lobby.players.stream().map(c -> lobby.answers.get(c)).allMatch(b -> b == QueueAnswer.accepted)) {
			
		}
		return true;
	}
	
	/** when a player locks in a character, to display it on both clients' champselect screen */
	public Object updateTeam(User client, JadeCreature[] jadeteam) {
		Lobby lobby = getLobby(client); 
		if(lobby == null) return "client is in no lobby";
		lobby.jadeteams.put(client._id, jadeteam);
		return "updated player's team";
	}
	
	/** 
	 * when the champselect is ended, create a gameserver and send its information to players somehow 
	 * Spectate is done by asking for this */
	public String getMoonstoneInfo(User client) {
		Lobby lobby = getLobby(client); 
		if(lobby == null) return "client is in no lobby";
		return lobby.moonstoneInfo;
	}
	
	/** might be in data instead, by just getting the match history from opal api and playing the replay on the client side */
	public Object replay() {
		return null;
	}
	
	
}
