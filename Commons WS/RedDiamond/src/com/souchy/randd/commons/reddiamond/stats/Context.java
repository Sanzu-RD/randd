package com.souchy.randd.commons.reddiamond.stats;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.souchy.randd.commons.reddiamond.enums.ContextScope;
import com.souchy.randd.commons.reddiamond.instances.Fight;

public class Context {
	
	public final ImmutableMap<ContextScope, Integer> context;
	
	public Context(int fightid) {
		this(fightid, 0, 0);
	}
	public Context(int fightid, int actionid) {
		this(fightid, actionid, 0);
	}
	public Context(int fightid, int actionid, int effectid) {  // int entityUID
		// obtain those current stats from the parameters, 
		// since every object will have a context, 
		// we can get the action from the effect and the fight from the action
		Fight f = HistoryStats.getFight(fightid); // to get the round and turn
		Map<ContextScope, Integer> map = new HashMap<>();
		map.put(ContextScope.Fight, fightid);  		 // fight  uid
		map.put(ContextScope.Round, f.getRound());   // round  uid
		map.put(ContextScope.Turn, f.getTurn()); 	 // turn   uid
		map.put(ContextScope.Action, actionid); 	 // action uid	
		map.put(ContextScope.Effect, effectid); 	 // effect uid
		context = ImmutableMap.copyOf(map);
	}
	
	public int get(ContextScope scope) {
		return context.get(scope);
	}

	public int getFightId() {
		return context.get(ContextScope.Fight);
	}
	public int getRoundId() {
		return context.get(ContextScope.Round);
	}
	public int getTurnId() {
		return context.get(ContextScope.Turn);
	}
	public int getActionId() {
		return context.get(ContextScope.Action);
	}
	public int getEffectId() {
		return context.get(ContextScope.Effect);
	}
	
}
