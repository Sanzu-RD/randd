package com.souchy.randd.tools.mapeditor.actions;

import com.badlogic.gdx.Gdx;
import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.tools.mapeditor.configs.EditorConf;
import com.souchy.randd.tools.mapeditor.main.MapEditorCore;

public class Actions {

	
	public static void resetConf() {
		MapEditorCore.conf = new EditorConf(); 
		MapEditorCore.conf.save();
	}

	public static void reloadAssets() {
		reloadAssetModels();
		reloadAssetTextures();
	}
	public static void reloadAssetModels() {
		LapisAssets.loadModels(Gdx.files.internal("res/models/"));
	}
	public static void reloadAssetTextures() {
		LapisAssets.loadTextures(Gdx.files.internal("res/textures/"));
	}
	
	public static void reloadAssetData(Object... args) {
		AssetData.loadResources();
	}

	
}
