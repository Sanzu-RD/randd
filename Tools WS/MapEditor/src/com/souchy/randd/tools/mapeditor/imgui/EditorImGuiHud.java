package com.souchy.randd.tools.mapeditor.imgui;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.mapeditor.configs.EditorConf.HudConfig.ComponentConfig;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent.Container;
import com.souchy.randd.tools.mapeditor.imgui.windows.AssetExplorer;
import com.souchy.randd.tools.mapeditor.imgui.windows.BottomBar;
import com.souchy.randd.tools.mapeditor.imgui.windows.Console;
import com.souchy.randd.tools.mapeditor.imgui.windows.ObjectsTree;
import com.souchy.randd.tools.mapeditor.imgui.windows.Properties;
import com.souchy.randd.tools.mapeditor.imgui.windows.Settings;
import com.souchy.randd.tools.mapeditor.imgui.windows.TopBar;
import com.souchy.randd.tools.mapeditor.main.MapEditorCore;

import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiKey;

public class EditorImGuiHud extends ImGuiHud {
	
	public TopBar top;
	public BottomBar bottom;
	public ObjectsTree tree;
	public AssetExplorer explorer;
	public Properties properties;
	public Console console;
	public Settings settings;
	
	/**
	 * Thread-safe list of components to draw in the loop.
	 */
	public List<ImGuiComponent> components = new CopyOnWriteArrayList<>();
	
	@Override
	public void init() {
		//super.init();
		IGStyle.initImGui();
	}
	
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
		components.add(properties = new Properties());
		components.add(console = new Console());
		components.add(settings = new Settings());
		//removeContainer(console);
	}
	
	public void showContainer(Container c) {
		c.showContainer.set(true);
		this.components.add(c);
	}
	public void removeContainer(Container c) {
		c.showContainer.set(false); // déjà fait par imgui, mais si on veut enlever un container manuellement
		c.dispose(); // save config
		this.components.remove(c);
	}
	
	@Override
	public void renderContent(float delta) {
		for(var c : components)
			c.render(delta);
	}
	
	@Override
	public void resizeScreen(int screenW, int screenH) {
		for(var c : components)
			c.resizeScreen(screenW, screenH);
	}
	
	@Override
	public void dispose() {
		for(var c : components)
			c.dispose();
		super.dispose();
	}
	
}
