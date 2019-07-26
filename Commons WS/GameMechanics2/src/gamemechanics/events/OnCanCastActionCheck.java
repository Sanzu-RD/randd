package gamemechanics.events;

import com.google.common.eventbus.Subscribe;

import gamemechanics.common.Action;
import gamemechanics.common.FightEvent;

public class OnCanCastActionCheck implements FightEvent {

	public static interface OnCanCastActionHandler {
		@Subscribe
		public void onCanCastAction(OnCanCastActionCheck e);
	}
	
	public Action action;
	public boolean canCast = true;
}
