package gamemechanics.events.new1.damage;

import com.google.common.eventbus.Subscribe;

import gamemechanics.data.effects.damage.Damage;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.events.new1.displacement.OnPushEvent;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public class OnDmgEvent extends Event {
	
	/** interface for statuses to implement */
	public interface OnDmgHandler extends Handler { //<OnDmgEvent> {
		@Subscribe
		public default void handle0(OnDmgEvent event) {
			if(check(event)) onDmg(event);
		}
		public void onDmg(OnDmgEvent e);
	}
	
	public OnDmgEvent(Entity caster, Cell target, Damage effect) {
		super(caster, target, effect);
	}

	@Override
	public OnDmgEvent copy0() {
		return new OnDmgEvent(source, target, (Damage) effect.copy());
	}
	
}
