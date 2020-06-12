package gamemechanics.events.new1.status;

import com.google.common.eventbus.Subscribe;

import gamemechanics.data.effects.status.AddStatusEffect;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;

public class OnAddStatusEvent extends Event {
	
	public interface OnAddStatusHandler extends Handler { //<OnAddStatusEvent> {
		@Subscribe
		public default void handle0(OnAddStatusEvent event) {
			if(check(event)) onAddStatus(event);
		}
		public void onAddStatus(OnAddStatusEvent event);
	}
	
	public OnAddStatusEvent(Creature source, Cell target, AddStatusEffect effect) {
		super(source, target, effect);
	}

	@Override
	public OnAddStatusEvent copy0() {
		return new OnAddStatusEvent(source, target, (AddStatusEffect) effect.copy());
	}

	
}
