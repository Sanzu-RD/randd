package com.souchy.randd.commons.diamond.common;

import java.util.function.Supplier;

import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.statusevents.other.TurnEndEvent;
import com.souchy.randd.commons.tealwaters.logging.Log;

public abstract class Action implements Supplier<Boolean> {
	
	public static class EndTurnAction extends Action {
		public EndTurnAction(Fight f) {
			super(f);
			this.id = TURN_END;
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
	
	public static class SpellAction extends Action {
		private final Spell spell;
		private final Cell cell;
		private final Creature casterC;
		public SpellAction(Fight f) {
			super(f);
			spell = fight.spells.get(id);
			cell = fight.board.get(cellX, cellY);
			casterC = fight.creatures.get(caster);
		}
		@Override
		public boolean canApply() {
			if(!super.canApply()) return false;
			if(id > 0 && !(spell.canCast(casterC) && spell.canTarget(casterC, cell))) return false;
//			if(id == 0 && )
			return true;
		}
		@Override
		public void apply() {
			// spell
			if(id > 0) {
				spell.cast(casterC, cell);
			} else
			// movement
			if(id == 0) {
				
			}
		}
	}
	
	/**
	 * id for ending turn is -2
	 */
	public static final int TURN_END = -2;
	/**
	 * id for moving is -1
	 */
	public static final int MOVE = -1;
	/**
	 * id for none is 0
	 */
	public static final int NONE = 0;

	
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
	 * turn (aka round, not index) on which it was casted
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
