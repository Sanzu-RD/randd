package com.souchy.randd.situationtest.models.org;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.jade.api.EventProxy;
import com.souchy.randd.jade.api.IEntity;
import com.souchy.randd.situationtest.math.Buffer;
import com.souchy.randd.situationtest.models.stage.Board;
import com.souchy.randd.situationtest.models.stage.Cell;
import com.souchy.randd.situationtest.situations.CacheInjector;


public class FightContext implements EventProxy {

	/** Event bus */
	public EventBus bus = new EventBus();

	/** Cache GuiceInjector */
	public /*final*/ CacheInjector injector;
	
	/* Map */
	public final Board board;
	
	/* queue line of playing entities (player characters and summons) */
	public final Buffer<IEntity> playerQueue; //Queue<IEntity> playerQueue;
	
	/* full list of all the entities on the board, including all players, summons, traps, glyphs */ //(not traps and glyphs anymore since theyre statuses)
	public final List<IEntity> entities;
	
	
	public FightContext() {
		injector = new CacheInjector();
		board = new Board();
		playerQueue = new Buffer<>();
		entities = new ArrayList<>(); // linkedlist ?
	}
	

	/*{
		queue = null;
		IEntity currentPlayer = queue.poll();
		queue.offer(currentPlayer);
	}*/

	
	public static class RoundContext {

		/**
		 * Try to do an action on this cell.
		 * Store the cell here so we can check the conditions in the spell by using the fightcontext.
		 */
		public Cell attemptingTargetCell;
		
		/**
		 * Idk how to do this, list of events maybe ? 
		 * ~~oh wait no it's literally a list of actions, etc <source x cast y spell at z targetcell> i thin~~
		 * 
		 * nope, its really a list of events after an actual action has been made (moving, 
		 */
		//public Queue<Action> actionPipeLine;
		
	}

	

	@Override
	public EventBus bus() {
		return bus;
	}
}
