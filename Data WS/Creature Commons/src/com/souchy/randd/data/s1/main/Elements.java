package com.souchy.randd.data.s1.main;

import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.StatProperty;
import com.souchy.randd.jade.Constants;

public enum Elements implements Element, StatProperty {
	

	normal,
	
	fire,
	water,
	earth,
	air,
//	nature,
	
//	light,
//	dark,

//	physical,
//	steel,
//	ghost,
//	ice,
//	psychic,
//	electricity,
//	toxin,
	
	;
	
	private Elements() {
		StatPropertyID.register(this);
		values.add(this);
	}
	

	/**
	 * helper to make spell ids<p>
	 * 
	 * Season Status Creature E SSS<br>
	 * 01     0      000      1 001
	 */
	public int makeid(int season, int s) {
		return makeid(season, 0, s, false);
	}
	/**
	 * helper to make spell ids<p>
	 * 
	 * Season Status Creature E SSS<br>
	 * 01     0      000      1 001
	 */
	public int makeid(int season, int s, boolean status) {
		return makeid(season, 0, s, status);
	}
	/**
	 * helper to make spell ids<p>
	 * 
	 * Season Status Creature E SSS<br>
	 * 01     0      000      1 001
	 */
	public int makeid(int season, int creaturemodel, int s) {
		return makeid(season, creaturemodel, s, false);
	}
	/**
	 * helper to make spell ids<p>
	 * 
	 * Season Status Creature E SSS<br>
	 * 01     0      000      1 001
	 */
	public int makeid(int season, int creaturemodel, int s, boolean status) {
		return Constants.makeSpellId(season, status, creaturemodel, this.ordinal(), s);
//		return (season * Constants.seasonMult) + (status ? 10000000 : 0) + (creaturemodel * 10000) + ((this.ordinal() + 1) * 1000) + s;
	}
	
	
}
