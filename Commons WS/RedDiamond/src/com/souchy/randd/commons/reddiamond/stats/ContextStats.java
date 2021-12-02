package com.souchy.randd.commons.reddiamond.stats;

import java.util.Map;

import com.souchy.randd.commons.reddiamond.common.ValueMultiplier;
import com.souchy.randd.commons.reddiamond.enums.ContextScope;
import com.souchy.randd.commons.reddiamond.enums.Resource;
import com.souchy.randd.commons.reddiamond.enums.TargetType;
import com.souchy.randd.commons.reddiamond.instances.Entity;

public class ContextStats extends Entity {

	
	public Map<Resource, Integer> resourceUsed;
	public Map<Resource, Integer> resourceAdded;
	public Map<Resource, Integer> resourceLost; // same as reduced
	public Map<Resource, Integer> cellsTraveled; // something like this through movement abilities not mp
	public Map<TargetType, Integer> targetsHit;
	
	
	public final Context context;
	public ContextStats(int fightid) {
		this(fightid, 0, 0);
	}
	public ContextStats(int fightid, int actionid) {
		this(fightid, actionid, 0);
	}
	public ContextStats(int fightid, int actionid, int effectid) {  // int entityUID
		// obtain those current stats from the parameters, 
		// since every object will have a context, 
		// we can get the action from the effect and the fight from the action
		context = new Context(fightid, actionid, effectid);
	}
//	public ContextStats(ContextStats context) {
//		this(context.getFightId(), context.getActionId(), context.getEffectId());
//	}
	
	
	public int getFightId() {
		return context.getFightId();
	}
	public int getRoundId() {
		return context.getRoundId();
	}
	public int getTurnId() {
		return context.getTurnId();
	}
	public int getActionId() {
		return context.getActionId();
	}
	public int getEffectId() {
		return context.getEffectId();
	}
	
	
	public int get(ValueMultiplier m) {
		if(m.resType != null) {
			switch(m.type) {
				case PerXAdded -> resourceAdded.get(m.resType);
				case PerXReduced -> resourceAdded.get(m.resType);
				case PerXUsed -> resourceAdded.get(m.resType);
			}
		}
		return 0;
	}
	
}
