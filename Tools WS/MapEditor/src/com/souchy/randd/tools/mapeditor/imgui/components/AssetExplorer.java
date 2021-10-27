package com.souchy.randd.tools.mapeditor.imgui.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent.Container;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.main.MapWorld;

import imgui.ImGui;
import imgui.flag.ImGuiTableFlags;
import imgui.flag.ImGuiWindowFlags;

/**
 * Asset explorer
 * 
 * @author Blank
 * @date 26 oct. 2021
 */
public class AssetExplorer extends Container {
	
	// getting the names from the objects is too costly (AssetManager foreaches *every* loaded asset to find the corresponding one)
	//Array<Model> models = new Array<>();
	//Array<Texture> textures = new Array<>();
	//Array<String> shaders = new Array<>();
	
	public AssetExplorer() {
		super();
		this.title = "Asset Explorer";
		;
		this.size = new int[] { 1000, 200 };
		this.position = new int[] { 0, MapEditorGame.screen.imgui.bottom.position[1] - size[1] };
	}
	
	@Override
	public void renderContent(float delta) {
		if(ImGui.beginTabBar("AssetExplorer")) {
			if(ImGui.beginTabItem("Models")) {
				foreach(Model.class);
				ImGui.endTabItem();
			}
			if(ImGui.beginTabItem("Textures")) {
				foreach(Texture.class);
				ImGui.endTabItem();
			}
			if(ImGui.beginTabItem("Shaders")) {
				foreach(ShaderProgram.class);
				ImGui.endTabItem();
			}
			
			ImGui.endTabBar();
		}
	}
	
	/**
	 * hate this code but can't do better because of libgdx's base AssetManager. would need to copy the entire code to modify it
	 */
	private <T> void foreach(Class<T> c) {
		int i = 0;
		if(ImGui.beginTable("asset table", 5, ImGuiTableFlags.ScrollY)) {
			ImGui.tableNextRow();
			for(var asset : LapisAssets.getAssetNames()) {
				if(LapisAssets.getType(asset) == c) {
	//				ImGui.beginGroup();
	//				{
	//					thing(c, asset);
	//				}
	//				ImGui.endGroup();
	//				ImGui.sameLine();
					
					// asset table
					// ImGui.tableNextRow();
					ImGui.tableSetColumnIndex(i++);
					ImGui.pushID(i);
					//ImGui.nextColumn();
					{
						cell(c, asset);
					}
					ImGui.popID();
				}
			}
	        ImGui.endTable();
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void cell(Class c, String asset) {
		int texid = 0;
		if(c == Texture.class) {
			texid = LapisAssets.<Texture>get(asset).getTextureObjectHandle();
		}
		if(ImGui.imageButton(texid, 100, 100)) {
			// if model, load an instance of it and set it selected in the controller
			if(c == Model.class) {
				//Log.info("AssetExplorer click model");
				if(MapEditorGame.screen.controller.selectedInstance != null)
					MapWorld.world.instances.remove(MapEditorGame.screen.controller.selectedInstance);
				Model model = LapisAssets.<Model>get(asset);
				ModelInstance instance = new ModelInstance(model);
				MapEditorGame.screen.controller.selectedInstance = instance;
				MapEditorGame.screen.controller.selectedModel = model; 
				MapWorld.world.instances.add(instance);
				MapEditorGame.screen.controller.updateCursor();
			}
		}
		ImGui.text(asset);
	}
	
	
}
