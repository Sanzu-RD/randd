package gamemechanics.models.entities;

import gamemechanics.common.Vector2;
import gamemechanics.events.OnVisibilityCheck;
import gamemechanics.models.Fight;
import gamemechanics.properties.Properties;
import gamemechanics.properties.Targetability;
import gamemechanics.stats.NewStats;
import gamemechanics.status.StatusList;

public abstract class Entity {

	public static enum Team {
		Neutral,
		A,
		B
	}
	
	/** Fight reference */
	public Fight fight;
	
	/** Team appartenance */
	public Team team;

	/** board position */
	public Vector2 pos;

	/** Statuses */
	private StatusList statuses;
	
	/** Properties like pathing,  line of sights, visibility, orientation */
	public Properties properties;
	

	/**
	 * If this entity can cast through an entity
	 */
	public boolean canCastThrough(Entity e) {
		return properties.can(Targetability.CanCastThroughBlocks) || e.properties.can(Targetability.CanBeCastedThrough);
	}
	/**
	 * If this entity can target cast on an entity
	 */
	public boolean canCastOn(Entity e) {
		return properties.can(Targetability.CanCastOnBlocks) || e.properties.can(Targetability.CanBeCastedOn);
	}
	/**
	 * If this entity can walk through an entity (without stopping on it)
	 */
	public boolean canWalkThrough(Entity e) {
		return properties.can(Targetability.CanWalkThroughBlocks) || e.properties.can(Targetability.CanBeWalkedThrough);
	}
	/**
	 * If this entity can target walk and stop on an entity
	 */
	public boolean canWalkOn(Entity e) {
		return properties.can(Targetability.CanWalkOnBlocks) || e.properties.can(Targetability.CanBeWalkedOn);
	}

	
	/**
	 * Default empty stat table.
	 * Override for creatures
	 */
	public NewStats getStats() {
		return new NewStats();
	}
	/**
	 * Default empty status table. 
	 * Override for creatures, cells
	 */
	public StatusList getStatus() {
		return statuses; //new StatusList();
	}
	
	
	public Cell getCell() {
		return fight.board.cells.get(pos.x, pos.y);
	}
	
	
	
}
