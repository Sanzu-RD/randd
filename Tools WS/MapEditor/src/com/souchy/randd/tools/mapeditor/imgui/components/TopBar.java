package com.souchy.randd.tools.mapeditor.imgui.components;

import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;

import imgui.ImGui;

public class TopBar implements ImGuiComponent {

	@Override
	public void render(float delta) {
		ImGui.text("top bar");
	}
	
}
