package com.souchy.randd.situationtest.events.cell;

import com.souchy.randd.jade.api.AEntity;
import com.souchy.randd.situationtest.events.Event;
import com.souchy.randd.situationtest.models.map.Cell;

public abstract class CellEvent extends Event {
	
	public final Cell cell;
	
	public CellEvent(AEntity source, Cell target) {
		super(source);
		this.cell = target;
	}

}
