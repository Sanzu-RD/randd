package com.souchy.randd.situationtest.scripts;

import com.souchy.randd.situationtest.events.Event;
import com.souchy.randd.situationtest.interfaces.IEntity;

public abstract class ScriptedAction extends Event {

	public ScriptedAction(IEntity source) {
		super(source);
	}

}
