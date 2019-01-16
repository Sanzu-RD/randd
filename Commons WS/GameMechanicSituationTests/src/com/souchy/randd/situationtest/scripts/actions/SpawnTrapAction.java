package com.souchy.randd.situationtest.scripts.actions;

import java.util.function.Consumer;

import com.souchy.randd.jade.api.AEntity;
import com.souchy.randd.situationtest.events.cell.EnterCellEvent;
import com.souchy.randd.situationtest.scripts.ScriptedAction;

public class SpawnTrapAction extends ScriptedAction {
	
	public final Consumer<EnterCellEvent> onProcScript;
	
	public SpawnTrapAction(AEntity source, Consumer<EnterCellEvent> onProcScript) {
		super(source);
		this.onProcScript = onProcScript;
	}
	

}
