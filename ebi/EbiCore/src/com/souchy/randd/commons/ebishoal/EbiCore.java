package com.souchy.randd.commons.ebishoal;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageDiscoverer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandlerDiscoverer;
import com.souchy.randd.commons.net.netty.bytebuf.multihandlers.BBMessageFactories;
import com.souchy.randd.commons.net.netty.bytebuf.multihandlers.BBMessageHandlers;
import com.souchy.randd.commons.tealwaters.commons.ClassInstanciator;

/**
 * 
 * How to implement and launch an EbiCore : 
 * <ul>
 * <li>	create a class that extends EbiCore </li>
 * <li>	create a public static main(String[] args) method </li>
 * <li>	call launch(new MyCore()) inside it </li>
 * </ul>
 * @author Souchy
 *
 */
public abstract class EbiCore {


	/**
	 * Message factories used by both netty and rabbitmq communication
	 */
	protected BBMessageFactories msgFactories;
	/**
	 * Message handlers contains both netty handlers and rabbitmq handlers
	 */
	protected BBMessageHandlers msgHandlers = new BBMessageHandlers();
	
	/**
	 * Core communication service
	 */
	//protected SmoothRivers rivers;
	
	/**
	 * Called first by Core.launch();
	 * @throws Exception 
	 */
	public abstract void init() throws Exception;
	
	/**
	 * Called last by Core.launch();
	 */
	public abstract void start();

	/**
	 * Sert à savoir ou aller chercher les classes de Message et de MessageHandler pour les Discoverers.
	 * @return - Noms des packages contenant les classes. La recherche est r�cursive. 
	 */
	protected abstract String[] getRootPackages();
	
	/**
	 * Calls : <p><code>core.init(); <p>core.start();</code>
	 * @param core
	 * @throws Exception 
	 */
	public static void launch(EbiCore core) throws Exception {
		core.initMesssages();
		core.init();
		core.start();
	}
	
	/**
	 * Initialize 
	 */
	protected void initMesssages() {
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
		System.out.println("--");
		this.msgFactories.foreach(e -> System.out.println("DeathShadowCore init message ["+e.getKey()+"] ["+e.getValue().getClass().getSimpleName()+"]"));
		System.out.println("--");
		this.msgHandlers.foreach(h -> System.out.println("DeathShadowCore init handler ["+h.getID()+"] ["+h.getClass().getSimpleName()+"] handles ["+h.getMessageClass().getSimpleName()+"]"));
		System.out.println("--");
	}
	
}
