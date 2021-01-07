package com.souchy.randd.data.s1.main;

import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.StatProperty;

public enum Elements implements Element, StatProperty {
	

//	normal,
	
	fire,
	water,
	earth,
//	nature,
	air,
	
	light,
	dark,

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
	
}
