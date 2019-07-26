package gamemechanics.data.status;

import gamemechanics.common.Constants;
import gamemechanics.events.OnCanCastActionCheck;
import gamemechanics.events.OnCanCastActionCheck.OnCanCastActionHandler;
import gamemechanics.status.Status;

public class Muted extends Status implements OnCanCastActionHandler {
	@Override
	public void onCanCastAction(OnCanCastActionCheck e) {
		if(e.action.id != Constants.MovementActionID) e.canCast = false;
	}
	@Override
	public void fuse(Status s) {
		refreshSet(s.duration);
	}
}