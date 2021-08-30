package com.souchy.randd.commons.deathebi;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.net.netty.bytebuf.multihandlers.BBMessageFactories;
import com.souchy.randd.commons.net.netty.bytebuf.multihandlers.BBMessageHandlers;
import com.souchy.randd.commons.net.netty.bytebuf.reflect.BBMessageDiscoverer;
import com.souchy.randd.commons.net.netty.bytebuf.reflect.BBMessageHandlerDiscoverer;
import com.souchy.randd.commons.tealwaters.commons.ClassInstanciator;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.commons.tealwaters.logging.Logging;
import com.souchy.randd.deathshadows.smoothrivers.SmoothRivers;

/**
 * A core is an application
 * 
 * A core can have multiple microservices (servers and clients)
 * 
 * @author Blank
 * @date 27 déc. 2019
 */
public abstract class Core {
	
	/**
	 * Core events like packets are thrown here. <br>
	 * Classes can listen to them without being a full blown BBMessageHandler class <br>
	 * Useful for UI updates for example
	 */
	public final EventBus bus = new EventBus();
	
	// this doesnt work because some are clients some are servers. Could have List<MicroserviceClient> and List<MicroserviceServer> tho
	// would need a better name
	//public final List<Microservice> microservices;

	/**
	 * Core communication service
	 */
//	protected SmoothRivers rivers;
	
	/**
	 * Message factories used by both netty and rabbitmq communication
	 */
	protected BBMessageFactories msgFactories;
	/**
	 * Message handlers contains both netty handlers and rabbitmq handlers
	 */
	protected BBMessageHandlers msgHandlers = new BBMessageHandlers(bus);
	

	/**
	 * Use this constructor to initialize your microservices (servers and clients)
	 */
	public Core(String args[]) throws Exception {
		// load smooth rivers conf (has ip, port, and enable bool)
		// init smooth rivers on localhost:port from conf if smoothrivers is enabled in the conf
		// init microservices with their messages and handlers
		// start application
		
		Logging.registerLogModule(this.getClass());
		discoverMesssages();
//		this.bus = new EventBus();
//		this.rivers = new SmoothRivers();
	}
	

	/**
	 * Get packages paths for messages and handlers classes
	 */
	protected abstract String[] getRootPackages();
	
	//protected abstract void initMicroservices() throws Exception;
	
	/**
	 * Initialize messages. This includes Smooth River messages for inter-server communication and communication with Pearl manager
	 */
	private void discoverMesssages() {
		// Init msg factories and handlers
		msgFactories = new BBMessageFactories();
		msgHandlers = new BBMessageHandlers(bus);
		
		// Search for Message and MessageHandler classes
		List<Class<BBMessage>> msgClasses = new ArrayList<>();
		List<Class<BBMessageHandler<BBMessage>>> handlerClasses = new ArrayList<>();
		for(String path : getRootPackages()) {
			msgClasses.addAll(new BBMessageDiscoverer().explore(path)); //new ClassDiscoverer().explore(path, c -> BBMessageHandler.class.isAssignableFrom(c)));
			handlerClasses.addAll(new BBMessageHandlerDiscoverer().explore(path)); //new ClassDiscoverer().explore(path, c -> BBMessage.class.isAssignableFrom(c)));
		}
		// Instantiate all Messages and MessageHandlers and add them to their respective manager
		msgFactories.populate(ClassInstanciator.list(msgClasses)); 
		msgHandlers.populate(ClassInstanciator.list(handlerClasses));
		// Log check what handler handles what message
		Log.info("--");
		this.msgFactories.foreach(e -> Log.info("Microservice init message ["+e.getKey()+"] ["+e.getValue().getClass().getSimpleName()+"]"));
		Log.info("--");
		this.msgHandlers.foreach(h -> Log.info("Microservice init handler ["+h.getID()+"] ["+h.getClass().getSimpleName()+"] handles ["+h.getMessageClass().getSimpleName()+"]"));
		Log.info("--");
	}
	
	public BBMessageFactories getMessages() {
		return msgFactories;
	}
	public BBMessageHandlers getHandlers() {
		return msgHandlers;
	}
	
}
