package com.souchy.randd.tools.mapeditor.imgui.windows;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.mapeditor.controls.Commands.LapisCommands;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent.Container;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiKeyModFlags;
import imgui.flag.ImGuiTableFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImString;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.*;

public class Console extends Container {

	private int inputIndex = 0;
	public List<String> inputHistory = new ArrayList<>();
	public List<String> messages = new ArrayList<>();
	public ImString input = new ImString(300);
	
	private int dirty = 2;
	
	public Console() {
		this.title = "Console";
		this.windowFlags |= ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse;
		
		//for(char i = 'a'; i <= 'z'; i++) {
		//	messages.add("" + i);
		//}
		inputHistory.add("");
	}
	
	@Override
	protected void applyDefaults() {
		this.position = new int[] { 10, 30 };
		this.size = new int[] { 150, 200 };
	}

	@Override
	public void renderContent(float delta) {
		// message table
		if(ImGui.beginTable("console table", 1, ImGuiTableFlags.ScrollY)) {
			ImGui.tableNextRow();
			ImGui.tableSetColumnIndex(0);
            {
				for (var m : messages) {
					ImGui.textWrapped(m);
					// ImGui.newLine();
				}
            }
	
            // scroll the message table
			if(dirty > 0) {
				ImGui.setScrollY(ImGui.getScrollMaxY());
				dirty--;
			}
            ImGui.endTable();
		}
		// scroll the console window
		if(dirty > 0) {
			ImGui.setScrollY(ImGui.getScrollMaxY());
			dirty--;
		}
		// input
		{
			ImGui.pushItemWidth(ImGui.getWindowSizeX());
				if(ImGui.inputText("", input, ImGuiInputTextFlags.EnterReturnsTrue | ImGuiInputTextFlags.AllowTabInput | ImGuiInputTextFlags.CtrlEnterForNewLine)) {
					var text = input.get();
					add(text);
					inputHistory.add(text);
					inputIndex = inputHistory.size() - 1;
					input.set("");
					ImGui.setKeyboardFocusHere();
					LapisCommands.process(text);
					dirty += 5;
				}
			ImGui.popItemWidth();
			
//			if(ImGui.isKeyDown(ImGuiKey.UpArrow)) {
//				Log.info("up");
//			}
//			if(ImGui.isKeyDown(Keys.DOWN)) {
//				// User fill ImGuiIO.KeyMap[] array with indices into the ImGuiIO.KeysDown[512] array
//				Log.info("down");
//			}
//			if(ImGui.isKeyDown(ImGuiKey.LeftArrow)) {
//				// User fill ImGuiIO.KeyMap[] array with indices into the ImGuiIO.KeysDown[512] array
//				Log.info("left");
//			}
			if(ImGui.isKeyPressed(GLFW_KEY_UP) && ImGui.isItemActive()) {
				Log.info("up");
				inputIndex--;
				if(inputIndex <= 0) inputIndex = 0;
				input.set(inputHistory.get(inputIndex));
			}
			if(ImGui.isKeyPressed(GLFW_KEY_DOWN) && ImGui.isItemActive()) {
				Log.info("down");
				inputIndex++;
				if(inputIndex >= inputHistory.size()) {
					 inputIndex = inputHistory.size();
				} else {
					input.set(inputHistory.get(inputIndex));
				}
			}
		}
	}
	
	@Override
	public void resize(ImVec2 last, ImVec2 next) {
		super.resize(last, next);
		dirty += 2;
	}
	
	public void add(String msg) {
		messages.add(msg);
	}
	
}
