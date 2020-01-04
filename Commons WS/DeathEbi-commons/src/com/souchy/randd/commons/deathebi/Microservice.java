package com.souchy.randd.commons.deathebi;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.net.netty.NettyBuilder;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.net.netty.bytebuf.multihandlers.BBMessageFactories;
import com.souchy.randd.commons.net.netty.bytebuf.multihandlers.BBMessageHandlers;
import com.souchy.randd.commons.net.netty.bytebuf.reflect.BBMessageDiscoverer;
import com.souchy.randd.commons.net.netty.bytebuf.reflect.BBMessageHandlerDiscoverer;
import com.souchy.randd.commons.tealwaters.commons.ClassInstanciator;
import com.souchy.randd.commons.tealwaters.logging.Log;

import io.netty.channel.Channel;

/**
 * A microservice holds message factories and message handlers for a particular service (ex: opal, coral, beryl, moonstone)
 * 
 * A core application can have multiple microservices
 * 
 * @author Blank
 * @date 25 déc. 2019
 */
public abstract class Microservice {

	public static Microservice buildTCP(int port, String packag) {
		return new Microservice() {
			@Override protected void preinit() { }
			@Override
			protected Channel initNetty(NettyBuilder builder) throws Exception {
				return builder.tcp(port, msgFactories, msgHandlers);
			}
			@Override
			protected String[] getRootPackages() {
				return new String[] { packag };
			}
		};
	}
	
	/**
	 * Communication channel 
	 */
	public Channel channel;
	/**
	 * Message factories used by both netty and rabbitmq communication
	 */
	protected BBMessageFactories msgFactories;
	/**
	 * Message handlers contains both netty handlers and rabbitmq handlers
	 */
	protected BBMessageHandlers msgHandlers = new BBMessageHandlers();

	
	public Microservice() {
		try {
			preinit();
			initMesssages();
			channel = initNetty(new NettyBuilder());
		} catch(Exception e) {
			Log.error("", e);
		}
	}
	
	public void block() throws InterruptedException {
		channel.closeFuture().sync();
	}

	/**
	 * If you have anything to do before initializing messages, handlers and channel
	 */
	protected abstract void preinit();
	
	/**
	 * Initialize a netty connection
	 */
	protected abstract Channel initNetty(NettyBuilder builder) throws Exception;

	/**
	 * Get packages paths for messages and handlers classes
	 */
	protected abstract String[] getRootPackages();
	
	/**
	 * Initialize messages. This includes Smooth River messages for inter-server communication and communication with Pearl manager
	 */
	private void initMesssages() {
		// Init msg factories and handlers
		msgFactories = new BBMessageFactories();
		msgHandlers = new BBMessageHandlers();
		
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

	
}
