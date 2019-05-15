package com.souchy.randd.situationtest.asdf;

import com.souchy.randd.situationtest.properties.ElementValue;
import com.souchy.randd.situationtest.properties.types.Damages;
import com.souchy.randd.situationtest.properties.types.Elements;
import com.souchy.randd.situationtest.models.entities.Character;

public class DamageHandler {

	
	public void asdf() {
		Damages type;
		Elements output;
		//ElementValue scl;
		//ElementValue flat;
		DamageMatrix matrix;
		
		
		Character target;
		Character source;
		
		target.stats.get(stat)
		
	}
	
	
	public static class DamageMatrix {
		public ElementValue[][] values = new ElementValue[Elements.values().length][2];
		public ElementValue scl(Elements ele) {
			return values[ele.ordinal()][0];
		}
		public ElementValue flat(Elements ele) {
			return values[ele.ordinal()][1];
		}
		public void set(ElementValue scl, ElementValue flat) {
			values[scl.element.ordinal()][0] = scl;
			values[flat.element.ordinal()][1] = flat;
		}
	}
	
	
	
	
}
