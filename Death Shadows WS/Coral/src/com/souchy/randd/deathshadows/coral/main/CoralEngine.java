package com.souchy.randd.deathshadows.coral.main;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.bson.types.ObjectId;

import com.google.common.eventbus.Subscribe;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.souchy.randd.commons.coral.out.MatchFound;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadow.core.handlers.AuthenticationFilter.UserActiveEvent;
import com.souchy.randd.deathshadow.core.handlers.AuthenticationFilter.UserInactiveEvent;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.jade.matchmaking.GameQueue;
import com.souchy.randd.jade.matchmaking.Lobby;
import com.souchy.randd.jade.matchmaking.Lobby.LobbyPhase;
import com.souchy.randd.jade.matchmaking.Queuee;
import com.souchy.randd.jade.matchmaking.Team;

/**
 * This does the matching between people of a certain queue
 * 
 * @author Blank
 * @date 27 déc. 2019
 */
@SuppressWarnings("unchecked")
public class CoralEngine {

	public CoralEngine() {
		var pool = Executors.newScheduledThreadPool(10);
		if(Coral.coral.queue == GameQueue.mock) {
			pool.scheduleWithFixedDelay(this::findMatchMock, 0, 200, TimeUnit.MILLISECONDS);
		} else {
			pool.scheduleWithFixedDelay(this::findMatch, 0, 200, TimeUnit.MILLISECONDS);
		}
	}
	
	/**
	 * When a new client is connected to the server : Enqueue
	 */
	@Subscribe
	public void channelActive(UserActiveEvent event) throws Exception {
		Log.info("CoralEngine channel active " + event.user);
		
		var user = event.user;
		if(user == null) {
			Log.info("CoralEngine channel active closing because no user");
			event.ctx.channel().close();
		}
		
		// start by removing the user from any queue
//		for(var queue : GameQueue.values()) {
//			Emerald.collection(queue.getQueueeClass()).deleteOne(Filters.eq(user._id));
//		}
		
		var q = Coral.coral.queue;
		var queuee = q.createQueuee();
//		queuee.userid = user._id;
		queuee._id = user._id;
		queuee.mmr = user.mmr;
		queuee.timeQueued = System.currentTimeMillis();
		Emerald.collection(q.getQueueeClass()).insertOne(queuee);
	}

	/**
	 * When a client's connection is lost : Dequeue
	 */
	@Subscribe
	public void channelInactive(UserInactiveEvent event) throws Exception {
		var filter = Filters.eq(event.user._id);
		Log.info("CoralEngine " + Coral.coral.queue.getQueueeClass() + " channel inactive " + filter);
		
		// remove the user from the queue
		dequeue(event.user._id);
//		Emerald.collection(Coral.coral.queue.getQueueeClass()).deleteOne(filter);
		
		// if the client was in a lobby, close every participant's channel in the lobby
		var lobby = event.ctx.channel().attr(Lobby.attrkey).get();
		if(lobby != null) {
			for(var userid : lobby.teams.keySet()) {
				var channel = Coral.coral.server.users.get(userid);
				if(channel != null) 
					channel.close();
			}
		}
	}
	
	private void dequeue(ObjectId userid) {
		Emerald.collection(Coral.coral.queue.getQueueeClass()).deleteOne(Filters.eq(userid));
	}
	
	/**
	 * mock queue starts a lobby instantly with only 1 player
	 */
	private void findMatchMock() {
		var docs = ((MongoCollection<Queuee>) Emerald.collection(Coral.coral.queue.getQueueeClass())).find();

		// find 1st player
		var itor1 = docs.iterator();
		while(itor1.hasNext()) {
			var p1 = itor1.next();

			var channel1 = Coral.coral.server.users.get(p1._id);
			
			Lobby lobby = new Lobby();
			lobby.type = GameQueue.mock;
//			lobby.users.add(p1._id);
			//lobby.users.add(p2._id);
			lobby.teams.put(p1._id, Team.A);
			//lobby.teams.put(p2._id, Team.B);
			lobby.moonstoneInfo = "127.0.0.1:443";
			lobby.playerTurn = p1._id;
			lobby.time = System.currentTimeMillis();
			lobby.phase = LobbyPhase.pick;
			
			var msg = new MatchFound();
			msg.lobby = lobby;
			
			dequeue(p1._id);

			channel1.attr(Lobby.attrkey).set(lobby);
			channel1.writeAndFlush(msg);
			return;
		}
	}
	
	/**
	 * normal queues try to match 2 players of similar mmr
	 */
	private void findMatch() {
		var docs = ((MongoCollection<Queuee>) Emerald.collection(Coral.coral.queue.getQueueeClass())).find();
		
		// find 1st player
		var itor1 = docs.iterator();
		while(itor1.hasNext()) {
			var p1 = itor1.next();

			// find 2nd player
			var itor2 = docs.iterator();
			while(itor2.hasNext()) {
				var p2 = itor2.next();
				
				// if match
				if(canMatch(p1, p2)) {
//					itor1.remove();
//					itor2.remove();
					itor1.close();
					itor2.close();
					
					var channel1 = Coral.coral.server.users.get(p1._id);
					var channel2 = Coral.coral.server.users.get(p2._id);
					
					if(channel1 == null) {
						Coral.coral.server.users.remove(p1._id);
//						Emerald.collection(Coral.coral.queue.getQueueeClass()).deleteOne(Filters.eq(p1._id));
						dequeue(p1._id);
					}
					if(channel2 == null) {
						Coral.coral.server.users.remove(p2._id);
//						Emerald.collection(Coral.coral.queue.getQueueeClass()).deleteOne(Filters.eq(p2._id));
						dequeue(p2._id);
					}
					if(channel1 != null && channel2 != null) {
						Lobby lobby = new Lobby();
						lobby.type = GameQueue.draft;
//						lobby.users.add(p1._id);
//						lobby.users.add(p2._id);
						lobby.teams.put(p1._id, Team.A);
						lobby.teams.put(p2._id, Team.B);
						lobby.moonstoneInfo = "127.0.0.1:443";
						lobby.playerTurn = p1._id;
						lobby.time = System.currentTimeMillis();
						lobby.phase = LobbyPhase.ban;
						
						// dequeue
						dequeue(p1._id);
						dequeue(p2._id);
//						Emerald.collection(Coral.coral.queue.getQueueeClass()).deleteOne(Filters.eq(p1._id));
//						Emerald.collection(Coral.coral.queue.getQueueeClass()).deleteOne(Filters.eq(p2._id));
						
						var msg = new MatchFound();
						msg.lobby = lobby;
						
						channel1.attr(Lobby.attrkey).set(lobby);
						channel2.attr(Lobby.attrkey).set(lobby);
						channel1.writeAndFlush(msg);
						channel2.writeAndFlush(msg);
					}
					
					return;
				}
			}
		}
	}
	
	/**
	 * Check whether two players should play against each other according to their MMR and time queued. <br>
	 * 
	 * The maximum difference between two MMRs grows with time queued to insure queue times aren't too long.
	 */
	private boolean canMatch(Queuee p1, Queuee p2) {
		var dm = Math.abs(p1.mmr - p2.mmr);
		var limit = 100;
		var factor1 = p1.timeQueued / (1000 * 60);
		var factor2 = p2.timeQueued / (1000 * 60);
		return dm < limit * (factor1 + factor2);
	}
	
	
}
