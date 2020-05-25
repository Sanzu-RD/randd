package gamemechanics.events;

import com.google.common.eventbus.Subscribe;

import gamemechanics.common.FightEvent;
import gamemechanics.models.entities.Creature;
import gamemechanics.models.entities.Entity;

public class OnDeath implements FightEvent {

	public static interface OnDeathHandler {
		@Subscribe
		public void onDeath(OnDeath e);
	}
	
	public Entity source;
	public Creature target;
}
