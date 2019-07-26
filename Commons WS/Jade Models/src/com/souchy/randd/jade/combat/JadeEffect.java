package com.souchy.randd.jade.combat;

public interface JadeEffect {
	
	/** could also be called type/model id */
	public int effectID();
	public int[] areaOfEffect();
	public int targetConditions();
	
}
