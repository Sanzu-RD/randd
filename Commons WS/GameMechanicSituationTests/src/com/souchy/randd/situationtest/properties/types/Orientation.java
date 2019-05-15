package com.souchy.randd.situationtest.properties.types;

/**
 * TODO ORIENTATION URGENT
 * 
 * @author Souchy
 *
 */
public enum Orientation {

	North,
	East,
	South,
	West,
	
	// Activées uniquement lorsqu'un spell a canDiagonal() == true;
	NE,
	ES,
	SE,
	EN;
	
}
