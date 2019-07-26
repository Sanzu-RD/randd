package gamemechanics.events;

import com.google.common.eventbus.Subscribe;

import gamemechanics.common.FightEvent;
import gamemechanics.models.entities.Entity;

public class OnVisibilityCheck implements FightEvent {

	public static interface IsVisibleHandler {
		@Subscribe
		public void isVisible(OnVisibilityCheck e);
	}
	
	public Entity target;
	/** if the target has an invisibility status */
	public boolean invisible = false;
	/** 
	 * If the target is 'revealed' by a status 
	 * If the target is invisible, this will reveal it. 
	 * If it is visible, it will remain visible even if 'revealed' is false
	 */
	public boolean revealed = false;
	public OnVisibilityCheck(Entity target) { this.target = target; }
	
}
