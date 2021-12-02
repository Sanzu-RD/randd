package com.souchy.randd.tools.mapeditor.imgui.windowsgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent.Container;

/**
 * 
 * 
 * @author Blank
 * @date 28 nov. 2021
 */
public class SpellEditor extends Container {

	public SpellEditor() {
		super();
		this.title = "SpellEditor";
	}

	@Override
	protected void applyDefaults() {
		this.size = new int[] { 800, 700 };
		this.position = new int[] { (Gdx.graphics.getWidth() - size[0]) / 2, 30 };
	}
	
	
	@Override
	public void renderContent(float delta) {
		// TODO Auto-generated method stub
		
	}
	
	
	
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
		public static int idCounter = 0;
		public SpellCondition condition;
		public final int id = idCounter++;
		public String name;
		
		public SpellNode parent;
		public List<SpellNode> nodes = new ArrayList<>();
		
		public Map<String, Integer> memory = new HashMap<>(); 
		
		public Effect e;
		
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
