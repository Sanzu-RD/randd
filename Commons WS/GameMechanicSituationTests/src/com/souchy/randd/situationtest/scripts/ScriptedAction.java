package com.souchy.randd.situationtest.scripts;

import com.souchy.randd.jade.api.AEntity;
import com.souchy.randd.situationtest.events.Event;

public abstract class ScriptedAction extends Event {

	public ScriptedAction(AEntity source) {
		super(source);
	}

}
