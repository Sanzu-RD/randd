package gamemechanics.events.new1.status;

import com.google.common.eventbus.Subscribe;

import gamemechanics.data.effects.status.RemoveStatusEffect;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;

public class OnRemoveStatusEvent extends Event {

	public interface OnRemoveStatusHandler extends Handler { //<OnRemoveStatusEvent> {
		@Subscribe
		public default void handle0(OnRemoveStatusEvent event) {
			if(check(event)) onRemoveStatus(event);
		}
		public void onRemoveStatus(OnRemoveStatusEvent event);
	}
	
	public OnRemoveStatusEvent(Creature source, Cell target, RemoveStatusEffect effect) {
		super(source, target, effect);
	}

	@Override
	public OnRemoveStatusEvent copy0() {
		return new OnRemoveStatusEvent(source, target, (RemoveStatusEffect) effect.copy());
	}
	
}
