package gamemechanics.ext;


import gamemechanics.statics.properties.Targetability;

public enum CellType {
	Type00(0, 0, 0, 0), // block/wall/column/pillar
	Type01(0, 0, 0, 1),  // hole : can't move or target
	Type02(0, 0, 1, 0),
	Type03(0, 0, 1, 1),
	Type04(0, 1, 0, 0), 
	Type05(0, 1, 0, 1),
	Type06(0, 1, 1, 0),
	Type07(0, 1, 1, 1),
	Type08(1, 0, 0, 0), // impossible type (walk on but no walk through)
	Type09(1, 0, 0, 1),
	Type10(1, 0, 1, 0), // impossible type (walk on but no walk through)
	Type11(1, 0, 1, 1), // impossible type (walk on but no walk through)
	Type12(1, 1, 0, 0), // walk on/through but no cast on/through ? how
	Type13(1, 1, 0, 1),
	Type14(1, 1, 1, 0), // // mist : can walk on/through, can cast on, but not through
	Type15(1, 1, 1, 1), // normal cell
	;

	public final boolean[] targetability = new boolean[8];
	private CellType(int canBeWalkedOn, int canBeWalkedThrough, int canBeCastedOn, int canBeCastedThrough) {
		targetability[Targetability.CanBeWalkedOn.ordinal()] = canBeWalkedOn == 1;
		targetability[Targetability.CanBeWalkedThrough.ordinal()] = canBeWalkedThrough == 1;
		targetability[Targetability.CanBeCastedOn.ordinal()] = canBeCastedOn == 1;
		targetability[Targetability.CanBeCastedThrough.ordinal()] = canBeCastedThrough == 1;
	}
	
	
}