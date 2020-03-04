package gamemechanics.statics.properties;

import gamemechanics.models.entities.Entity;

public class Targeting {
	
	private boolean isVisible = true;

	private Orientation orientation = Orientation.North;

	private boolean[] targetability = new boolean[8];
	
	
	public boolean can(Targetability targetting) {
		return targetability[targetting.ordinal()];
	}
	public void setCan(Targetability targetting, boolean should) {
		targetability[targetting.ordinal()] = should;
	}
	
	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	
	
	/**
	 * If this entity can cast through an entity
	 */
	public boolean canCastThrough(Entity e) {
		return can(Targetability.CanCastThroughBlocks) || e.targeting.can(Targetability.CanBeCastedThrough);
	}
	/**
	 * If this entity can target cast on an entity
	 */
	public boolean canCastOn(Entity e) {
		return can(Targetability.CanCastOnBlocks) || e.targeting.can(Targetability.CanBeCastedOn);
	}
	/**
	 * If this entity can walk through an entity (without stopping on it)
	 */
	public boolean canWalkThrough(Entity e) {
		return can(Targetability.CanWalkThroughBlocks) || e.targeting.can(Targetability.CanBeWalkedThrough);
	}
	/**
	 * If this entity can target walk and stop on an entity
	 */
	public boolean canWalkOn(Entity e) {
		return can(Targetability.CanWalkOnBlocks) || e.targeting.can(Targetability.CanBeWalkedOn);
	}
	
	
}
