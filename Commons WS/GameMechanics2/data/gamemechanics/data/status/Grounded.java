package gamemechanics.data.status;

import data.new1.timed.Status;
import gamemechanics.events.OnCanCastActionCheck;
import gamemechanics.events.OnCanCastActionCheck.OnCanCastActionHandler;

public class Grounded extends Status implements OnCanCastActionHandler {
	@Override
	public void onCanCastAction(OnCanCastActionCheck e) {
		//if(e.action.id == Constants.MovementActionID) e.canCast = false;
		// cant cast movement spells (but can move by walking)
	}
	@Override
	public void fuse(Status s) {
		refreshSet(s.duration);
	}
}