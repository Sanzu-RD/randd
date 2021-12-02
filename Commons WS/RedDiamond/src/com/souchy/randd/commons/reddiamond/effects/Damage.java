package com.souchy.randd.commons.reddiamond.effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.souchy.randd.commons.reddiamond.common.ValueMultiplier;
import com.souchy.randd.commons.reddiamond.enums.Element;
import com.souchy.randd.commons.reddiamond.stats.IntStat;


/**
 * 
 * 
 * @author Blank
 * @date 7 nov. 2021
 */
public class Damage {
	

	/**
	 * Base ratios
	 */
	public Map<Element, IntStat> formula = new HashMap<>();
	/**
	 * Value multipliers, usually only 1, maybe 2
	 */
	public List<ValueMultiplier> multipliers = new ArrayList<>();

	
	
	
}
