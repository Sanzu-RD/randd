package com.souchy.randd.jade.api;

import com.souchy.randd.commons.tealwaters.commons.Identifiable;

/**
 * 
 * @author Souchy
 *
 */
public interface IEffect extends Identifiable<Integer> {

	
	public ICombatEntity getSource();
	
	
	public Integer getID();
	
	
	/**
	 * Apply the effect to an entity
	 */
	public void apply(IEntity target);
	
	/**
	 * 
	 */
	public void getType();
	
	/**
	 * Gets the text describing this effect (client-side environment)
	 */
	public void getDesc(); 

	
	/**
	 * Can stack the effect
	 */
	public void canStack();
	
	/**
	 * Can refresh the duration of the effect
	 */
	public void canProlong();

	/**
	 * How many maximal stacks can one entity have at once.
	 */
	public void maxStacks();
	
	/**
	 * Gets the base duration of the effect
	 * <br>
	 * 0 for instant effect that don't last
	 * <br> 
	 * >0 for over time effects
	 */
	public void maxDuration();
	
	/**
	 * J'sais pas si c'est une bonne idée cette fonction,
	 * <br>
	 * Mais ça serait pour choisir si l'effet est visible dans la barre des effets du personnage.
	 * <br>
	 * Ex certains passifs pourraient ptete être cachés.
	 */
	public void isVisible();
	
	/**
	 * Tells which kind of targets this effect applies to.
	 * <br>
	 * Example an AoE that hits only enemies, or only allies, or both, or only summons, etc.
	 * @return Prob a byte or short that you do bitwise operations to.
	 * <br> Ex flag = <s>00000000 |=</s> (ALLY_HEROES | ALLY_SUMMONS) pour target juste les alliés
	 * <br> Ex flag = (ALLY_SUMMONS | ENEMY_SUMMONS) pour target juste les invocations
	 * <br> Ex flag = (ALLY_TRAPS | ENEMY_TRAPS) pour target juste les traps
	 */
	public void targetFlags();
	
	//-------------------------------------------
	
	
	
	/**
	 * Gets the amount of stacks this effect currently has
	 */
	public void currentStacks();
	
	/**
	 * Gets the current remaining duration of the effect
	 */
	public void currentDuration();
	
	/**
	 * Conditions for the application of the effect
	 * <p>
	 * -> pas besoin d'avoir cette fonction dans l'environnement client 
	 * <p>
	 * -> edit : rectification, c'est une bonne chose d'avoir les conditions côté client aussi pour pouvoir faire les calculs priliminaires sur chaque cellules de ce côté.
	 * <br>Comme ça, une fois que le client a décidé de son cast, le serveur a juste besoin de vérifier le cast sur la cellule demandé plutôt que toutes les cellules
	 */
	public void conditions();
	
	
	
	
}
