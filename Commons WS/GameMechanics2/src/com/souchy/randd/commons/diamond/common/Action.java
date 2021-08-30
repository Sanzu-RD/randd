package com.souchy.randd.commons.diamond.common;

import java.util.function.Supplier;

import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statusevents.other.TurnEndEvent;
import com.souchy.randd.commons.tealwaters.commons.ActionPipeline.BaseAction;
import com.souchy.randd.commons.tealwaters.logging.Log;

public abstract class Action extends BaseAction implements Supplier<Boolean> {
	
	public static class EndTurnAction extends Action {
		public EndTurnAction(Fight f) {
			super(f);
			this.id = TURN_END;
		}
		@Override
		public void apply() {
			//Log.info("EndTurnAction apply");
			synchronized(fight.timeline) {
				//
				fight.endTurnTimer();
//				var event = new TurnEndEvent(fight, fight.timeline.turn(), fight.timeline.index());
//				fight.statusbus.post(event);
//				fight.bus.post(event);
				fight.timeline.moveUp();
				fight.startTurnTimer();
			}
		}
	}
	
	public static class MoveAction extends Action {
		public MoveAction(Fight f) {
			super(f);
		}
		@Override
		public void apply() {
		}
	}
	
	public static class SpellAction extends Action {
		private final Creature casterC;
		private final Spell spell;
		private final Cell cell;
		public SpellAction(Fight f, int id, int cellX, int cellY) {
			super(f);
			this.id = id;
			this.cellX = cellX;
			this.cellY = cellY;
			casterC = fight.creatures.get(caster);
			spell = fight.spells.get(id);
			cell = fight.board.get(cellX, cellY);
			//Log.info("SpellAction cell : " + cell);
		}
		@Override
		public boolean canApply() {
			Log.format("SpellAction canApply : %s, %s, %s", super.canApply(), spell.canCast(casterC), spell.canTarget(casterC, cell));
			if(!super.canApply()) 
				return false;
			if(id > 0 && !(spell.canCast(casterC) && spell.canTarget(casterC, cell)))
				return false;
//			if(id == 0 && )
			return true;
		}
		@Override
		public void apply() {
			Log.format("SpellAction apply %s %s %s %s", spell.id, spell.getModel().getClass().getSimpleName(), casterC.getModel().getClass().getSimpleName(), cell.pos);
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
	 * turn (aka round, not index) on which it was casted
	 */
	public int turn;
	
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
	
	@Override
	public String toString() {
		return String.format("{ fight %s, turn %s, caster %s, action %s, cell [%s, %s] }", fight.id, turn, caster, id, cellX, cellY);
	}
	
}
