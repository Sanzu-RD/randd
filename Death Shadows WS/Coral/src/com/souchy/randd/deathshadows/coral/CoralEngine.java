package com.souchy.randd.deathshadows.coral;

import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.mongodb.client.FindIterable;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.jade.mm.GameQueue;
import com.souchy.randd.jade.mm.Lobby;
import com.souchy.randd.jade.mm.Queuee;

/**
 * This does the matching between queued people
 * 
 * @author Blank
 * @date 27 déc. 2019
 */
public class CoralEngine {
	
	/*
	 * queued people list : [.........]
	 * 
	 * matched people list : [[..].......]
	 * 
	 */
	
	public CoralEngine() {
		var pool = Executors.newScheduledThreadPool(10);
		pool.scheduleWithFixedDelay(this::findMatchDraft, 0, 200, TimeUnit.MILLISECONDS);
		pool.scheduleWithFixedDelay(this::findMatchBlind, 0, 200, TimeUnit.MILLISECONDS);
		pool.scheduleWithFixedDelay(this::findMatchMock, 0, 200, TimeUnit.MILLISECONDS);
	}
	
	public void findMatchDraft() {
		var docs = Emerald.queue_simple_draft().find();
		findMatch(docs, GameQueue.draft);
	}
	public void findMatchBlind() {
		var docs = Emerald.queue_simple_blind().find();
		findMatch(docs, GameQueue.blind);
	}
	public void findMatchMock() {
		// scroll all people in the draft queue
//		var docs = Emerald.queue_simple_blind().find();
//		findMatch(docs, GameQueue.draft);
	}
	
	public void findMatch(FindIterable<Queuee> docs, GameQueue queue) {
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
					itor1.remove();
					itor2.remove();
					itor1.close();
					itor2.close();
					
					Lobby lobby = new Lobby();
					lobby.players.add(p1.userid);
					lobby.players.add(p2.userid);
					
					
					// sendMessageMatch(lobby);
					return;
				}
			}
		}
	}

	/**
	 * Most simple matchmaking is to take the first 2 players in the queue
	 */
	public void exampleMatchSimple(Queue<Object> queue) {
		if(queue.size() >= 2) {
			var p1 = queue.poll();
			var p2 = queue.poll();
			// sendMessageMatch(p1, p2);
		}
	}
	
	/*	
	public void exampleMatch(LinkedList<Queuee> queue) {
		if(queue.size() >= 2) {
			Lobby lobby = new Lobby();

			var itor1 = queue.iterator();
			while(itor1.hasNext()) {
				var p1 = itor1.next();

				var itor2 = queue.iterator();
				while(itor2.hasNext()) {
					var p2 = itor2.next();
					if(canMatch(p1, p2)) {
						itor1.remove();
						itor2.remove();
						
						// sendMessageMatch(lobby);
						return;
					}
				}
			}

		}
	}
	*/

	private boolean canMatch(Queuee p1, Queuee p2) {
		var dm = Math.abs(p1.mmr - p2.mmr);
		var limit = 100;
		var factor1 = p1.timeQueued / (1000 * 60);
		var factor2 = p2.timeQueued / (1000 * 60);
		return dm < limit * (factor1 + factor2);
	}
	
	
}
