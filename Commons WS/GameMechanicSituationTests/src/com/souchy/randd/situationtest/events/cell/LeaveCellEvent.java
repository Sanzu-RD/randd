package com.souchy.randd.situationtest.events.cell;

import com.souchy.randd.jade.api.IEntity;
import com.souchy.randd.situationtest.models.stage.Cell;

public class LeaveCellEvent extends CellEvent {

	public LeaveCellEvent(IEntity source, Cell target) {
		super(source, target);
	}

}
