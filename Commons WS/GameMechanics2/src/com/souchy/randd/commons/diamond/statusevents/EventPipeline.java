package com.souchy.randd.commons.diamond.statusevents;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.tealwaters.ecs.Entity;

@SuppressWarnings("preview")
public class EventPipeline {
	
	
//	public EventBus bus;
//	public EventBus busLast;
	
	// interceptors    // things that block propagation
	// modifiers       // things that modify effect values
	// appliers		   // actually apply effect (reduce hp, displace a creature, send packets...)
	// reactors		   // proc reaction effects propagation
	
	public EventBus interceptors = new EventBus("interceptors");
	public EventBus modifiers = new EventBus("modifiers");
	public EventBus reactors = new EventBus("reactors");
	
	public <T extends Event> void post(T e) { // Entity target, T e) {
		interceptors.post(e);
		modifiers.post(e);
		reactors.post(e);
	}
	
	/** StatusAdd effect : */
	public void register(Status status) {
		if(status instanceof Handler) {
			register((Handler) status);
		}
	}
	public void register(Handler handler) {
		switch (handler.type()) {
			case Interceptor : interceptors.register(handler); break;
			case Modifier : modifiers.register(handler); break;
			case Reactor : reactors.register(handler); break;
//			case Interceptor -> interceptors.register(handler);
//			case Modifier -> modifiers.register(handler);
//			case Reactor -> reactors.register(handler);
		}
	}

	/** StatusRemove effect : */
	public void unregister(Status status) {
		// Status Remove Effect
		if(status instanceof Handler) {
			unregister((Handler) status);
		}
	}
	public void unregister(Handler handler) {
		// Status Remove Effect
		switch (handler.type()) {
			case Interceptor : interceptors.unregister(handler); break;
			case Modifier : modifiers.unregister(handler); break;
			case Reactor : reactors.unregister(handler); break;
//			case Interceptor -> interceptors.unregister(handler);
//			case Modifier -> modifiers.unregister(handler);
//			case Reactor -> reactors.unregister(handler);
		}
	}
	
}
