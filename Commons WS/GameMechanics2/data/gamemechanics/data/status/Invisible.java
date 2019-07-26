package gamemechanics.data.status;

import gamemechanics.events.OnVisibilityCheck;
import gamemechanics.events.OnVisibilityCheck.IsVisibleHandler;
import gamemechanics.status.Status;

/**
 * 
 * Makes an entity invisible from enemies
 * 
 * @author Blank
 *
 */
public class Invisible extends Status implements IsVisibleHandler {
	
	@Override
	public void fuse(Status s) {
		refreshSet(s.duration);
	}

	@Override
	public void isVisible(OnVisibilityCheck e) {
		if(e.target == this.target)
			e.invisible = true;
	}
	
}