package com.souchy.randd.tools.mapeditor.imgui.windows;

import com.badlogic.gdx.Gdx;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.RenderOptions;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent.Container;
import com.souchy.randd.tools.mapeditor.imgui.components.AutoTable;
import com.souchy.randd.tools.mapeditor.imgui.components.MaterialProperties;
import com.souchy.randd.tools.mapeditor.main.MapEditorCore;

import imgui.ImGui;

public class Settings extends Container {
	
	private AutoTable tableGfx;
	private AutoTable tableRender;
	private AutoTable tableHud;
	
	//public MaterialProperties props = new MaterialProperties();
	
	public Settings() {
		this.title = "Settings";
		
		tableGfx = new AutoTable(MapEditorCore.conf.gfx);
		tableRender = new AutoTable(new RenderOptions());
		tableHud = new AutoTable(MapEditorCore.conf.hud);
	}
	
	@Override
	protected void applyDefaults() {
		size[0] = 800;
		size[1] = 700;
		position[0] = Gdx.graphics.getWidth() / 2 - size[0] / 2;
		position[1] = Gdx.graphics.getHeight() / 2 - size[1] / 2;
	}

	
	@Override
	public void renderContent(float delta) {
		if(ImGui.beginTabBar("settings tabs")) {
			if(ImGui.beginTabItem("Graphics")) {
				tableGfx.render(delta);
				ImGui.separator();
				tableRender.render(delta);
				ImGui.endTabItem();
			}
			if(ImGui.beginTabItem("Hud")) {
				tableHud.render(delta);
				ImGui.endTabItem();
			}
			ImGui.endTabBar();
		}
	}
	
	
	
	
}
