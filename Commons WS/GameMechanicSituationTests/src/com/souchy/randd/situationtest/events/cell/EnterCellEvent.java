package com.souchy.randd.situationtest.events.cell;

import com.souchy.randd.jade.api.IEntity;
import com.souchy.randd.situationtest.models.map.Cell;

public class EnterCellEvent extends CellEvent {

	public EnterCellEvent(IEntity source, Cell target) {
		super(source, target);
	} 

}
