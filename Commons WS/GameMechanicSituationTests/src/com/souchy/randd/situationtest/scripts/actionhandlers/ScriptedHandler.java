package com.souchy.randd.situationtest.scripts.actionhandlers;

import com.souchy.randd.situationtest.eventshandlers.EventHandler;
import com.souchy.randd.situationtest.scripts.ScriptedAction;

@FunctionalInterface
public interface ScriptedHandler<T extends ScriptedAction> extends EventHandler<T> {

}
