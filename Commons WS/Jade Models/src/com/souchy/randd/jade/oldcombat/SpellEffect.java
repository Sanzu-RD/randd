package com.souchy.randd.jade.oldcombat;

import java.util.List;


/**
 * Cr�� �a vite fait pour holder les effets des sorts avec leurs cellules d'application et leurs conditions etc
 * <br>
 * Pcq chaque effet d'un sort a son propre AoE et ses propres condition et �a devient lourd � mettre dans l'interface Spell, donc un wrapper ici peut aider 
 * 
 * 
 * @author Souchy
 *
 */
public interface SpellEffect {

	
	public IEffect effect();
	
	
	
	public List<Cell> cells();
	
	public boolean lineOfSight();
	
	public void conditions();
	
}
