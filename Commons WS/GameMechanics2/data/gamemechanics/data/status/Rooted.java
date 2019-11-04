package gamemechanics.data.status;

import data.new1.timed.Status;
import gamemechanics.common.Constants;
import gamemechanics.events.OnCanCastActionCheck;
import gamemechanics.events.OnCanCastActionCheck.OnCanCastActionHandler;

public class Rooted extends Status implements OnCanCastActionHandler {
	@Override
	public void onCanCastAction(OnCanCastActionCheck e) {
		if(e.action.id == Constants.MovementActionID) e.canCast = false;
	}
	@Override
	public void fuse(Status s) {
		refreshSet(s.duration);
	}
}