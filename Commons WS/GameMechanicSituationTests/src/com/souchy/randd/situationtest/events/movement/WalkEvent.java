package com.souchy.randd.situationtest.events.movement;

import com.souchy.randd.jade.api.IEntity;
import com.souchy.randd.situationtest.events.Event;


/**
 * 
 * Dunno if I should make this a CellEvent
 * 
 * @author Souchy
 *
 */
public class WalkEvent extends Event {

	/**
	 * Nombre de points de movement utilis�s / cellules parcourues en marchant
	 */
	public final int movementPointsUsed;
	
	public WalkEvent(IEntity source, int movementPointsUsed) {
		super(source);
		this.movementPointsUsed = movementPointsUsed;
	}

}