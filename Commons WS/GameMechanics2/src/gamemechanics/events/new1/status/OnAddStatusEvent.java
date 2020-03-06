package gamemechanics.events.new1.status;

import com.google.common.eventbus.Subscribe;

import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;
import gamemechanics.data.effects.status.AddStatusEffect;

public class OnAddStatusEvent extends Event {
	
	public interface OnAddStatusHandler extends Handler<OnAddStatusEvent> {
		@Subscribe
		public void onAddStatus(OnAddStatusEvent event);
	}
	
	public OnAddStatusEvent(Entity source, Cell target, AddStatusEffect effect) {
		super(source, target, effect);
	}

	@Override
	public OnAddStatusEvent copy0() {
		return new OnAddStatusEvent(source, target, (AddStatusEffect) effect.copy());
	}

	
}
