package gamemechanics.events.new1;

import com.google.common.eventbus.EventBus;

import data.new1.timed.Status;
import gamemechanics.events.new1.Handler.HandlerType;
import gamemechanics.models.entities.Entity;

public class EventPipeline {
	
	
//	public EventBus bus;
//	public EventBus busLast;
	
	// interceptors    // things that block propagation
	// modifiers       // things that modify effect values
	// appliers		   // actually apply effect (reduce hp, displace a creature, send packets...)
	// reactors		   // proc reaction effects propagation
	
	public EventBus interceptors;
	public EventBus modifiers;
	public EventBus reactors;
	
	public <T extends Event> void post(Entity target, T e) {
		interceptors.post(e);
		modifiers.post(e);
		reactors.post(e);
	}

	/** Status Remove Effect : */
	public void unregister(Status status) {
		// Status Remove Effect
		if(status instanceof Handler) {
			Handler handler = (Handler) status;
			switch (handler.type()) {
				case Interceptor -> interceptors.unregister(status);
				case Modifier -> modifiers.unregister(status);
				case Reactor -> reactors.unregister(status);
			}
		}
	}
	
	/** Status Add Effect : */
	public void register(Status status) {
		if(status instanceof Handler) {
			Handler handler = (Handler) status;
			switch (handler.type()) {
				case Interceptor -> interceptors.register(status);
				case Modifier -> modifiers.register(status);
				case Reactor -> reactors.register(status);
			}
		}
	}
	
	
}
