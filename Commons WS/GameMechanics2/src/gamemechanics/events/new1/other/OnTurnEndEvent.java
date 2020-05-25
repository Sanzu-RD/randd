package gamemechanics.events.new1.other;

import com.google.common.eventbus.Subscribe;

import data.new1.Effect;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public class OnTurnEndEvent extends Event {

	public interface OnTurnEndHandler extends Handler { //<OnTurnEndEvent> {
		@Subscribe
		public default void handle0(OnTurnEndEvent event) {
			if(check(event)) onTurnEnd(event);
		}
		public void onTurnEnd(OnTurnEndEvent event);
	}
	
	public OnTurnEndEvent(Entity source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public OnTurnEndEvent copy0() {
		return new OnTurnEndEvent(source, target, effect.copy());
	}
	
}
