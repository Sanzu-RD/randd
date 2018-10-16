package com.souchy.randd.situationtest.scripts.actions;

import java.util.function.Consumer;

import com.souchy.randd.jade.api.IEntity;
import com.souchy.randd.situationtest.events.cell.EnterCellEvent;
import com.souchy.randd.situationtest.scripts.ScriptedAction;

public class SpawnTrapAction extends ScriptedAction {
	
	public final Consumer<EnterCellEvent> onProcScript;
	
	public SpawnTrapAction(IEntity source, Consumer<EnterCellEvent> onProcScript) {
		super(source);
		this.onProcScript = onProcScript;
	}
	

}
