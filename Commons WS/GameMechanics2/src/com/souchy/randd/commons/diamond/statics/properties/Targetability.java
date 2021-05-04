package com.souchy.randd.commons.diamond.statics.properties;

public enum Targetability {
	
	// self
	CanBeWalkedOn,
	CanBeWalkedThrough,
	CanBeCastedOn,
	CanBeCastedThrough,
	
	// creatures
	CanWalkOnCreature,
	CanWalkThroughCreature,
	CanCastOnCreature,
	CanCastThroughCreature,
	
	// walls // renamed Block to Wall
	CanWalkOnWall,
	CanWalkThroughWall,
	CanCastOnWall,
	CanCastThroughWall,
	
	// holes
	CanWalkOnHole,
	CanWalkThroughHole,
	CanCastOnHole,
	CanCastThroughHole,
	
	// dash
	CanBeDashedThrough,
	
}