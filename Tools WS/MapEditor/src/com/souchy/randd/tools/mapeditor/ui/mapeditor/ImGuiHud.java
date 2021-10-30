package com.souchy.randd.tools.mapeditor.ui.mapeditor;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

public class ImGuiHud {
	
	ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
	ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
	long windowHandle;
    String glslVersion = "#version 130";
    
	public void create() {
		// imgui
		GLFWErrorCallback.createPrint(System.err).set();
		if(!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		ImGui.createContext();
		final ImGuiIO io = ImGui.getIO();
		io.setIniFilename(null);
		//ImGuiTools.setupFonts(io);

		windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();

		imGuiGlfw.init(windowHandle, true);
		imGuiGl3.init(glslVersion);
	}

	
	public void render(float delta) {
		// render 3D scene
		imGuiGlfw.newFrame();
		ImGui.newFrame();
		{
			// render stuff
			renderContent(delta);
		}
		ImGui.render();
		imGuiGl3.renderDrawData(ImGui.getDrawData());
		//GLFW.glfwSwapBuffers(windowHandle);
		GLFW.glfwPollEvents();
	}
	
	public void renderContent(float delta) {
		
	}
	
	public void resizeScreen(int screenW, int screenH) {
		
	}
	
	public void dispose() {
		imGuiGl3.dispose();
		imGuiGlfw.dispose();
		ImGui.destroyContext();
	}
	
	
}
