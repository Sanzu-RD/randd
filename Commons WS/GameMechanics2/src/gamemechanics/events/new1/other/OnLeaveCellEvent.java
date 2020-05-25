package gamemechanics.events.new1.other;

import com.google.common.eventbus.Subscribe;

import data.new1.Effect;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public class OnLeaveCellEvent extends Event {

	public interface OnLeaveCellHandler extends Handler { //<OnLeaveCellEvent> {
		@Subscribe
		public default void handle0(OnLeaveCellEvent event) {
			if(check(event)) onLeaveCell(event);
		}
		public void onLeaveCell(OnLeaveCellEvent event);
	}
	
	public OnLeaveCellEvent(Entity source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public OnLeaveCellEvent copy0() {
		return new OnLeaveCellEvent(source, target, effect.copy());
	}
	
}
