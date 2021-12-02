package com.souchy.randd.commons.reddiamond.enums;

/**
 * Multiplies an effect value per x things
 * 
 * @author Blank
 * @date 7 nov. 2021
 */
public enum ValueMultiplierType {
	
	// none
	None,

	// target type
	PerXTargetHit,
	PerXTargetAlive,
	PerXTargetDead,
	
	
	// affinity
	PerX,

	
	// resources
	PerXAdded,
	PerXReduced,
	PerXUsed,
	
	PerMaxX,
	PerCurrentX,
	PerCurrentMissingX,
	
	PerPercentMaxX,
	PerPercentCurrentX,
	PerPercentMissingX,
	
	PerPercentCurrentMaxX,
	
	
	
}
