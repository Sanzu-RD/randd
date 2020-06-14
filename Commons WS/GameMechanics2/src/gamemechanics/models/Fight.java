package gamemechanics.models;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;

import data.new1.ecs.Engine;
import gamemechanics.common.Action;
import gamemechanics.common.ActionPipeline;
import gamemechanics.events.new1.EventPipeline;
import gamemechanics.models.Creature.Team;
import gamemechanics.systems.CellSystem;
import gamemechanics.systems.CreatureSystem;
import gamemechanics.systems.SpellSystem;
import gamemechanics.systems.StatusSystem;
import io.netty.buffer.ByteBuf;
import io.netty.util.AttributeKey;


public class Fight extends Engine implements Identifiable<Integer>, BBSerializer, BBDeserializer {

	public static final AttributeKey<Fight> attrkey = AttributeKey.newInstance("fight");
	
	//public EventPipeline bus;
	
	/**
	 * need a fight id for fight instances on blackmoonstone servers and for clients to join the right fight
	 */
	public int id = ++engineIdCounter;
	
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
	public List<Creature> timeline; // this should just be creature IDS, or at least EntityRefs
	
	public List<Creature> teamA; // this should just be creature IDS 
	public List<Creature> teamB; // this should just be creature IDS 
	
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
