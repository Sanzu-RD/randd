package com.souchy.randd.situationtest.properties.types;

public enum Elements {

	/**
	 * Pour avoir une valeur disont flat qui ne va pas chercher les +do du personnage.
	 * Ex tu mets ElementValue(10, NULL), ça va toujours être 10 dégâts fixe, sinon is on met un élément ça ajouterait les +do du perso
	 */
	PURE,
	
	//Physical, // bleeding included in phys ?
	
	Fire, // red : 
	Earth, // green : nature   // earth ou nature
	Water,
	Wind, // wind ou air
	
	Dark, // black : peut inclure du genre skills de vampire/blood magic
	Light, // white : for good things, like candy/fairy magic, blessings, w/e

	
	/*
	Lightning, // Electricity, // thunder -> could be under wind/air
	Ice, // ice skills -> could be based on water element
	Poison, // rename Toxic/toxin/acid ? -> or use dark
	*/
	
	
	/*
	 * Red - Fire
	 * Green/Brown - Earth nature
	 * Blue - Water
	 * Yellow - Air, Lightning
	 * Black - Dark
	 * White - Light
	 * 
	 * dont need : 
	 * Purple - Poison
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
