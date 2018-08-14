package com.souchy.randd.situationtest.properties.types;

public enum Elements {

	/**
	 * Pour avoir une valeur disont flat qui ne va pas chercher les +do du personnage.
	 * Ex tu mets ElementValue(10, NULL), ça va toujours être 10 dégâts fixe, sinon is on met un élément ça ajouterait les +do du perso
	 */
	NULL,
	
	Physical, // bleeding included in phys ?
	
	Poison, // rename Toxic/acid ?
	
	
	Fire,
	Water,
	Earth, // earth ou nature
	Wind, // wind ou air
	
	Lightning, // Electricity, // thunder
	Ice,
	
	
	Dark, // peut inclure du genre skills de vampire/blood magic
	Light,
	
	
	Void,
	Chaos,
	
	// Creation  ? 
	// Fairy ? 
	// Magical ?
	// Curse ?
	// Psychic / Mind ?
	
	// TODO éléments : https://powerlisting.wikia.com/wiki/Category:Form_of_magic
	
	
}
