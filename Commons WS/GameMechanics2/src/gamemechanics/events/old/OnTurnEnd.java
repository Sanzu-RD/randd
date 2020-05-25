package gamemechanics.events;

import com.google.common.eventbus.Subscribe;

import gamemechanics.common.FightEvent;
import gamemechanics.models.entities.Creature;

public class OnTurnEnd implements FightEvent {

	public static interface OnTurnEndHandler {
		@Subscribe
		public void onTurnEnd(OnTurnEnd e);
	}
	
	public Creature target;
}
