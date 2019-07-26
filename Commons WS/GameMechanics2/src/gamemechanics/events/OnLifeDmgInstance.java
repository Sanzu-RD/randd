package gamemechanics.events;

import com.google.common.eventbus.Subscribe;

import gamemechanics.common.FightEvent;
import gamemechanics.models.entities.Entity;

public class OnLifeDmgInstance implements FightEvent {

	public static interface OnLifeDmgInstanceHandler {
		@Subscribe
		public void onLifeDmgInstance(OnLifeDmgInstance e);
	}
	
	public Entity source;
	public Entity target;
	public int lifeShield;
	public int life;
	public OnLifeDmgInstance(int lifeShield, int life) { 
		this.lifeShield = lifeShield; 
		this.life = life; 
	}
}
