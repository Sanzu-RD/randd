package com.souchy.randd.ebishoal.sapphire.gfx;

import com.badlogic.gdx.Gdx;

public class SapphireAssets {

	public static String getCreatureIconPath(String iconName) {
		return getSkinPath(Gdx.files.internal("res/textures/creatures/" + iconName + ".png").path().toString().replace("\\",  "/"));
	}

//	public static String getCreatureModelPath(String modelName) {
//		return Gdx.files.internal("res/models/creatures/" + modelName + ".g3dj").path().toString().replace("\\",  "/");
//	}
	
	public static String getSpellIconPath(String iconName) {
		return getSkinPath(Gdx.files.internal("res/textures/spells/" + iconName + ".png").path().toString().replace("\\",  "/"));
	}
	
	private static String getSkinPath(String texturepath) {
		return texturepath.substring(texturepath.indexOf("textures"), texturepath.lastIndexOf(".")).replace("/", ".");
	}
	

}
