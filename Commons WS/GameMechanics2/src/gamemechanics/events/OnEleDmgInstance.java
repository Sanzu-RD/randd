package gamemechanics.events;

import java.util.Map;

import com.google.common.eventbus.Subscribe;

import gamemechanics.common.FightEvent;
import gamemechanics.models.entities.Entity;
import gamemechanics.stats.StatProperty.element;

public class OnEleDmgInstance implements FightEvent {

	public static interface OnEleDmgInstanceHandler {
		@Subscribe
		public void onEleDmgInstance(OnEleDmgInstance e);
	}
	
	public Entity source;
	public Entity target;
	public Map<element, Double> eledmg;
}