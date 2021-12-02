package com.souchy.randd.tools.mapeditor.imgui;

import java.util.function.Consumer;

import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.mapeditor.configs.EditorConf.HudConfig.ComponentConfig;
import com.souchy.randd.tools.mapeditor.main.MapEditorCore;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiDir;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

public interface ImGuiComponent {
	
	public void render(float delta);
	public default void dispose() {
	}
	public default void resizeScreen(int screenW, int screenH) {
		
	}
	
	
	public static abstract class Container implements ImGuiComponent {
		public static Consumer<Container> defaultClose = (c) -> { };
		public static Consumer<Container> defaultShow = (c) -> { };

//		protected int[] defaultPosition = new int[] { 0, 0 };
//		protected int[] defaultSize = new int[] { 800, 700 };
		
		
		private ImVec2 lastSize = new ImVec2();
		private ImVec2 lastPos = new ImVec2();
		public ImBoolean showContainer = new ImBoolean(true);
		public int[] position = new int[] { 0, 0 };
		public int[] size = new int[] { 800, 700 };
		public String title = "Window";
		public int windowFlags = ImGuiWindowFlags.None;
		public int windowCond = ImGuiCond.Once;
		public Consumer<Container> close = defaultClose;
		public Consumer<Container> show = defaultShow;
		public Container() {
			// windowFlags = ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoMove; 
			
			var conf = MapEditorCore.conf.hud.components.get(this.getClass().getName());
			if(conf != null) {
				this.position = conf.pos;
				this.size = conf.size;
				this.showContainer.set(conf.visible);
			} else {
				applyDefaults();
			}
		}
		protected void applyDefaults() {
			this.position[0] = 0; // defaultPosition[0];
			this.position[1] = 0; // defaultPosition[1];
			this.size[0] = 800; // defaultSize[0];
			this.size[1] = 700; // defaultSize[1];
		}
		public void makeWindow() {
	        ImGui.setNextWindowSize(size[0], size[1], windowCond);
	        ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX() + position[0], ImGui.getMainViewport().getPosY() + position[1], windowCond);
		}
		
		@Override
		public void render(float delta) {
			if(!showContainer.get()) {
				Log.info("close " + this.hashCode());
				close();
				return;
			}
			makeWindow();
	        if (ImGui.begin(title, showContainer, windowFlags)) {
	        	ImVec2 size = ImGui.getWindowSize();
	        	if(!lastSize.equals(size)) 
	        		resize(lastSize, size);
	        	
	        	ImVec2 pos = ImGui.getWindowPos();
	        	if(!lastPos.equals(pos)) 
	        		move(lastPos, pos);
	        	
	        	
	        	//Log.info("Container1 " + showContainer.get() + " " + this.hashCode());
	        	renderContent(delta);
	        } else {
	        	//Log.info("Container2 " + showContainer.get());
	        }
	        ImGui.end();
		}
		public abstract void renderContent(float delta);
		public void show() {
			show.accept(this);
		}
		public void close() {
			close.accept(this);
		}
		public void toggleShow() {
			if(this.showContainer.get())
				close();
			else
				show();
		}
		public void resize(ImVec2 last, ImVec2 next) {
			last.set(next);
			size[0] = (int) next.x;
			size[1] = (int) next.y;
		}
		public void move(ImVec2 last, ImVec2 next) {
			last.set(next);
        	position[0] = (int) next.x;
        	position[1] = (int) next.y;
		}
		@Override
		public void dispose() {
			MapEditorCore.conf.hud.components.put(this.getClass().getName(), new ComponentConfig(showContainer.get(), position, size));
		}
	}
	
}
