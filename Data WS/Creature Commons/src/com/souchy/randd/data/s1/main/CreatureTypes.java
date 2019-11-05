package com.souchy.randd.data.s1.main;

import data.new1.timed.Status.Passive;
import gamemechanics.statics.stats.Stats;
import gamemechanics.statics.stats.modifiers.eleMod;
import gamemechanics.statics.stats.modifiers.mathMod;
import gamemechanics.statics.stats.properties.Resource;
import statics.IECreatureType;



/**
 * 
 * 
 * dont need this enum if you just use the CreatureTypeModel class to tag the creatures and spells
 * 
 * 
 * @author Blank
 * @date 5 nov. 2019
 */
public enum CreatureTypes implements IECreatureType {
	
	knight {{
	}},
	
	zombie {{
	}},
	
	skeleton {{
	}},
	;
	
	
//	public final Stats stats = new Stats();
//	public final Passive passive;

	
}
