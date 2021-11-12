package com.souchy.randd.tools.mapeditor.imgui.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.souchy.randd.commons.tealwaters.commons.Lambda;

import imgui.ImGui;

/**
 * Popup dialog to select an asset (for example set a texture on a TextureAttribute)
 * 
 * @author Blank
 * @date 30 oct. 2021
 */
public class AssetDialog extends AssetExplorer {
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