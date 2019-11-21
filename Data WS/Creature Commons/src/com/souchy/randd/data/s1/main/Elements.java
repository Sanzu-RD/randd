package com.souchy.randd.data.s1.main;

import gamemechanics.statics.Element;
import gamemechanics.statics.stats.properties.StatProperty;
import statics.IEElement;

public enum Elements implements Element, StatProperty {
	
//	public static enum asdf implements StatProperty {
//		
//	}

	normal,
	
	fire,
	water,
	nature,
	air,
	
	light,
	dark,

	earth,
	ghost,
	ice,
	steel,
	physical,
	psychic,
	electricity,
	toxin,
	
	;
	
	private Elements() {
		values.add(this);
		StatPropertyID.register(this);
	}
	
}
