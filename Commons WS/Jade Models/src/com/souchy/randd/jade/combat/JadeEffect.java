package com.souchy.randd.jade.combat;

public interface JadeEffect {
	
	/** could also be called type */
	public int effectID();
	public int[] areaOfEffect();
	public int targetConditions();
	
}
