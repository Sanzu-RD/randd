package gamemechanics.events;

import com.google.common.eventbus.Subscribe;

import gamemechanics.common.FightEvent;
import gamemechanics.models.entities.Creature;

public class OnTurnStart implements FightEvent {

	public static interface OnTurnStartHandler {
		@Subscribe
		public void onTurnStart(OnTurnStart e);
	}
	
	public Creature target;
}
