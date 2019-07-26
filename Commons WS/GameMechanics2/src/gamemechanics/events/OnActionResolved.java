package gamemechanics.events;

import com.google.common.eventbus.Subscribe;

import gamemechanics.common.Action;


public class OnActionResolved {

	public static interface OnActionResolvedHandler {
		@Subscribe
		public void onActionResolved(OnActionResolved e);
	}

	public Action action;
}
