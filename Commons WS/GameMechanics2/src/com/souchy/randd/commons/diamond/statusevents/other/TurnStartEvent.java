package com.souchy.randd.commons.diamond.statusevents.other;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class TurnStartEvent extends Event {

	public interface OnTurnStartHandler extends Handler { //<OnTurnStartEvent> {
		@Subscribe
		public default void handle0(TurnStartEvent event) {
			if(check(event)) onTurnStart(event);
		}
		public void onTurnStart(TurnStartEvent event);
	}

	/** fight, should be in Event class */
	public Fight fight;
	/** full turns */
	public int turn;
	/** player index in the timeline */
	public int index;
	
	public TurnStartEvent(Fight f, int turn, int index) { //Creature source, Cell target, Effect effect) {
		super(null, null, null); //source, target, effect);
		this.fight = f;
		this.turn = turn;
		this.index = index;
	}
	
	@Override
	public TurnStartEvent copy0() {
		return new TurnStartEvent(this.fight, this.turn, this.index); // source, target, effect.copy());
	}
	
	@Override
	public String testMessage() {
		if(this.level != 0) return "";
		return String.format("fight %s turn %s sta %s", fight.id, turn, index);
	}
	
}
