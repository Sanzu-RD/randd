package com.souchy.randd.commons.diamond.common;

import java.util.function.Supplier;

import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.statusevents.other.TurnEndEvent;
import com.souchy.randd.commons.tealwaters.logging.Log;

public abstract class Action implements Supplier<Boolean> {
	
	public static class EndTurnAction extends Action {
		public EndTurnAction(Fight f) {
			super(f);
			this.id = TurnEndActionID;
		}
		@Override
		public void apply() {
			//Log.info("EndTurnAction apply");
			synchronized(fight.timeline) {
				fight.endTurnTimer();
//				var event = new TurnEndEvent(fight, fight.timeline.turn(), fight.timeline.index());
//				fight.statusbus.post(event);
//				fight.bus.post(event);
				fight.timeline.moveUp();
				fight.startTurnTimer();
			}
		}
	}
	
	public static class MoveAction {
	}
	
	public static class SpellAction {
	}
	
	/**
	 * id for ending turn is -1
	 */
	public static final int TurnEndActionID = -1;
	/**
	 * id for moving is 0
	 */
	public static final int MoveActionID = 0;

	
	/**
	 * the fight
	 */
	public Fight fight;
	/**
	 * the action's caster / source (creature id)
	 */
	public int caster;

	/**
	 * action id. 0 = walk (Action.MoveActionID), {1-...} = spells ids
	 */
	public int id; 
	/**
	 * target cell x coordinate
	 */
	public int cellX;
	/**
	 * target cell y coordinate
	 */
	public int cellY;
	
	/**
	 * turn on which it was casted
	 */
	public int turn;
	
	public Action(Fight f) {
		fight = f;
		caster = fight.timeline.current();
		turn = fight.timeline.turn();
	}
	

	@Override
	public Boolean get() {
		if(canApply()) {
			apply();
			return true;
		}
		return false;
	}
	
	/**
	 * can only apply an action if it is still the turn of the creature it was for
	 */
	public boolean canApply() {
		return fight.timeline.turn() == turn && fight.timeline.current() == caster; //caster.id; // fight.currentPlayingCreature.get() == caster.id;
	}
	
	/**
	 * apply/resolve the action on the pipeline
	 */
	public abstract void apply();
	
}
