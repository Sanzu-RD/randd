package com.souchy.randd.situationtest.scripts;

import java.util.List;
import java.util.function.Predicate;

import com.souchy.randd.jade.api.IEffect;
import com.souchy.randd.situationtest.events.CastSpellEvent;
import com.souchy.randd.situationtest.eventshandlers.OnCast;
import com.souchy.randd.situationtest.models.org.FightContext;

public abstract class ScriptedSkill {

	public String name = "java name";
	
	//public abstract void hiRuby();
	
	/*public void test() {
		System.out.println("hey test");
	}*/
	
	/**
	 * ce qu'on applique 
	 */
	public List<IEffect> effects;
	
	/**
	 * ce qu'il se fait appeler quand on cast le spell
	 */
	public OnCast onCast;
	

	/**
	 * general conditions
	 */
	public List<Predicate<FightContext>> conditions;
	
	public String getName() {
		return name;
	}
	public void setName(String str) {
		name = str;
	} 
	
	public ScriptedSkill(String str) {
		name = str;
	}
	public void setOnCast(OnCast handler) {
		this.onCast = handler;
	}
	public OnCast getOnCastHandler() {
		return onCast;
	}
	
	/**
	 * Active l'utilisation des orientations diagonales, c-à-d :
	 * ex un sort avec une aoe en ligne :
	 * [x,x,x] 
	 * sera tournée comme ça par défaut quand la souris est en diago : 
	 * [x,_,_]
	 * [_,x,_]
	 * [_,_,x]
	 * 
	 * + ça autorise cette orientation quand on fait tourner l'aoe avec le contrôle d'orientation 
	 * TODO ScriptedSkill.canDiagonal() ?
	 * @return
	 */
	public boolean canOrientDiagonal() {
		return false;
	}
	
	
	/**
	 * Vérifie si toutes les conditions sont remplies
	 * @param context - contexte/état du combat
	 * @return - True si toutes les conditions sont remplies, false si au moins une condition ne l'est pas.
	 */
	public boolean assertConditions(FightContext context) {
		for(Predicate<FightContext> c : conditions)
			if(c.test(context) == false) return false;
		return true;
	}
	
}
