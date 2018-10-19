package com.souchy.randd.situationtest.properties.types;

public enum Elements {

	/**
	 * Pour avoir une valeur disont flat qui ne va pas chercher les +do du personnage.
	 * Ex tu mets ElementValue(10, NULL), ça va toujours être 10 dégâts fixe, sinon is on met un élément ça ajouterait les +do du perso
	 */
	PURE,
	
	Physical, // bleeding included in phys ?
	
	Fire,
	Earth, // earth ou nature
	Water,
	Wind, // wind ou air

	Lightning, // Electricity, // thunder
	Dark, // peut inclure du genre skills de vampire/blood magic
	
	
	Ice, // ice skills could be based on water element
	Poison, // rename Toxic/toxin/acid ? or use dark
	Light,
	
	/*
	 * Red - fire
	 * Brown - Earth nature
	 * Blue - Water
	 * Black - Dark
	 * White - Light
	 * Purple - Poison
	 * Yellow - Lightning
	 */
	
	//Void,
	//Chaos,
	
	// Creation  ? 
	// Fairy ? 
	// Magical ?
	// Curse ?
	// Psychic / Mind ? Mystic ? Cosmic ?
	
	// TODO éléments : https://powerlisting.wikia.com/wiki/Category:Form_of_magic
	
	
}
