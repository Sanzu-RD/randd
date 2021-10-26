package com.souchy.randd.tools.mapeditor.ui.mapeditor;

import com.souchy.randd.tools.mapeditor.imgui.components.BottomBar;
import com.souchy.randd.tools.mapeditor.imgui.components.TopBar;

public class EditorImGuiHud extends ImGuiHud {
	
	public BottomBar bottom;
	public TopBar top;
	
	@Override
	public void create() {
		super.create();
		bottom = new BottomBar();
		top = new TopBar();
	}
	
	
	@Override
	public void renderContent(float delta) {
		bottom.render(delta);
		top.render(delta);
	}
	
}
