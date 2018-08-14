package com.souchy.randd.commons.tealwaters.responsibilities;

import java.util.HashMap;
import java.util.Map;

import com.souchy.randd.commons.tealwaters.commons.Identifiable;
import com.souchy.randd.commons.tealwaters.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.net.netty.bytebuf.BBMessageHandler;


public abstract class ResponsibilityManager<I, O> {

	private final Map<Integer, Responsibility<I, O>> responsibilities = new HashMap<>();
	
	
	/**
	 * Create and configure a ResponsibilityManager
	 */
	public ResponsibilityManager(){
		configure();
	}
	
	/**
	 * Configure this ResponsibilityManager by <b>add</b>ing all the responsibilities that should be handled by this ResponsibilityManager
	 * <p>
	 * Call <b>add</b>(new Responsibility()); for each responsibility.
	 */
	public abstract void configure();
	
	/**
	 * Adds a new responsibility to handle
	 * @param res
	 */
	public final void add(Responsibility<I, O> res){
		if(res == null) return;
		responsibilities.put(res.getID(), res);
	}

	/**
	 * 
	 * @param id - The responsibility identification number
	 * @return The responsibility 
	 */
	public final Responsibility<I, O> get(int id){
		return responsibilities.get(id);
	}
	
	public final Responsibility<I, O> remove(Responsibility<I, O> res) {
		return responsibilities.remove(res.getID());
	}
	
	public final Responsibility<I, O> remove(int id) {
		return responsibilities.remove(id);
	}
	
	/**
	 * 
	 * @param id
	 * @return True if this ResponsabilityManager can handle the object asked
	 */
	public final boolean canHandle(int id){
		return responsibilities.containsKey(id);
	}
	

	/**
	 * 
	 * @param in - 
	 * @return True if this ResponsabilityManager can handle the object asked
	 */
	public final <T extends Identifiable<Integer>> boolean canHandle(T in){
		return canHandle(in.getID());
	}
	
	public final <T extends  Identifiable<Integer>> void handle(T in) {
		if(canHandle(in)) {
			System.out.println("BBHandlerManager handling message ["+in+"] of ID ["+in.getID()+"]");
			get(in.getID()).handle(in);
		} else {
			System.out.println("BBHandlerManager cant handle message ["+in+"] of ID ["+in.getID()+"]");
		}
	}
	
	
}
