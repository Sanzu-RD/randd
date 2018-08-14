package com.souchy.randd.situationtest.interfaces;

import java.util.List;


/**
 * Créé ça vite fait pour holder les effets des sorts avec leurs cellules d'application et leurs conditions etc
 * <br>
 * Pcq chaque effet d'un sort a son propre AoE et ses propres condition et ça devient lourd à mettre dans l'interface Spell, donc un wrapper ici peut aider 
 * 
 * 
 * @author Souchy
 *
 */
public interface SpellEffect {

	
	public IEffect effect();
	
	
	
	public List<ICell> cells();
	
	public boolean lineOfSight();
	
	public void conditions();
	
}
