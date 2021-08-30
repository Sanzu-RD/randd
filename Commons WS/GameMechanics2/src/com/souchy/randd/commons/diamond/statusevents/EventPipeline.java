package com.souchy.randd.commons.diamond.statusevents;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.tealwaters.ecs.Entity;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class EventPipeline {
	
	
//	public EventBus bus;
//	public EventBus busLast;
	
	// interceptors    // things that block propagation
	// modifiers       // things that modify effect values
	// appliers		   // actually apply effect (reduce hp, displace a creature, send packets...)
	// reactors		   // proc reaction effects propagation
	
	public EventBus interceptors = new EventBus(this.hashCode() + "interceptors");
	public EventBus modifiers = new EventBus(this.hashCode() + "modifiers");
	public EventBus reactors = new EventBus(this.hashCode() + "reactors");
	
	public <T extends Event> void post(T e) { // Entity target, T e) {
		//Log.format("EventPipeline#%s post interceptors %s", this.hashCode(), e);
		interceptors.post(e);
		//Log.format("EventPipeline#%s post modifiers %s", this.hashCode(), e);
		modifiers.post(e);
		//Log.format("EventPipeline#%s post reactors %s", this.hashCode(), e);
		reactors.post(e);
	}
	
	/** StatusAdd effect : */
//	public void register(Status status) {
//		if(status instanceof Handler) {
//			register((Handler) status);
//		}
//	}
	public void register(Handler handler) {
		//Log.format("EventPipeline#%s register %s, %s", this.hashCode(), handler.type(), handler.getClass());
		switch (handler.type()) {
//			case Interceptor : interceptors.register(handler); break;
//			case Modifier : modifiers.register(handler); break;
//			case Reactor : reactors.register(handler); break;
			case Interceptor -> interceptors.register(handler);
			case Modifier -> modifiers.register(handler);
			case Reactor -> reactors.register(handler);
		}
	}

	/** StatusRemove effect : */
//	public void unregister(Status status) {
//		// Status Remove Effect
//		if(status instanceof Handler) {
//			unregister((Handler) status);
//		}
//	}
	public void unregister(Handler handler) {
		Log.format("EventPipeline unregister %s", handler.getClass());
		// Status Remove Effect
		switch (handler.type()) {
//			case Interceptor : interceptors.unregister(handler); break;
//			case Modifier : modifiers.unregister(handler); break;
//			case Reactor : reactors.unregister(handler); break;
			case Interceptor -> interceptors.unregister(handler);
			case Modifier -> modifiers.unregister(handler);
			case Reactor -> reactors.unregister(handler);
		}
	}
	
}
