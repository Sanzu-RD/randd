package com.souchy.randd.situationtest.effects;


/**
 * 
 * TODO delete SpecialEffect, bonne documentation ici, TODO tagMatrix
 * 
 * J'sais pas si vraiment besoin de �a ou si cest vraiment un bonne id�e ou pas mais �a servirait � appliquer des fonctions sp�ciales ex :
 * 
 *  un effet qui va calculer le total d'hp de toutes les entit�es dans une zone 
 * 
 * 
 * @author Souchy
 *
 */
public class SpecialEffect {

	
	/*
	 * 
	 * Exemple do damage equal to the total amount of hp of everyone :
	 * 
	 * // va dabord chercher les targets correspondant au tag "Effet2" m�me si on en r�alit� on a pas d'effet2, on s'en sert juste pour tagger et aller chercher les targets
	 * targets = board.getTargets(targetCell, effectMatrix, effect2)
	 * 
	 * // fait la somme de leur hp 
	 * tothp = targets.stream().map(t -> t.stats.hp).sum();
	 * 
	 * // element value fixe �gale au total d'hp pour pas qu'elle scale avec les stats du joueur
	 * flat = ElementValue(NULL, tothp)
	 * 
	 * // et voil� 
	 * DamageEffect(Damage.Dark, null, flat, )
	 * 
	 * 
	 * toutefois on vient de d�cider d'utiliser l'objet Effect avec chacun leur EffectMatrix,
	 * donc il n'y aurait pu d'effectmatrix directement dans Spell... mais on peut quand m�me en mettre une .. ??
	 * serait quand m�me bien de pouvoir voir l'AOE dans laquelle on va chercher les valeurs
	 * 
	 * on peut juste avoir une "tagMatrix" genre avec tag1, tag2, tag3 etc et se servir de �a seulement pour les cas sp�cials comme celui ci, �a serait bien.
	 * 
	 */
	
	
}
