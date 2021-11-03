package com.souchy.randd.tools.mapeditor.imgui.components;

import com.badlogic.gdx.Gdx;
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
import com.souchy.randd.tools.mapeditor.imgui.components.AssetExplorer.AssetDialog;
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
	
	
	
	
	
	
	/**
	 * Popup dialog to select an asset (for example set a texture on a TextureAttribute)
	 * 
	 * @author Blank
	 * @date 30 oct. 2021
	 */
	public static class AssetDialog extends AssetExplorer {
		public static AssetDialog modelPicker = new AssetDialog() { { this.textures = false; this.shaders = false; } };
		public static AssetDialog texturePicker = new AssetDialog() { { this.models = false; this.shaders = false; } };
		public static AssetDialog shaderPicker = new AssetDialog() { { this.textures = false; this.models = false; } };
		public AssetDialog() { }
		@Override
		protected void applyDefaults() {
			this.size = new int[] { 600, 300 };
			position[0] = Gdx.graphics.getWidth() / 2 - size[0] / 2;
			position[1] = Gdx.graphics.getHeight() / 2 - size[1] / 2;
		}
		@Override
		public void renderContent(float delta) {
			if(models) foreach(Model.class);
			if(textures) foreach(Texture.class);
			if(shaders) foreach(ShaderProgram.class);
		}
		@Override
		protected void click(Class c, String asset) {
			ImGui.closeCurrentPopup();
		}
		
		public String pick() {
			String picked = null;
			if(models) {
				picked = foreach(Model.class);
			}
			if(textures) {
				picked = foreach(Texture.class);
			}
			if(shaders) {
				picked = foreach(ShaderProgram.class);
			}
			return picked;
		}
		@Override
		public void show() {
			AssetDialog.texturePicker.makeWindow();
			ImGui.openPopup("AssetDialog");
		}
		public void prepare(Lambda l) {
			if(ImGui.beginPopup("AssetDialog", AssetDialog.texturePicker.windowFlags)) {
				l.call(); // called every frame when the popup is opened and rendering
				ImGui.endPopup();
			}
		}
	}
	
	
	
}
