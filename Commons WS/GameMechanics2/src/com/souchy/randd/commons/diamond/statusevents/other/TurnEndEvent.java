package com.souchy.randd.commons.diamond.statusevents.other;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class TurnEndEvent extends Event {

	public interface OnTurnEndHandler extends Handler { //<OnTurnEndEvent> {
		@Subscribe
		public default void handle0(TurnEndEvent event) {
			if(check(event)) onTurnEnd(event);
		}
		public void onTurnEnd(TurnEndEvent event);
	}

	/** fight, should be in Event class */
	public Fight fight;
	/** full turns */
	public int turn;
	/** player index in the timeline */
	public int index;
	
	public TurnEndEvent(Fight f, int turn, int index) { //Creature source, Cell target, Effect effect) {
		super(null, null, null); //source, target, effect);
		this.fight = f;
		this.turn = turn;
		this.index = index;
	}
	
	@Override
	public TurnEndEvent copy0() {
		return new TurnEndEvent(this.fight, this.turn, this.index); // source, target, effect.copy());
	}
	
}
