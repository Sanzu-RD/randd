package com.souchy.randd.commons.reddiamond.stats;

/**
 * Each spell has spell stats
 * 
 * Creatures and statuses can also have spell stats to suplement those
 * 
 * @author Blank
 * @date 7 nov. 2021
 */
public class SpellStats {

	/*
	 * {
	 * 	spellmodelid : 0, 		// 0 applies these stats to any spell
	 * 
	 * 	affinityRequirements : {
	 * 		water: { 0, 50, 0, 0 }
	 * 	},
	 * 	costs : {
	 * 		mana: { 3, 0, 0, 0 }
	 * 	},
	 * 	targetConditions : { 
	 *  },
	 * 	minRange : {
	 * 		pattern : {  },
	 * 		radius : { 0, 0, 0, 0 }
	 * 	},
	 * 	maxRange : {
	 * 		pattern : { circle },
	 * 		radius : { 10, 0, 0, 0 }
	 * 	},
	 *  cooldown : { 0, 0, 0, 0 },
	 *  castPerTurn : { 0, 0, 0, 0 },
	 *  castPerTarget : { 0, 0, 0, 0 },
	 *  cooldown : { 0, 0, 0, 0 },
	 *  canRotateAoe : { false, false }
	 *  
	 *  
	 *  int lastTurnCast; // tour auquel on a lancé le sort la dernière fois pour le cooldown
	 *  int castsThisTurn; // nombre de fois qu'on a lancé le sort ce tour
	 *  list<> targetsThisTurn; // cibles sur lesquelles on a lancé le sort ce tour
	 *  
	 *  
	 *  
	 * 
	 * }
	 */
	
	

	
	// cast costs ======================================================
//	public Map<Resource, IntStat> costs = new HashMap<>();

	// cell targetting conditions, inclu lineofsight ===================
//	public TargetTypeStat target = new TargetTypeStat();
	
	// cast ranges and patterns  =======================================
	/** Range minimale. 0 par défaut */
//	public IntStat minRangeRadius = new IntStat(0);
	/** Range maximale. 1 par défaut */
//	public IntStat maxRangeRadius = new IntStat(1);
	/** Pattern de range maximale. null par défaut. */
//	public ObjectStat<AoeBuilder> minRangePattern = new ObjectStat<AoeBuilder>(null);
	/** Pattern de range maximale. Cercle par défaut. <br>
	 * Example : stats.maxRangePattern.base = (t) -> AoeBuilders.cross.apply(t); */
//	public ObjectStat<AoeBuilder> maxRangePattern = new ObjectStat<AoeBuilder>(r -> AoeBuilders.circle.apply(r));
	
	// cast cooldowns ==================================================
//	public IntStat cooldown = new IntStat(0);
//	public IntStat castPerTurn = new IntStat(0);
//	public IntStat castPerTarget = new IntStat(0);
	
	
	// if the user can rotate the AOE manually or if it is based off the character's orientation
//	public BoolStat canRotate;
	// publicly modifiable aoes (spells create their aoes and place them here so that other classes can modify them without knowing the spell .class)
//	public List<ObjectStat<Aoe>> aoes = new ArrayList<>();
	
	
	
}
