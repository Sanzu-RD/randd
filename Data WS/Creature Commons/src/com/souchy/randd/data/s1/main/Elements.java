package com.souchy.randd.data.s1.main;

import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.StatProperty;

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
	 * helper to make spell ids
	 */
	public int makeid(int season, int i) {
		return season * 1000 + (this.ordinal() + 1) * 100 + i;
	}
	
}
