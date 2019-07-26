package gamemechanics.models;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;

import gamemechanics.common.Action;
import gamemechanics.common.ActionPipeline;
import gamemechanics.models.entities.Creature;


public class Fight {
	
	public EventBus bus;
	
	/**
	 * Board
	 */
	public Board board;
	
	/**
	 * Creature timeline / turn order
	 */
	public List<Creature> timeline;
	
	/**
	 * action pipeline : actions currently on the stack
	 */
	public ActionPipeline pipe; 
	
	/**
	 * action history : past / resolved actions
	 */
	public List<Action> history; 
	
	
	
	public Fight() {
		history = new ArrayList<>();
	}
	
	
	
}
