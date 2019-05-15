package com.souchy.randd.situationtest.properties;

import java.util.List;

import com.souchy.randd.situationtest.properties.types.Elements;

public class ElementBundle {
	
	public static enum ElementRate {
		Base, // flat base damage
		Inc, // increased % damage
		Flat, // +flat damage
		MoreScl, // more % damage
		
		ConvRate, // convert scalings elements
		ConvOutput, // convert damage output element
		Extra, // add damage as extra of another element
		
		ResScl, // % resistance
		ResFlat, // +flat resistance
		ResMore, // % more resistance
		
		PenScl, // % penetration
		PenFlat // +flat penetration
	}
	

	
	public ElementValue baseScl;
	public ElementValue scl;
	public ElementValue flat;
	public ElementValue moreScl;
	public List<ElementValue> convRates; // conv % of scaling and flat source dmg as another element
	public List<ElementValue> extraAs; // adds % of output as another element
	public List<ElementValue> convOutput; // conv % of output as another element
	
	
	public ElementValue resScl;
	public ElementValue resFlat;
	public ElementValue resMore;
	
	public ElementValue penScl;
	public ElementValue penFlat;
	
	public ElementBundle() {
		
	}
	
	public ElementBundle(Elements ele, int scl, int flat, int resScl, int resFlat) {
		this.scl = new ElementValue(ele, scl);
		this.flat = new ElementValue(ele, flat);
		this.resScl = new ElementValue(ele, resScl);
		this.resFlat = new ElementValue(ele, resFlat);
	}

}
