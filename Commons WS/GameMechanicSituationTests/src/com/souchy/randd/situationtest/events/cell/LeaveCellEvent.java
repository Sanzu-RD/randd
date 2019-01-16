package com.souchy.randd.situationtest.events.cell;

import com.souchy.randd.jade.api.AEntity;
import com.souchy.randd.situationtest.models.map.Cell;

public class LeaveCellEvent extends CellEvent {

	public LeaveCellEvent(AEntity source, Cell target) {
		super(source, target);
	}

}
