package com.souchy.randd.situationtest.interfaces;



/**
 * 
 * To delete, probably
 * 
 * @author Souchy
 *
 */
public interface IEffectTrigger {

	/*
	 * 
	 * On Kill
	 * On Death
	 * 
	 * On Damage
	 * On recv damage
	 * 
	 * On Heal
	 * On recv heal
	 * 
	 * On status
	 * On recv status
	 * 
	 * On low hp
	 * 
	 * On movement (ex poison vertige, pièges)
	 * On action (ex poison silence)
	 * 
	 * On start turn
	 * On end turn
	 * 
	 * On start
	 * 
	 * 
	 * OnStatChange (-> call au début du combat puis à chaque fois qu'une de tes stats cahgne pour checker des choses du genre intel>200 etc)
	 * 
	 * 
	 * Any condition can work as a trigger. 
	 * Ex : hp < 20% even if you have a shield
	 * Ex : int > 100
	 * Ex : PM utilisé > 4 dans le tour
	 * Ex : si le même spellcast a déjà fait plus de 250 dmg via les effets précédents => trigger un autre effet
	 * Ex : sacrieur's stacks > 4 
	 * Ex : Rage ouginak > 10 : transforme en chien
	 * 
	 * but then how do you check the condition and send an event for all these ?
	 * 
	 * need a "ConditionEvent" or something, that would be scripted and actually looking for a specific condition in the context
	 * like an Observer or Watcher
	 * maybe can use a handler that registers to and accepts multiple event types, ex stacks onTurnStart + onDamage + onWalk, etc
	 * just then we would need to change EventHandler to not be a FunctionalInterface, and it wouldntt work with  the generic type either,
	 * so would prob need to register like 3 seperate eventhandlers doing the same thing
	 * 
	 *  
	 */
	
	
	
}
