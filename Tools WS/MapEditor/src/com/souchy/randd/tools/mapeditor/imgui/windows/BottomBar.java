package com.souchy.randd.tools.mapeditor.imgui.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent.Container;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.main.MapWorld;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiWindowFlags;

public class BottomBar extends Container {
	
	private Vector3 worldPos = new Vector3();
	
	public BottomBar() {
		super();
		this.title = "Bottom Bar";
		this.windowCond = ImGuiCond.Always;
		this.windowFlags = ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoMove; 
		applyDefaults();
	}
	
	@Override
	public void renderContent(float delta) {
//		size[0] = Gdx.graphics.getWidth();
//		size[1] = 30;
//		position[0] = 0;
//		position[1] = Gdx.graphics.getHeight() - size[1];
		
    	if(MapEditorGame.currentFile.get() == null) {
    		ImGui.text("file: " + "(New map)");
    	} else {
    		ImGui.text("file: " + MapEditorGame.currentFile.get().name());
    	}
		separate();
    	ImGui.text(" |  fps " + Gdx.graphics.getFramesPerSecond() + "");
		separate();
    	ImGui.text(" |  camPos " + MapEditorGame.screen.getCamera().position + "");
		separate();
		ImGui.text(" |  camDir " + MapEditorGame.screen.getCamera().direction + "");
		separate();
		ImGui.text(" |  camUp " + MapEditorGame.screen.getCamera().up + "");
		separate();
//		var z = MapEditorGame.screen.controller.cursorZ - MapEditorGame.screen.controller.floorHeight;
//		var worldpos = MapEditorGame.screen.controller.getCursorWorldPos().add(0, 0, z);
		MapWorld.world.cursor.transform.getTranslation(worldPos);
		ImGui.text(" |  cursor (" + Gdx.input.getX() + "," + Gdx.input.getY() + ") " + worldPos);
	}
	
	private void separate() {
		ImGui.sameLine();
		// ImGui.separator();
	}
	
	@Override
	protected void applyDefaults() {
		size[0] = Gdx.graphics.getWidth();
		size[1] = 30;
		position[0] = 0;
		position[1] = Gdx.graphics.getHeight() - size[1];
	}

	@Override
	public void resizeScreen(int screenW, int screenH) {
		super.resizeScreen(screenW, screenH);
		applyDefaults();
	}
	
}
