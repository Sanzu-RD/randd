package gamemechanics.models.entities;

import data.new1.spellstats.CreatureStats;
import data.new1.spellstats.Targetting;
import data.new1.timed.StatusList;
import gamemechanics.common.generic.Vector2;
import gamemechanics.events.new1.EventPipeline;
import gamemechanics.models.Fight;
//import gamemechanics.statics.properties.Targeting;
import gamemechanics.statics.properties.Targetability;

public abstract class Entity {

	public static enum Team {
		A,
		B,
		/** C is Neutral */
		C,
	}
	
	/** Fight reference */
	public Fight fight;
	
	/** Team appartenance */
	public Team team;

	/** board position */
	public Vector2 pos;
	
	/** filled and emptied by StatusAdd and StatusLose effects */
	public EventPipeline handlers;

	/** Statuses */
	private StatusList statuses;
	
	/** Properties like pathing,  line of sights, ~~visibility~~, orientation */
	public Targetting targeting;
	
	public Entity() {
		statuses = new StatusList();
		targeting = new Targetting(); // should be in stats
	}
	
	/**
	 * If this entity can cast through an entity
	 */
	public boolean canCastThrough(Entity e) {
		return targeting.can(Targetability.CanCastThroughBlocks) || e.targeting.can(Targetability.CanBeCastedThrough);
	}
	/**
	 * If this entity can target cast on an entity
	 */
	public boolean canCastOn(Entity e) {
		return targeting.can(Targetability.CanCastOnBlocks) || e.targeting.can(Targetability.CanBeCastedOn);
	}
	/**
	 * If this entity can walk through an entity (without stopping on it)
	 */
	public boolean canWalkThrough(Entity e) {
		return targeting.can(Targetability.CanWalkThroughBlocks) || e.targeting.can(Targetability.CanBeWalkedThrough);
	}
	/**
	 * If this entity can target walk and stop on an entity
	 */
	public boolean canWalkOn(Entity e) {
		return targeting.can(Targetability.CanWalkOnBlocks) || e.targeting.can(Targetability.CanBeWalkedOn);
	}
	public boolean isVisible() {
		return this.getStats().visible.value();
	}
	
	
	/**
	 * Default empty stat table.
	 * Override for creatures
	 */
	public CreatureStats getStats() {
		return new CreatureStats();
	}
	
	/**
	 * Default empty status table. 
	 * Override for creatures & cells
	 */
	public StatusList getStatus() {
		return statuses; //new StatusList();
	}
	
	public Cell getCell() {
		return fight.board.cells.get(pos.x, pos.y);
	}
	
	
	
}
