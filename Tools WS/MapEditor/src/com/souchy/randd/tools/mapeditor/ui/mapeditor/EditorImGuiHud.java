package com.souchy.randd.tools.mapeditor.ui.mapeditor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Input.Keys;
import com.souchy.randd.tools.mapeditor.imgui.IGStyle;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent.Container;
import com.souchy.randd.tools.mapeditor.imgui.components.AssetExplorer;
import com.souchy.randd.tools.mapeditor.imgui.components.BottomBar;
import com.souchy.randd.tools.mapeditor.imgui.components.Console;
import com.souchy.randd.tools.mapeditor.imgui.components.ObjectsTree;
import com.souchy.randd.tools.mapeditor.imgui.components.TopBar;

import imgui.ImGui;
import imgui.flag.ImGuiKey;

public class EditorImGuiHud extends ImGuiHud {
	
	public TopBar top;
	public BottomBar bottom;
	public ObjectsTree tree;
	public AssetExplorer explorer;
	public Console console;
	
	/**
	 * Thread-safe list of components to draw in the loop.
	 */
	public List<ImGuiComponent> components = new CopyOnWriteArrayList<>();
	
	@Override
	public void create() {
		super.create();
		Container.defaultClose = this::removeContainer;
		Container.defaultShow = this::showContainer;
		
		// style
		IGStyle.style();
		// capture mouse
		ImGui.getIO().setWantCaptureMouse(true);
		ImGui.getIO().setWantCaptureKeyboard(true);
		// keys
//		ImGui.getIO().setKeyMap(ImGuiKey.UpArrow, Keys.UP);
//		ImGui.getIO().setKeyMap(ImGuiKey.DownArrow, Keys.DOWN);
		
		components.add(bottom = new BottomBar());
		components.add(top = new TopBar());
		components.add(tree = new ObjectsTree());
		components.add(explorer = new AssetExplorer());
		components.add(console = new Console());
		//removeContainer(console);
	}
	
	public void showContainer(Container c) {
		c.showContainer.set(true);
		this.components.add(c);
	}
	public void removeContainer(Container c) {
		c.showContainer.set(false); // déjà fait par imgui, mais si on veut enlever un container manuellement
		this.components.remove(c);
	}
	
	
	@Override
	public void renderContent(float delta) {
		for(var c : components)
			c.render(delta);
	}
	
}
