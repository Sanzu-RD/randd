package gamemechanics.models;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;

import gamemechanics.common.Action;
import gamemechanics.common.ActionPipeline;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.EventPipeline;
import gamemechanics.models.entities.Creature;
import gamemechanics.models.entities.Entity;
import gamemechanics.models.entities.Entity.Team;


public class Fight {
	
	//public EventPipeline bus;
	
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
	public List<Creature> teamC;
	
	/**
	 * action pipeline : actions currently on the stack
	 */
	public ActionPipeline pipe; 
	
	/**
	 * action history : past / resolved actions
	 */
	public List<Action> history; 
	
	
	public Fight() {
		//bus = new EventPipeline();
		
		board = new Board();
		
		timeline = new ArrayList<>();
		teamA = new ArrayList<>();
		teamB = new ArrayList<>();
		teamC = new ArrayList<>();
		
		pipe = new ActionPipeline();
		history = new ArrayList<>();
	}
	
	
	public void add(Creature c, Team team) {
		timeline.add(c);
		switch(team) {
			case A -> teamA.add(c);
			case B -> teamB.add(c);
			case C -> teamC.add(c);
		}
	}
	
	public void postAll(Event event) {
		List<Entity> entities = null;
		entities.forEach(e -> e.handlers.post(e, event));
	}
	
	
}
