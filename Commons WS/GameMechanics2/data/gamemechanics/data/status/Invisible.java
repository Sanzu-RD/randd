package gamemechanics.data.status;

import data.new1.timed.Status;
import gamemechanics.events.OnVisibilityCheck;
import gamemechanics.events.OnVisibilityCheck.IsVisibleHandler;

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