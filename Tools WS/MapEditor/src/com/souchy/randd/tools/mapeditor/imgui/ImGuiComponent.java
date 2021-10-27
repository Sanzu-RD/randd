package com.souchy.randd.tools.mapeditor.imgui;

import java.util.function.Consumer;

import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.logging.Log;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiDir;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

public interface ImGuiComponent {
	
	public void render(float delta);
	
	
	public static abstract class Container implements ImGuiComponent {
		public static Consumer<Container> defaultClose = (c) -> { };
		public static Consumer<Container> defaultShow = (c) -> { };
		
		private ImVec2 lastSize = new ImVec2();
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
		}
		@Override
		public void render(float delta) {
			if(!showContainer.get()) {
				Log.info("close " + this.hashCode());
				close();
				return;
			}
	        ImGui.setNextWindowSize(size[0], size[1], windowCond);
	        ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX() + position[0], ImGui.getMainViewport().getPosY() + position[1], windowCond);
	        if (ImGui.begin(title, showContainer, windowFlags)) {
	        	ImVec2 size = ImGui.getWindowSize();
	        	if(!lastSize.equals(size)) resize(lastSize, size);
	        	
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
		public void resize(ImVec2 last, ImVec2 next) {
			lastSize.set(next);
		}
	}
	
}
