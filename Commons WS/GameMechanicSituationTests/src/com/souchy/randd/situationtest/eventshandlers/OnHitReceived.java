package com.souchy.randd.situationtest.eventshandlers;

import com.souchy.randd.situationtest.events.OnHitEvent;

/**
 * 
 * @author Souchy
 * 
 * 
 * ex : un joueur se soigne de 10 (eau, flat) lorsqu'il reçoit un hit
 * 
 * player.register(new OnHitReceived() -> (OnHitEvent e){
 * 		e.source.post(new Heal2Action(e.source, e.source, new ElementValue(Elements.Water, 10)));
 * });
 * 
 *
 */
@FunctionalInterface
public interface OnHitReceived extends EventHandler<OnHitEvent> {

}
