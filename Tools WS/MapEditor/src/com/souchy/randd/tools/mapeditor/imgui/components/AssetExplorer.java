package com.souchy.randd.tools.mapeditor.imgui.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.tools.mapeditor.controls.GeckoControls;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent.Container;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.main.MapWorld;

import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiTableFlags;
import imgui.flag.ImGuiWindowFlags;

/**
 * Asset explorer
 * 
 * @author Blank
 * @date 26 oct. 2021
 */
public class AssetExplorer extends Container {
	
	protected boolean models = true;
	protected boolean textures = true;
	protected boolean shaders = true;
	protected boolean dialog = false;
	
	public AssetExplorer() {
		super();
		this.title = "Asset Explorer";
	}
	
	@Override
	protected void applyDefaults() {
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
			if(ImGui.beginTabItem("Materials")) {
				foreach(Material.class);
				ImGui.endTabItem();
			}
			if(ImGui.beginTabItem("Shaders")) {
				foreach(ShaderProgram.class);
				ImGui.endTabItem();
			}
			ImGui.endTabBar();
		}
	}
	
	public static float cellSize = 80.0f;
	public static float cellMargin = 10.0f;
	/**
	 * hate this lapisassets code but can't do better because of libgdx's base AssetManager. would need to copy the entire code to modify it
	 */
	protected <T> String foreach(Class<T> c) {
		cellSize = 64;
		cellMargin = 15;
		
		//var names = LapisAssets.getAssetNames();
		var assets = LapisAssets.getAll(c, new Array<>());
		//LapisAssets.getName(assets.get(0));
		
		int nc = (int) (ImGui.getWindowWidth() / cellSize);
		//if(assets.size < nc) nc = assets.size;
		if(nc < 1) nc = 1;
		int nr = Math.floorDiv(assets.size, nc) + 1; // assets.size / nc;
		
		if(nr == 0) nr = 1;
		//Log.info("Assets table size "+cellSize+" : assets %s, window %s, cols %s, rows %s", assets.size, ImGui.getWindowWidth(), nc, nr);
		String result = null;
		//int i = 0;
		if(ImGui.beginTable("asset table", nc, ImGuiTableFlags.SizingStretchSame | ImGuiTableFlags.ScrollY)) {
			for(int i = 0; i < nr; i++) {
				ImGui.tableNextRow();
				for(int j = 0; j < nc; j++){ //for(var asset : names) {
					int id = i * nc + j;
					if(id >= assets.size) break;
					var name = LapisAssets.getName(assets.get(id));
						ImGui.tableSetColumnIndex(j);
						ImGui.pushID(id);
						{
							if(cell(c, name))
								result = name;
						}
						ImGui.popID();
					//}
				}
			}
	        ImGui.endTable();
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	private boolean cell(Class c, String asset) {
		int texid = 0;
		if(c == Texture.class) {
			texid = LapisAssets.<Texture>get(asset).getTextureObjectHandle();
		}
		if(ImGui.imageButton(texid, cellSize-cellMargin, cellSize-cellMargin)) {
			click(c, asset);
			return true;
		}
//		ImGui.textWrapped(asset.substring(asset.lastIndexOf("/")+1));
		ImGui.textWrapped(asset);
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	protected void click(Class c, String asset) {
		// if model, load an instance of it and set it selected in the controller
		if(c == Model.class) {
			Model model = LapisAssets.<Model>get(asset);
			GeckoControls.selectBrushModel(model);
		}
	}
	
	
	
}
