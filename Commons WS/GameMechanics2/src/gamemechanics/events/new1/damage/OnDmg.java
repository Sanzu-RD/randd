package gamemechanics.events.new1.damage;

import com.google.common.eventbus.Subscribe;

import gamemechanics.data.effects.damage.Damage;
import gamemechanics.events.OnEleDmgInstance;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.models.entities.Entity;

public class OnDmg extends Event {
	
	public interface OnDmgHandler extends Handler {
		@Subscribe
		public void onDmg(OnDmg e);
	}
	
//	public Entity source;
//	public Entity target;
	public Damage effect;
	
	public OnDmg(Damage effect) {
		this.effect = effect;
	}
	
}
