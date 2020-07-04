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
	
	
	
/*
	public static String getI18nPath(I18nCategory cat) {
		return "res/i18n/" + cat.name() + "/bundle";
	}
	
	public static Texture getCreatureIcon(String iconName) {
		return assets.get(getCreatureIconPath(iconName), Texture.class);
	}
	
	public static Texture getSpellIcon(String iconName) {
		return assets.get(getSpellIconPath(iconName), Texture.class);
	}
	
	public static Model getCreatureModel(int creatureModelID) {
		return assets.get("" + creatureModelID, Model.class);
	}
	
	public static Texture getPfx(String iconName) {
		return assets.get(iconName, Texture.class);
	}

	public static I18NBundle getI18nBundle(I18nCategory cat) {
		return assets.get(getI18nPath(cat));
	}
	
	public static enum I18nCategory {
		//main,
		ui,
		creatures,
		creaturetypes,
		effects,
		elements,
		spells
	}
	*/
	
}
