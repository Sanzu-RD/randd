package gamemechanics.events.new1.other;

import com.google.common.eventbus.Subscribe;

import data.new1.Effect;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;

public class OnEnterCellEvent extends Event {

	public interface OnEnterCellHandler extends Handler { // <OnEnterCellEvent> {
		@Subscribe
		public default void handle0(OnEnterCellEvent event) {
			if(check(event)) onEnterCell(event);
		}
		public void onEnterCell(OnEnterCellEvent event);
	}
	
	public OnEnterCellEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public OnEnterCellEvent copy0() {
		return new OnEnterCellEvent(source, target, effect.copy());
	}
	
}
