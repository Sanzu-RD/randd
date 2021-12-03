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
	
	
	
}
