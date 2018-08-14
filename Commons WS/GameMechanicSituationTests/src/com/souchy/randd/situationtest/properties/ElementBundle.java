package com.souchy.randd.situationtest.properties;

import com.souchy.randd.situationtest.properties.types.Elements;

public class ElementBundle {
	
	public final ElementValue scl;
	public final ElementValue flat;
	public final ElementValue resScl;
	public final ElementValue resFlat;
	
	public ElementBundle(Elements ele, int scl, int flat, int resScl, int resFlat) {
		this.scl = new ElementValue(ele, scl);
		this.flat = new ElementValue(ele, flat);
		this.resScl = new ElementValue(ele, resScl);
		this.resFlat = new ElementValue(ele, resFlat);
	}

}
