package com.souchy.randd.deathshadows.coral.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.google.common.eventbus.Subscribe;
import com.mongodb.client.model.Filters;
import com.souchy.randd.commons.coral.out.MatchFound;
import com.souchy.randd.commons.tealwaters.commons.ActionPipeline;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadow.core.handlers.AuthenticationFilter.UserActiveEvent;
import com.souchy.randd.deathshadow.core.handlers.AuthenticationFilter.UserInactiveEvent;
import com.souchy.randd.jade.Constants;
import com.souchy.randd.jade.matchmaking.GameQueue;
import com.souchy.randd.jade.matchmaking.Lobby;
import com.souchy.randd.jade.meta.User;
import com.souchy.randd.jade.matchmaking.Queuee;
import com.souchy.randd.jade.matchmaking.Team;

/**
 * This does the matching between people of a certain queue
 * 
 * @author Blank
 * @date 27 déc. 2019
 */
public class CoralEngine {

	public CoralDB db = new CoralDB.CoralList();
	
	
	public CoralEngine() {
		var pool = Executors.newScheduledThreadPool(10);
		if(Coral.coral.queue == GameQueue.mock) {
			pool.scheduleWithFixedDelay(this::findMatchMock, 0, 200, TimeUnit.MILLISECONDS);
		} else {
			pool.scheduleWithFixedDelay(this::findMatch, 0, 200, TimeUnit.MILLISECONDS);
		}
	}


	public ScheduledExecutorService timers = Executors.newScheduledThreadPool(10);
	
	
	public void startTimer(Lobby lobby) {
//		e.fight.future = e.fight.timer.scheduleAtFixedRate(() -> {
//			if(e.fight.time > 0) e.fight.time--;
//			else e.fight.pipe.push(new EndTurnAction(e.fight));
//		}, 1, 1, TimeUnit.SECONDS);
		
		if(lobby.future != null) lobby.future.cancel(true);
		
		var action = new OnTurnEndAction(lobby);
		lobby.future = timers.schedule(() -> {
			lobby.pipe().push(action);
		}, Constants.baseTimePerTurn, TimeUnit.SECONDS);
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
		db.enqueue(queuee);
		
		Log.info("enqueued (" + q.name() + ") user " + event.user.username);
	}

	/**
	 * When a client's connection is lost : Dequeue
	 */
	@Subscribe
	public void channelInactive(UserInactiveEvent event) throws Exception {
		if(event.user == null) {
			var u = event.ctx.channel().attr(User.attrkey);
			Log.info("Coral user inactive (" + u + "), channel " + event.ctx.channel());
			return;
		}
		var filter = Filters.eq(event.user._id);
		Log.info("CoralEngine " + Coral.coral.queue.getQueueeClass() + " channel inactive " + filter);
		
		// remove the user from the queue
		db.dequeue(event.user._id);
		// Emerald.collection(Coral.coral.queue.getQueueeClass()).deleteOne(filter);
		
		// if the client was in a lobby, close every participant's channel in the lobby
		var lobby = event.ctx.channel().attr(Lobby.attrkey).get();
		if(lobby != null) {
			for(var userid : lobby.users) { // .teams.keySet()) {
				var channel = Coral.coral.server.users.get(userid);
				if(channel != null) 
					channel.close();
			}
		}
	}
	
	
	/**
	 * mock queue starts a lobby instantly with only 1 player
	 */
	private void findMatchMock() {
		db.foreach(p1 -> {
			var channel1 = Coral.coral.server.users.get(p1._id);
			
			Lobby lobby = new Lobby();
			lobby.type = GameQueue.mock;
			lobby.users.add(p1._id);
			lobby.teams.add(Team.A); 
			lobby.jadeteams.add(new ArrayList<>()); // .jadeteams.put(p1._id, new ArrayList<>()); // new JadeCreature[Constants.CreaturesPerTeam]);
			lobby.moonstoneInfo = "127.0.0.1:443";
			lobby.turn(0); // p1._id;
			lobby.time = System.currentTimeMillis();
//			lobby.phase = LobbyPhase.pick;
			
			db.dequeue(p1._id);
			
			var msg = new MatchFound();
			msg.lobby = lobby;
			channel1.attr(Lobby.attrkey).set(lobby);
			channel1.writeAndFlush(msg);
			
			startTimer(lobby);
			return true;
		});
	}
	

	/**
	 * normal queues try to match 2 players of similar mmr
	 */
	private void findMatch() {
//		var docs = ((MongoCollection<Queuee>) Emerald.collection(Coral.coral.queue.getQueueeClass())).find();
//		// find 1st player
//		var itor1 = docs.iterator();
		db.foreach(p1 -> {
			db.foreach(p2 -> {
				// if match
				if(canMatch(p1, p2)) {
					var channel1 = Coral.coral.server.users.get(p1._id);
					var channel2 = Coral.coral.server.users.get(p2._id);
					
					if(channel1 == null) {
						Coral.coral.server.users.remove(p1._id);
						db.dequeue(p1._id);
					}
					if(channel2 == null) {
						Coral.coral.server.users.remove(p2._id);
						db.dequeue(p2._id);
					}
					if(channel1 != null && channel2 != null) {
						Lobby lobby = new Lobby();
						lobby.type = GameQueue.draft;
						lobby.users.add(p1._id);
						lobby.users.add(p2._id);
						lobby.teams.add(Team.A); 
						lobby.teams.add(Team.B); 
						lobby.moonstoneInfo = "127.0.0.1:443";
						lobby.turn(0); // p1._id;
						lobby.time = System.currentTimeMillis();
//						lobby.phase = LobbyPhase.ban;
						
						// dequeue
						db.dequeue(p1._id);
						db.dequeue(p2._id);
						
						var msg = new MatchFound();
						msg.lobby = lobby;
						channel1.attr(Lobby.attrkey).set(lobby);
						channel2.attr(Lobby.attrkey).set(lobby);
						channel1.writeAndFlush(msg);
						channel2.writeAndFlush(msg);

						startTimer(lobby);
					}
					
					return true;
				}
				return false;
			});
			return false;
		});
		
	}
	
	/**
	 * Check whether two players should play against each other according to their MMR and time queued. <br>
	 * 
	 * The maximum difference between two MMRs grows with time queued to insure queue times aren't too long.
	 */
	private boolean canMatch(Queuee p1, Queuee p2) {
		if(p1 == p2) return false;
		var dm = Math.abs(p1.mmr - p2.mmr);
		var limit = 100;
		var factor1 = p1.timeQueued / (1000 * 60);
		var factor2 = p2.timeQueued / (1000 * 60);
		return dm < limit * (factor1 + factor2);
	}
	
	
}
