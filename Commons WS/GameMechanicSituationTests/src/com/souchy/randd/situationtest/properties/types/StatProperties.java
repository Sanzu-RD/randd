package com.souchy.randd.situationtest.properties.types;

public enum StatProperties {

	/** hp */
	Resource1,
	/** Shield for resource 1 */
	Resource1Shield,
	
	/** mana = pa */
	Resource2,
	/** Shield for resource 2 */
	Resource2Shield,
	
	/** points de movements / pm, mais tu peux avoir un perso qui utilise sa mana pour se déplacer à la place */
	Resource3,

	/** special points specific to certain classes
	 * p.ex. exemple des points que t'accumule en lancant des sorts normaux et qui te permettent de lancer un sort spécial */
	Resource4,

	
	
	
	/** Height difference between 2 cells which a character can jump/walk over */
	Jump,

	/** nb d'invocations */
	MaxInvocation,
	
	/** Range modificator */
	Range,

	/** Counter chance definitely */
	CounterChance,
	
	
	/** Crit ou pas ? */
	//CritChance,
	/** Dodge ? */
	//DodgeChance,
	
	
	
}
