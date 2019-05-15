package com.souchy.randd.situationtest.properties.types;

public enum Elements {

	/**
	 * Pour avoir une valeur disont flat qui ne va pas chercher les +do du personnage.
	 * Ex tu mets ElementValue(10, NULL), ça va toujours �tre 10 d�g�ts fixe, sinon is on met un �l�ment �a ajouterait les +do du perso
	 */
	PURE,
	/**
	 * Affects all elements
	 */
	Global, 
	
	//Physical, // bleeding included in phys ?
	
	Fire, // red : 
	Water, // blue :
	Nature, // green : nature   // earth ou nature
	Air, // yellow : wind ou air
	
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
