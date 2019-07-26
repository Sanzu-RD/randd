package gamemechanics.events;

import com.google.common.eventbus.Subscribe;

import gamemechanics.common.FightEvent;
import gamemechanics.models.entities.Entity;

public class OnManaDmgInstance implements FightEvent {

	public static interface OnManaDmgInstanceHandler {
		@Subscribe
		public void onManaDmgInstance(OnManaDmgInstance e);
	}
	
	public Entity source;
	public Entity target;
	public int manaShield;
	public int mana;
	public OnManaDmgInstance(int manaShield, int mana) { 
		this.manaShield = manaShield;
		this.mana = mana;
	}
}
