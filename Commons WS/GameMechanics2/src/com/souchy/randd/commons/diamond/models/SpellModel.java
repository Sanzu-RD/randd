package com.souchy.randd.commons.diamond.models;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.diamond.models.stats.SpellStats;

public abstract class SpellModel {
	
	public final int id = id();
	public abstract int id();
	
	public SpellStats stats = new SpellStats();
	public List<Effect> effects = new ArrayList<>();
	

}
