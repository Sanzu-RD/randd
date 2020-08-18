package com.souchy.randd.commons.diamond.common;

import java.util.ArrayDeque;
import java.util.Deque;


public class ActionPipeline {


	/**
	 * Deck d'actions.
	 * Peut ajouter une action au début ou à la fin du deck.
	 */
	public final Deque<Action> q = new ArrayDeque<>();
	
	/**
	 * push an action to the top of the deck (first)
	 */
	public void over(Action a) {
		q.push(a);
	}
	
	/**
	 * push an action to the bottom of the deck (last)
	 */
	public void under(Action a) {
		q.add(a);
	}
	
	/**
	 * Exécute et enlève tous les actions dans la queue jusqu'à temps qu'elle soit vide.
	 * Une action peut en créer une autre et allonger le processus
	 */
	public void flush() {
		while(!q.isEmpty()) 
			q.pop().apply();
	}
	
	/**
	 * au cas ou ?
	 * genre si on veut annuler le reste de la pipeline durant un processus ? genre si qqn meurt et quon veut annuler le reste des actions
	 */
	public void clear() {
		q.clear();
	}
	
}
