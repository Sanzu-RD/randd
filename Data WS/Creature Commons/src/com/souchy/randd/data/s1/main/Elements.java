package com.souchy.randd.data.s1.main;

import gamemechanics.statics.Element;
import gamemechanics.statics.stats.properties.StatProperty;
//import statics.IEElement;

public enum Elements implements Element, StatProperty {
	
//	public static enum asdf implements StatProperty {
//		
//	}

	normal,
	
	fire,
	water,
	earth,
	nature,
	air,
	
	light,
	dark,

	physical,
	steel,
	ghost,
	ice,
	psychic,
	electricity,
	toxin,
	
	;
	
	private Elements() {
		values.add(this);
		StatPropertyID.register(this);
	}
	
}
