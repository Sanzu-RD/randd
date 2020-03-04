package gamemechanics.events.new1.damage;

import com.google.common.eventbus.Subscribe;

import gamemechanics.data.effects.damage.Damage;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public class OnDmgEvent extends Event {
	
	/** interface for statuses to implement */
	public interface OnDmgHandler extends Handler<OnDmgEvent> {
		public void handle(OnDmgEvent e);
	}
	
//	public Entity source;
//	public Cell target;
//	public Damage effect;
	
	public OnDmgEvent(Entity caster, Cell target, Damage effect) {
		super(caster, target, effect);
//		this.effect = effect;
//		this.source = caster;
//		this.target = target;
	}

	@Override
	public OnDmgEvent copy0() {
		return new OnDmgEvent(source, target, (Damage) effect.copy());
	}
	
}
