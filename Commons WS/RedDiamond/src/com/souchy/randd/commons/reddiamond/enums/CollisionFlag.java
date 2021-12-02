package com.souchy.randd.commons.reddiamond.enums;

public enum CollisionFlag {
	/*
	CanWalkThrough,
	CanWalkOn,
	CanCastThrough,
	CanCastOn,

	CanBeWalkThrough,
	CanBeWalkOn,
	CanBeCastThrough,
	CanBeCastOn,
	*/
	
	
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
