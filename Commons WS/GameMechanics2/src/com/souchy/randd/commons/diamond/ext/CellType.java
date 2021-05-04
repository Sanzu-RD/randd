package com.souchy.randd.commons.diamond.ext;


import com.souchy.randd.commons.diamond.models.stats.special.Targetting;
import com.souchy.randd.commons.diamond.statics.properties.Targetability;

public enum CellType {
	Type00(1, 1, 1, 1), // normal cell
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
	
	Type15(0, 0, 0, 0), // block/wall/column/pillar
	;

//	public final boolean[] targetability = new boolean[4];
	public final Targetting t = new Targetting();
	private CellType(int canBeWalkedOn, int canBeWalkedThrough, int canBeCastedOn, int canBeCastedThrough) {
//		targetability[Targetability.CanBeWalkedOn.ordinal()] = canBeWalkedOn == 1;
//		targetability[Targetability.CanBeWalkedThrough.ordinal()] = canBeWalkedThrough == 1;
//		targetability[Targetability.CanBeCastedOn.ordinal()] = canBeCastedOn == 1;
//		targetability[Targetability.CanBeCastedThrough.ordinal()] = canBeCastedThrough == 1;

		t.setBase(Targetability.CanBeWalkedOn, canBeWalkedOn == 1);
		t.setBase(Targetability.CanBeWalkedThrough, canBeWalkedThrough == 1);
		t.setBase(Targetability.CanBeCastedOn, canBeCastedOn == 1);
		t.setBase(Targetability.CanBeCastedThrough, canBeCastedThrough == 1);
	}
	
	
	public static CellType get(Targetting t) {
		for(var type : values()) {
//			if(t.targetability.get(Targetability.CanBeWalkedOn).value() != type.targetability[Targetability.CanBeWalkedOn.ordinal()]) continue;
//			if(t.targetability.get(Targetability.CanBeWalkedThrough).value() != type.targetability[Targetability.CanBeWalkedThrough.ordinal()]) continue;
//			if(t.targetability.get(Targetability.CanBeCastedOn).value() != type.targetability[Targetability.CanBeCastedOn.ordinal()]) continue;
//			if(t.targetability.get(Targetability.CanBeCastedThrough).value() != type.targetability[Targetability.CanBeCastedThrough.ordinal()]) continue;
			
			if(type.t.same(t))
				return type;
		}
		return null;
	}
	
	
}