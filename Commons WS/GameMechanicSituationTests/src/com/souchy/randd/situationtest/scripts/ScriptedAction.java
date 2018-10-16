package com.souchy.randd.situationtest.scripts;

import com.souchy.randd.jade.api.IEntity;
import com.souchy.randd.situationtest.events.Event;

public abstract class ScriptedAction extends Event {

	public ScriptedAction(IEntity source) {
		super(source);
	}

}
