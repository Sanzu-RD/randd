package com.souchy.randd.situationtest.models.org;

import java.util.Deque;
import java.util.List;
import java.util.Queue;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.situationtest.interfaces.EventProxy;
import com.souchy.randd.situationtest.interfaces.IEntity;
import com.souchy.randd.situationtest.models.stage.Board;
import com.souchy.randd.situationtest.models.stage.Cell;
import com.souchy.randd.situationtest.situations.CacheInjector;


public class FightContext implements EventProxy {

	/**
	 * Event bus
	 */
	public EventBus bus = new EventBus();
	
	@Override
	public EventBus bus() {
		return bus;
	}
	
	public final CacheInjector injector;
	
	/*
	 * Map
	 */
	public Board board;
	
	/*
	 * queue line of playing entities (player characters and summons)
	 */
	public Queue<IEntity> playerQueue;
	
	/*
	 * full list of all the entities on the board, including all players, summons, traps, glyphs
	 */
	public List<IEntity> entities;
	
	

	/*{
		queue = null;
		IEntity currentPlayer = queue.poll();
		queue.offer(currentPlayer);
	}*/


	
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
	public Queue<Action> actionPipeLine;
	
	
}
