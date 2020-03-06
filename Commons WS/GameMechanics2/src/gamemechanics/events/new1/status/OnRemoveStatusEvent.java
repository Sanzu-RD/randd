package gamemechanics.events.new1.status;

import com.google.common.eventbus.Subscribe;

import data.new1.Effect;
import data.new1.timed.Status;
import gamemechanics.common.FightEvent;
import gamemechanics.data.effects.status.RemoveStatusEffect;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public class OnRemoveStatusEvent extends Event {

	public interface OnRemoveStatusHandler extends Handler<OnRemoveStatusEvent> {
		@Subscribe
		public void onRemoveStatus(OnRemoveStatusEvent event);
	}
	
	public OnRemoveStatusEvent(Entity source, Cell target, RemoveStatusEffect effect) {
		super(source, target, effect);
	}

	@Override
	public OnRemoveStatusEvent copy0() {
		return new OnRemoveStatusEvent(source, target, (RemoveStatusEffect) effect.copy());
	}
	
}
