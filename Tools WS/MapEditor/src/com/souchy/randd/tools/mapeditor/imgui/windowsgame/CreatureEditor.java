package com.souchy.randd.tools.mapeditor.imgui.windowsgame;

import com.badlogic.gdx.Gdx;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent.Container;

public class CreatureEditor extends Container {
	
	private static CreatureEditor singleton = new CreatureEditor();
	public static Container get() {
		return singleton;
	}

	public CreatureEditor() {
		super();
		this.title = "CreatureEditor";
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
