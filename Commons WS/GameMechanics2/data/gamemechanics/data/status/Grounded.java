package gamemechanics.data.status;

import gamemechanics.events.OnCanCastActionCheck;
import gamemechanics.events.OnCanCastActionCheck.OnCanCastActionHandler;
import gamemechanics.status.Status;

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