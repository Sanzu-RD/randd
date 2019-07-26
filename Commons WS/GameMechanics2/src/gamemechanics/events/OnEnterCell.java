package gamemechanics.events;

import com.google.common.eventbus.Subscribe;

import gamemechanics.common.FightEvent;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;

public class OnEnterCell implements FightEvent {
	
	public static interface OnEnterCellHandler {
		@Subscribe
		public void onEnterCell(OnEnterCell e);
	}
	
	public Creature creature;
	public Cell cell;
}
