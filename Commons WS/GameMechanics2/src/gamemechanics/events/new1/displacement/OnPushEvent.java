package gamemechanics.events.new1.displacement;

import com.google.common.eventbus.Subscribe;

import gamemechanics.data.effects.displacement.Push;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public class OnPushEvent extends Event {
	
	/** interface for statuses to implement */
	public interface OnPushHandler extends Handler { // <OnPushEvent> {
		@Subscribe
		public default void handle0(OnPushEvent event) {
			if(check(event)) onPush(event);
		}
		public void onPush(OnPushEvent e);
	}
	
	
	public OnPushEvent(Entity caster, Cell target, Push effect) {
		super(caster, target, effect);
	}

	@Override
	public OnPushEvent copy0() {
		return new OnPushEvent(source, target, (Push) effect.copy());
	}
	
}
