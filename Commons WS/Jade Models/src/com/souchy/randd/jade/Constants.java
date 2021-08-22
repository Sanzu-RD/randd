package com.souchy.randd.jade;

public class Constants {
	
	/**
	 * Number of spells in a spellbook per creature
	 */
	public static final int numberOfSpells = 8;

	/**
	 * Nombre d'éléments dans le jeu (red, green, blue, brown, white, black)
	 */
	public static final int ElementsCount = 6;
	/**
	 * Nombre de créatures de base par équipe / deck
	 */
//	public static final int CreaturesPerTeam = 4;
	/**
	 * Nombre de bans par équipe en mode draft
	 */
//	public static final int BansPerTeam = 3;
	/**
	 * ID de l'action de mouvement
	 */
	public static final int MovementActionID = 0;
	
	/**
	 * Base time per turn in seconds
	 */
	public static final int baseTimePerTurn = 30;
	
	
	/**
	 * Height of the floor (Z) (1f)
	 */
	public static final float floorZ = 1f;
	/**
	 * Width of a cell (1f)
	 */
	public static final float cellWidth = 1f;
	/**
	 * Half width of a cell (1f)
	 */
	public static final float cellHalf = 0.5f;
//	public static final Vector2 modelOffset = new Vector2(0.5, 0.5);
	
	
	private static final int statusMult = 10000000;
	
	/**
	 * helper to make spell ids <p>
	 * 
	 * Season Status Creature E SSS <br>
	 * 01     0      000      1 001
	 */
	public static final int makeSpellId(int season, boolean status, int creaturemodel, int element, int spell) {
		return (season * 100000000) + (status ? statusMult : 0) + (creaturemodel * 10000) + ((element + 1) * 1000) + spell;
	}
	/**
	 * add the status multiplier to a spell id
	 */
	public static int statusId(int spellid) {
		return spellid + 10000000;
	}
	/**
	 * spell id without the season and status digits for debugging
	 */
	public static final int simplifiedSpellId(int i) {
		i %= Math.pow(10, (int) Math.log10(i)); // season 10 digit
		i %= Math.pow(10, (int) Math.log10(i)); // season 01 digit
		i %= Math.pow(10, (int) Math.log10(i)); // status digit
		return i;
	}
	
}
