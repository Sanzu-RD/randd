package gamemechanics.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;

import data.new1.ecs.Engine;
import data.new1.ecs.Entity;
import gamemechanics.common.Action;
import gamemechanics.common.ActionPipeline;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.EventPipeline;
import gamemechanics.models.Creature.Team;
import gamemechanics.systems.CellSystem;
import gamemechanics.systems.CreatureSystem;
import gamemechanics.systems.SpellSystem;
import gamemechanics.systems.StatusSystem;


public class Fight extends Engine implements Identifiable<Integer> {
	
	//public EventPipeline bus;
	
	/**
	 * need a fight id for fight instances on blackmoonstone servers and for clients to join the right fight
	 */
	public int id;
	
	/**
	 * All entities (cells and creatures)
	 */
//	public Map<Integer, Entity> entities;
	
	/**
	 * Board
	 */
	public Board board;
	
	/**
	 * Creature timeline / turn order
	 */
	public List<Creature> timeline;
	
	public List<Creature> teamA;
	public List<Creature> teamB;
//	public List<Creature> teamC;
	
	/**
	 * action pipeline : actions currently on the stack
	 */
	public ActionPipeline pipe; 
	
	/**
	 * action history : past / resolved actions
	 */
	public List<Action> history; 
	
	/**
	 * Status event handlers
	 */
	public EventPipeline handlers;
	
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
		
		timeline = new ArrayList<>();
		teamA = new ArrayList<>();
		teamB = new ArrayList<>();
//		teamC = new ArrayList<>();
		
		handlers = new EventPipeline();
		
		pipe = new ActionPipeline();
		history = new ArrayList<>();
	}
	
	
	public void add(Creature c, Team team) {
		timeline.add(c);
		switch(team) {
			case A -> teamA.add(c);
			case B -> teamB.add(c);
//			case C -> teamC.add(c);
		}
	}
	
//	public void postAll(Event event) {
//		List<Entity> entities = null;
//		entities.forEach(e -> e.handlers.post(e, event));
//	}


	@Override
	public Integer getID() {
		return id;
	}
	
	
}
