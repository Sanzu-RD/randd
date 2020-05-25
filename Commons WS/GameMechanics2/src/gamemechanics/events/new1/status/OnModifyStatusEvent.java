package gamemechanics.events.new1.status;

import com.google.common.eventbus.Subscribe;

import gamemechanics.data.effects.status.ModifyStatusEffect;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public class OnModifyStatusEvent extends Event {

	public interface OnModifyStatusHandler extends Handler { //<OnModifyStatusEvent> {
		@Subscribe
		public default void handle0(OnModifyStatusEvent event) {
			if(check(event)) onModifyStatus(event);
		}
		public void onModifyStatus(OnModifyStatusEvent event);
	}
	
	public OnModifyStatusEvent(Entity source, Cell target, ModifyStatusEffect effect) {
		super(source, target, effect);
	}

	@Override
	public OnModifyStatusEvent copy0() {
		return new OnModifyStatusEvent(source, target, (ModifyStatusEffect) effect.copy());
	}
	
}
