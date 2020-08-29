package com.souchy.randd.commons.diamond.models;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.souchy.randd.commons.diamond.common.Action;
import com.souchy.randd.commons.diamond.common.Action.EndTurnAction;
import com.souchy.randd.commons.diamond.common.ActionPipeline;
import com.souchy.randd.commons.diamond.common.ecs.Engine;
import com.souchy.randd.commons.diamond.common.ecs.EntityF;
import com.souchy.randd.commons.diamond.common.generic.IndexedList;
import com.souchy.randd.commons.diamond.statusevents.EventPipeline;
import com.souchy.randd.commons.diamond.statusevents.other.TurnEndEvent;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent;
import com.souchy.randd.commons.diamond.systems.CellSystem;
import com.souchy.randd.commons.diamond.systems.CreatureSystem;
import com.souchy.randd.commons.diamond.systems.SpellSystem;
import com.souchy.randd.commons.diamond.systems.StatusSystem;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.jade.matchmaking.Team;
import com.souchy.randd.jade.meta.User;

import io.netty.buffer.ByteBuf;
import io.netty.util.AttributeKey;


public class Fight extends Engine implements Identifiable<Integer>, BBSerializer, BBDeserializer {
	
	public ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
	public Future<?> future;
	

	public static final AttributeKey<Fight> attrkey = AttributeKey.newInstance("fight");

//	public final HashMap<String, Object> components = new HashMap<>();
	
	//public EventPipeline bus;
	
	/**
	 * need a fight id for fight instances on blackmoonstone servers and for clients to join the right fight
	 */
	public int id = ++engineIdCounter;
	
	/**
	 * Time currently remaining on the turn timer
	 */
	public int time;
	
	/**
	 * Board
	 */
	public Board board;

	
	/**
	 * Creature timeline / turn order by creature ID.
	 * Timeline has to be populated manually on the server when a new creature (normal or summon) is created.
	 * Client just copies it in FullUpdateHandler
	 */
	public IndexedList<Integer> timeline; 
	
	public List<Creature> teamA(){
		return creatures.where(c -> c.team == Team.A);
	}
	public List<Creature> teamB(){
		return creatures.where(c -> c.team == Team.B);
	}
	
	/**
	 * action pipeline : actions currently on the stack
	 */
	public ActionPipeline pipe; 
	
	/**
	 * Status event handlers
	 */
	public EventPipeline statusbus;
	
	
	// systems hold and manage instances, theyre cool guys
	// might want a system for Effects and have effect models with ids the same way as status/spells
	public CreatureSystem creatures;
	public CellSystem cells;
	public SpellSystem spells;
	public StatusSystem status;
	
	
	public Fight() {
		super();
		
		this.creatures = new CreatureSystem(this);
		this.cells = new CellSystem(this);
		this.spells = new SpellSystem(this);
		this.status = new StatusSystem(this);
		
		board = new Board(this);
		
		timeline = new IndexedList<>();
		
		pipe = new ActionPipeline();
		statusbus = new EventPipeline();
	}

	/**
	 * Start the turn timer thread and sends a TurnStart event. 
	 * Clients will react to this in status handlers
	 * Servers will react to this in status handlers and by broadcasting a TurnStart message.
	 * Will get cancelled by an EndTurnAction if a player passes his turn.
	 */
	public void startTurnTimer() {
//		Log.format("raw turn start %s %s", timeline.turn(), timeline.index());
		var event = new TurnStartEvent(this, timeline.turn(), timeline.index());
		statusbus.post(event);
		bus.post(event);
	}
	public void endTurnTimer() {
		var event = new TurnEndEvent(this, this.timeline.turn(), this.timeline.index());
		this.statusbus.post(event);
		this.bus.post(event);
	}
	
	

	@Override
	public Integer getID() {
		return id;
	}


	@Override
	public ByteBuf serialize(ByteBuf out) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public BBMessage deserialize(ByteBuf in) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
