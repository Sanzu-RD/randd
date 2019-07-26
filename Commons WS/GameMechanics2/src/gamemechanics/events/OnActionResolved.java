package gamemechanics.events;

import com.google.common.eventbus.Subscribe;

import gamemechanics.common.Action;
import gamemechanics.common.FightEvent;


public class OnActionResolved implements FightEvent {

	public static interface OnActionResolvedHandler {
		@Subscribe
		public void onActionResolved(OnActionResolved e);
	}

	public Action action;
}
