package gamemechanics.events;

import com.google.common.eventbus.Subscribe;

import gamemechanics.common.FightEvent;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;

public class OnLeaveCell implements FightEvent {

	public static interface OnLeaveCellHandler {
		@Subscribe
		public void onLeaveCell(OnLeaveCell e);
	}

	
	public Creature creature;
	public Cell cell;
}
