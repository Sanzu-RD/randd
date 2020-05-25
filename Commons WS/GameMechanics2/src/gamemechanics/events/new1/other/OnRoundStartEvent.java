package gamemechanics.events.new1.other;

import com.google.common.eventbus.Subscribe;

import data.new1.Effect;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public class OnRoundStartEvent extends Event {

	public interface OnRoundStartHandler extends Handler { //<OnRoundStartEvent> {
		@Subscribe
		public default void handle0(OnRoundStartEvent event) {
			if(check(event)) onRoundStart(event);
		}
		public void onRoundStart(OnRoundStartEvent event);
	}
	
	public OnRoundStartEvent(Entity source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public OnRoundStartEvent copy0() {
		return new OnRoundStartEvent(source, target, effect.copy());
	}
	
}
