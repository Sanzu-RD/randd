package com.souchy.randd.jade.api;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;
import com.souchy.randd.situationtest.matrixes.PositionMatrix;
import com.souchy.randd.situationtest.models.org.ContextualObject;
import com.souchy.randd.situationtest.models.org.FightContext;
import com.souchy.randd.situationtest.scripts.Status;

/**
 * 
 * TODO Entities include traps and glyphs ? then they cant have stats.
 * 
 * They need to have event handling / eventbus and procc-able effects tho
 * 
 * ex: onEnterCellEvent pour une trap.
 * 
 * prob just put the @subscribe method in the trap class and register it to the Board's general eventbus when you create a trap instance.
 * then when someone walks, send the event to the board eventbus so that traps can catch it.
 * Or the trap could subscribe to the cell's eventbus.
 * Every cell can have an eventbus, that would be cool too
 * 
 * 
 * Is a Cell an entity ?
 * 
 * @author Souchy
 *
 */
public abstract class AEntity extends ContextualObject implements EventProxy, Identifiable<Integer> {
	
	/**
	 * Matrix representing the cells occupied by the entity
	 * @return
	 */
//	public PositionMatrix getOccupiedCells();

	private final int id;
	protected PositionMatrix position;
	
	/** This entity's current buffs */
	public final List<Status> statuss;
	
	private final EventBus bus = new EventBus();
	
	
	public AEntity(FightContext context, final int id) {
		super(context);
		this.id = id;
		statuss = new ArrayList<>();
	}
	
	@Override
	public EventBus bus() {
		return bus;
	}

	@Override
	public Integer getID() {
		return id;
	}
	
	//@Override
	public PositionMatrix getPos() {
		return position;
	}
	
	
}
