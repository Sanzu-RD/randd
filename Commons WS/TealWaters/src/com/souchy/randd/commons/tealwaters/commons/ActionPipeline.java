package com.souchy.randd.commons.tealwaters.commons;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.commons.tealwaters.logging.Log;

/**
 * Synchronized Action list using a pooled thread to execute the actions in order
 * 
 * 
 * Supplier.get() has to return true if the action has been applied or false if it couldnt be applied.
 * 
 * @author Blank
 * @date 2020-08-25
 */
public class ActionPipeline {
	
	public static abstract class BaseAction {
		public abstract boolean canApply();
		public abstract void apply();
	}
	
	/**
	 * Only the Black Moonstone server sets and registers to this bus to broadcast actions as they are applied
	 */
	private EventBus bus;
	
	
	/**
	 * Thread pool for flushing/executing actions. This is where the actual execution / fight thread comes from
	 */
	private static ExecutorService executors = Executors.newCachedThreadPool();

	/** 
	 * Executes actions on the main thread on the client 
	 */
	private Consumer<Runnable> postRunnable;

	/**
	 * Future determining if a thread is already executing flush()
	 */
	private Future<?> f;
	
	/**
	 * Current action deck
	 */
	private final Deque<BaseAction> q = new ArrayDeque<>();

	/**
	 * action history : past / resolved actions
	 */
	private List<BaseAction> history = new ArrayList<>();
	
	
	/**
	 * ASYNC 
	 * push an action to the bottom of the deck (last)
	 */
	public void push(BaseAction a) { // under
		synchronized (q) {
			q.add(a);
		}
		startThread();
	}

	/**
	 * ASYNC 
	 * push an action to the top of the deck (first)
	 */
	public void insert(BaseAction a) { // over
		synchronized (q) {
			q.push(a);
		}
		startThread();
	}
	
	/**
	 * SYNC
	 * starts a thread to flush current actions
	 */
	private synchronized void startThread() {
		if (f == null || f.isDone())
			f = executors.submit(this::flush);
	}
	
	/**
	 * SYNC
	 * as long as there are actions in the deck, pops an action then executes it
	 */
	private void flush() {
		BaseAction action = null;
		synchronized(q) {
			if(!q.isEmpty())
				action = q.pop();
		}
		Log.format("ActionPipe flush %s", action);
		if(action != null) {
			if(action.canApply()) {//if(action.get()) {
				postRunnable(action::apply);
				if (bus != null) {
					Log.format("ActionPipeline post action to bus  %s", action);
					bus.post(action);
				} else {
					Log.format("Action pipeline bus == null  %s", action);
				}
				history.add(action);
			}
//			if(action.canApply()) {
//				action.apply();
//				history.add(action);
//			}
			flush();
		}
	}
	
	

	public void setPostRunnable(Consumer<Runnable> method) {
		if(this.postRunnable != null) Log.critical("ActionPipeline.postRunnable can only be assigned once");
		else postRunnable = method;
	}
	private void postRunnable(Lambda l) {
		if(postRunnable != null) postRunnable.accept(l::call);
		else l.call();
	}

	public void setBus(EventBus bus) {
		if(this.bus != null) Log.critical("ActionPipeline.bus can only be assigned once");
		else this.bus = bus;
	}
	
	
//	/**
//	 * au cas ou ?
//	 * genre si on veut annuler le reste de la pipeline durant un processus ? genre si qqn meurt et quon veut annuler le reste des actions
//	 */
//	private void clear() {
//		synchronized(q) {
//			q.clear();
//		}
//	}
	
}
