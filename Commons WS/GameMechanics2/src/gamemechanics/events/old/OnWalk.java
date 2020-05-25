package gamemechanics.events;

import com.google.common.eventbus.Subscribe;


public class OnWalk {

	public static interface OnWalkHandler {
		@Subscribe
		public void onWalk(OnWalk e);
	}
	
}
