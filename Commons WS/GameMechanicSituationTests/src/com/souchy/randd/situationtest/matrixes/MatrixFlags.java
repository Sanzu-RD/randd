package com.souchy.randd.situationtest.matrixes;

import com.souchy.randd.commons.tealwaters.commons.Identifiable;

public enum MatrixFlags {
	
	;
	
	public static interface ICastableAreaFlags {
		
	}
	public static interface IConditionFlags {
		
	}
	public static interface IEffectFlags {
		
	}
	

	/** PositionningFlags */
	public static enum PositionningFlags implements Identifiable<Byte>, ICastableAreaFlags, IConditionFlags, IEffectFlags { //implements MatrixFlags {
		NoFlag	  (0b00000000),
		Source	  (0b00000001),
		TargetCell(0b00000010),
		;
		private final byte id;
		private PositionningFlags(int i) {
			id = (byte) i;
		}
		@Override
		public Byte getID() {
			return id;
		}
		
	}

	/** PositionningFlags + CastableCells/Range matrix  Defines the area in which the spell can be casted around the player */
	public static enum RangeMatrixFlags implements Identifiable<Byte>, ICastableAreaFlags {
		CanCast	  (0b00000100),
		CannotCast(0b00001000),
		;
		private final byte id;
		private RangeMatrixFlags(int i) {
			id = (byte) i;
		}
		@Override
		public Byte getID() {
			return id;
		}
	}
	
	/** PositionningFlags + Targeting conditions, read as "Must be ...", Defines conditions for positionning and classes of targets */
	public static enum CellConditiontFlags implements Identifiable<Byte>, IConditionFlags {
		EmptyCell (0b00000100),
		AlliedCell(0b00001000),
		EnemyCell (0b00010000),
		SummonCell(0b00100000),
		TrapCell  (0b01000000),
		GlyphCell (0b10000000),
		;
		private final byte id;
		private CellConditiontFlags(int i) {
			id = (byte) i;
		}
		@Override
		public Byte getID() {
			return id;
		}
	}

	/** PositionningFlags + Effects */
	public static enum EffectFlags implements Identifiable<Byte>, IEffectFlags {
		Effect1(0b00000100),
		Effect2(0b00001000),
		Effect3(0b00010000),
		Effect4(0b00100000),
		Effect5(0b01000000),
		Effect6(0b10000000),
		;
		private final byte id;
		private EffectFlags(int i) {
			id = (byte) i;
		}
		@Override
		public Byte getID() {
			return id;
		}
	}
	
	/*
	 1st matrix : Castable Area
	 Uses PositioningFlags + RangeMatrixFlags
	 Determines the area in which we are able to cast the spell
	 [
	 [0,0,0,0,0,0,0,0,0,0,0]
	 [0,0,0,0,0,0,0,0,0,0,0]
	 [0,0,0,0,0,0,0,0,0,0,0]
	 [0,0,0,0,0,0,0,0,0,0,0]
	 [0,0,0,0,0,0,0,0,0,0,0]
	 [0,0,0,0,0,1,0,0,0,0,0]
	 [0,0,0,0,0,0,0,0,0,0,0]
	 [0,0,0,0,0,0,0,0,0,0,0]
	 [0,0,0,0,0,0,0,0,0,0,0]
	 [0,0,0,0,0,0,0,0,0,0,0]
	 [0,0,0,0,0,0,0,0,0,0,0]
	 ]
	 
	 2nd matrix : AoE conditions/positionning
	 Uses PositioningFlags + CellConditiontFlags
	 This checks conditions in an aoe around the current hovered/targeted cell 
	 Eg. you can specify if the target cell must be an enemy
	 Eg coup sournois : 1 = source, 7 = target must be an enemy at exactly 3 range in line with the caster, 3 = must be an empty cell behind the target so the source TPs there
	 [
	 [0,0,3,0,0]
	 [0,0,7,0,0]
	 [0,0,0,0,0]
	 [0,0,0,0,0]
	 [0,0,1,0,0]
	 ]
	 Si tu voulais pas spécifier exactement 3 de range, faudrait faire simplement faire ça avec condition=enligne dans les conds du sort.
	 Toutefois, on pert la spécification pour l'orientation de la matrice. J'estimerais que la matrice est toujours orientée en face du lanceur. (le 1 serait toujours en dessous de la matrice par défaut) 
	 [
	 [3]
	 [7]
	 "1" -> oriente tjrs en assumant source en dessous si non-spécifié. 
	 ]
	 On peut avoir un bool pour dire si on a le droit de rotate la matrice.
	 À ce moment, p-e qu'on écrit plusieurs matrices pour décrire les possibles rotations
	 [
	 [0,0]
	 [2,0]
	 ]
	 [
	 [0,0]
	 [0,2]
	 ]
	 Est-ce qu'on en fait une pour chacun des 8 quadrants ? (nord,sud,est,ouest + combinaisons)
	 
	 3rd matrix : AoE effects application matrix
	 Uses PositioningFlags + EffectsFlags
	 Once the conditions are checked, we can go to this matrix and get the corresponding targets for each effect and then cast them
	 [
	 [0,0,0,0,0]
	 [0,0,0,0,0]
	 [0,0,0,0,0]
	 [0,0,0,0,0]
	 [0,0,0,0,0]
	 [0,0,0,0,0]
	 ]
	 
	 
	 */
	
	
	
}
