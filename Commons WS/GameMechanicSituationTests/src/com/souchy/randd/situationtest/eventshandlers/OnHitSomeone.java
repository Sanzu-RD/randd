package com.souchy.randd.situationtest.eventshandlers;

import com.souchy.randd.situationtest.events.OnHitEvent;

/**
 * 
 * @author Souchy
 *
 *
 * ex : un joueur ajoute un autre Hit de 10 dmg Dark à chaque fois qu'elle Hit quelqu'un
 * 
 * player.register(new OnHitSomeone() -> (OnHitEvent e){
 * 		e.target.post(new Damage2Action(e.source, e.target, Damages.Hit, new ElementValue(Elements.Dark, 10)));
 * });
 *
 */
@FunctionalInterface
public interface OnHitSomeone extends EventHandler<OnHitEvent> {


}
