package com.souchy.randd.situationtest.events.cell;

import com.souchy.randd.situationtest.events.Event;
import com.souchy.randd.situationtest.interfaces.IEntity;
import com.souchy.randd.situationtest.models.stage.Cell;

public abstract class CellEvent extends Event {
	
	public final Cell cell;
	
	public CellEvent(IEntity source, Cell target) {
		super(source);
		this.cell = target;
	}

}
