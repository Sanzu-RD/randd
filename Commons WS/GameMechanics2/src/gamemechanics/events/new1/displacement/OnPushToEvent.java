package gamemechanics.events.new1.displacement;

import gamemechanics.data.effects.displacement.PushTo;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public class OnPushToEvent extends Event {
	
	/** interface for statuses to implement */
	public interface OnPushToHandler extends Handler<OnPushToEvent> {
		public void handle(OnPushToEvent e);
	}
	
	
	public OnPushToEvent(Entity caster, Cell target, PushTo effect) {
		super(caster, target, effect);
	}

	@Override
	public OnPushToEvent copy0() {
		return new OnPushToEvent(source, target, (PushTo) effect.copy());
	}
	
}
