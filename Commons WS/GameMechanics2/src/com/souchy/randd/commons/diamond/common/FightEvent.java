package com.souchy.randd.commons.diamond.common;

public interface FightEvent {
	
	/*
	public static class OnTurnStart implements FightEvent {
		public Creature target;
	}
	public static class OnTurnEnd implements FightEvent {
		public Creature target;
	}
	public static class OnRoundStart implements FightEvent {
		
	}
	public static class OnRoundEnd implements FightEvent {
		
	}
	public static class OnWalk implements FightEvent {
		public Creature target;
		public Cell origin;
		public int distanceWalked;
	}
	public static class OnDeath implements FightEvent {
		public Entity source;
		public Creature target;
	}
	/** Used to check if you can cast an action * /
	public static class OnCanCastAction implements FightEvent {
		public Action action;
		public boolean canCast = true;
	}
	public static class OnActionResolved implements FightEvent {
		public Action action;
	}
	public static class OnEleDmgInstance implements FightEvent {
		public Entity source;
		public Entity target;
		public Map<st, Double> eledmg;
	}
	public static class OnLifeDmgInstance implements FightEvent {
		public Entity source;
		public Entity target;
		public int lifeShield;
		public int life;
		public OnLifeDmgInstance(int lifeShield, int life) { 
			this.lifeShield = lifeShield; 
			this.life = life; 
		}
	}
	public static class OnManaDmgInstance implements FightEvent {
		public Entity source;
		public Entity target;
		public int manaShield;
		public int mana;
		public OnManaDmgInstance(int manaShield, int mana) { 
			this.manaShield = manaShield; 
			this.mana = mana; 
		}
	}
	public static class OnEnterCell implements FightEvent {
		public Creature creature;
		public Cell cell;
	}
	public static class OnLeaveCell implements FightEvent {
		public Creature creature;
		public Cell cell;
	}
	public static class IsVisible implements FightEvent {
		public Entity target;
		/** if the target has an invisibility status * /
		public boolean invisible = false;
		/** 
		 * If the target is 'revealed' by a status 
		 * If the target is invisible, this will reveal it. 
		 * If it is visible, it will remain visible even if 'revealed' is false
		 * /
		public boolean revealed = false;
		public IsVisible(Entity target) { this.target = target; }
	}
	
	*/
//	public static class OnStatusExpire implements FightEvent {
//		public Status status;
//	}
	

//	public static interface OnStatusExpireHandler {
//		@Subscribe
//		public void onStatusExpire(OnStatusExpire e);
//	}
	
}
