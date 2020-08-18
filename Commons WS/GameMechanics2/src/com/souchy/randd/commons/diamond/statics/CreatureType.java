package com.souchy.randd.commons.diamond.statics;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.diamond.models.stats.CreatureStats;

import data.new1.Tag;

public interface CreatureType extends Tag {
	
	public static List<CreatureType> types = new ArrayList<>();
	
	
	public CreatureStats getStats();
//	public Passive getPassive();
	
}
