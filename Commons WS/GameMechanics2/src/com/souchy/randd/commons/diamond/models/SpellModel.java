package com.souchy.randd.commons.diamond.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.reddiamond.enums.ContextScope;

public abstract class SpellModel {
	
	public final int id = id();
	public abstract int id();
	
	public SpellStats stats = new SpellStats();
	public List<Effect> effects = new ArrayList<>();
	

	public int nodeIdCounter = 0;
	public SpellNode castNode = new SpellNode(nodeIdCounter++);
	
	
	/**
	 * {
	 * 	condition: {}
	 * 	nodes: [
	 * 		{ }
	 *  ]
	 * }
	 * 
	 * @author Blank
	 * @date 28 nov. 2021
	 */
	public static class SpellNode {
		public final int id;
		public String name;
		
		public SpellNode parent;
		
		public Effect e;
		public SpellCondition condition;
		public List<SpellNode> nodes = new ArrayList<>();
		
		public Map<String, Integer> memory = new HashMap<>(); 
		
		public SpellNode(int id) {
			this.id = id;
		}
		
		public void apply() {
			if(condition == null || condition.check()) {
				for(var n : nodes)
					n.apply();
			}
		}
		public SpellNode getRoot() {
			var root = parent;
			while(parent.parent != null)
				root = parent.parent;
			return root;
		}
	}
	
	public static class SpellCondition {
		// context / target
		// stat
		ContextScope scope;
		int targetId;
		
		public EqualitySign sign;
		public boolean check() {
			return true;
		}
	}
	
	public static enum EqualitySign {
		Equal,
		GT,
		GTE,
		LT,
		LTE;
	}
	public static enum SpellNodeType {
		Container,
		Effect;
	}
	
	
	
}
