package gamemechanics.events.new1.other;

import com.google.common.eventbus.Subscribe;

import data.new1.Effect;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public class OnTurnStartEvent extends Event {

	public interface OnTurnStartHandler extends Handler { //<OnTurnStartEvent> {
		@Subscribe
		public default void handle0(OnTurnStartEvent event) {
			if(check(event)) onTurnStart(event);
		}
		public void onTurnStart(OnTurnStartEvent event);
	}
	
	public OnTurnStartEvent(Entity source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public OnTurnStartEvent copy0() {
		return new OnTurnStartEvent(source, target, effect.copy());
	}
	
}
