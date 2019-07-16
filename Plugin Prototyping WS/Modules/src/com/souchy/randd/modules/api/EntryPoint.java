package com.souchy.randd.modules.api;

import com.google.common.eventbus.EventBus;

/**
 * When modules are loaded, they are given this class which they can use as an entrypoint to affect the parent application
 * 
 * @author Blank
 *
 */
public interface EntryPoint {
	
	public EventBus getBus();

}
