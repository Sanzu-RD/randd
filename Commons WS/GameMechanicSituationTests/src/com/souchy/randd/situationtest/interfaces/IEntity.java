package com.souchy.randd.situationtest.interfaces;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;
import com.souchy.randd.situationtest.math.Point3D;
import com.souchy.randd.situationtest.matrixes.PositionMatrix;
import com.souchy.randd.situationtest.models.org.FightContext;

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
public abstract class IEntity implements EventProxy, Identifiable<Integer> {
	
	/**
	 * Matrix representing the cells occupied by the entity
	 * @return
	 */
//	public PositionMatrix getOccupiedCells();

	private final int id;
	protected final FightContext context;
	protected PositionMatrix position;

	private final EventBus bus = new EventBus();
	
	
	public IEntity(FightContext context, final int id) {
		this.id = id;
		this.context = context;
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
	public PositionMatrix getOccupiedCells() {
		return position;
	}
	
	
}
