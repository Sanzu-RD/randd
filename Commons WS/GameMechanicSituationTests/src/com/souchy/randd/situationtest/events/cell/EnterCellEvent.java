package com.souchy.randd.situationtest.events.cell;

import com.souchy.randd.situationtest.interfaces.IEntity;
import com.souchy.randd.situationtest.models.stage.Cell;

public class EnterCellEvent extends CellEvent {

	public EnterCellEvent(IEntity source, Cell target) {
		super(source, target);
	} 

}
