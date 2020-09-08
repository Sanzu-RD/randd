package com.souchy.randd.commons.tealwaters.commons;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

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

	/**
	 * Thread pool for flushing/executing actions. This is where the actual execution / fight thread comes from
	 */
	private static ExecutorService executors = Executors.newCachedThreadPool();

	/**
	 * Future determining if a thread is already executing flush()
	 */
	private Future<?> f;
	
	/**
	 * Current action deck
	 */
	private final Deque<Supplier<Boolean>> q = new ArrayDeque<>();

	/**
	 * action history : past / resolved actions
	 */
	private List<Supplier<Boolean>> history = new ArrayList<>();


	/**
	 * ASYNC 
	 * push an action to the bottom of the deck (last)
	 */
	public void push(Supplier<Boolean> a) { // under
		synchronized (q) {
			q.add(a);
		}
		startThread();
	}

	/**
	 * ASYNC 
	 * push an action to the top of the deck (first)
	 */
	public void insert(Supplier<Boolean> a) { // over
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
		Supplier<Boolean> action = null;
		synchronized(q) {
			if(!q.isEmpty())
				action = q.pop();
		}
		//Log.format("ActionPipe flush %s", action);
		if(action != null) {
			if(action.get()) {
				history.add(action);
			}
//			if(action.canApply()) {
//				action.apply();
//				history.add(action);
//			}
			flush();
		}
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
