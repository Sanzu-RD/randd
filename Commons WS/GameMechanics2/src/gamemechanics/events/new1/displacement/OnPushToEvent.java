package gamemechanics.events.new1.displacement;

import com.google.common.eventbus.Subscribe;

import gamemechanics.data.effects.displacement.PushTo;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.events.new1.status.OnRemoveStatusEvent;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public class OnPushToEvent extends Event {
	
	/** interface for statuses to implement */
	public interface OnPushToHandler extends Handler { //<OnPushToEvent> {
		@Subscribe
		public default void handle0(OnPushToEvent event) {
			if(check(event)) onPushTo(event);
		}
		public void onPushTo(OnPushToEvent e);
	}
	
	
	public OnPushToEvent(Entity caster, Cell target, PushTo effect) {
		super(caster, target, effect);
	}

	@Override
	public OnPushToEvent copy0() {
		return new OnPushToEvent(source, target, (PushTo) effect.copy());
	}
	
}
