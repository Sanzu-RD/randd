package com.souchy.randd.ebishoal.sapphire.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;

public class SapphireAssets {

//	public static String getCreatureModelPath(String modelName) {
//		return Gdx.files.internal("res/models/creatures/" + modelName + ".g3dj").path().toString().replace("\\",  "/");
//	}
	
	
	public static String getCreatureIconPath(String iconName) {
		return getSkinPath(Gdx.files.internal("res/textures/creatures/" + iconName + ".png").path().toString().replace("\\",  "/"));
	}
	
	public static String getSpellIconPath(String iconName) {
		return getSkinPath(Gdx.files.internal("res/textures/spells/" + iconName + ".png").path().toString().replace("\\",  "/"));
	}
	
	public static String getStatusIconPath(String iconName) {
		return getSkinPath(Gdx.files.internal("res/textures/statuses/" + iconName + ".png").path().toString().replace("\\",  "/"));
	}
	
	private static String getSkinPath(String texturepath) {
		return texturepath.substring(texturepath.indexOf("textures"), texturepath.lastIndexOf(".")).replace("/", ".");
	}
	
	public static I18NBundle getI18N_UX() {
		return LapisAssets.get("res/i18n/ux/bundle", I18NBundle.class);
	}
	public static I18NBundle getI18N_Creatures() {
		return LapisAssets.get("res/i18n/creatures/bundle", I18NBundle.class);
	}
	public static I18NBundle getI18N_Spells() {
		return LapisAssets.get("res/i18n/spells/bundle", I18NBundle.class);
	}
	public static I18NBundle getI18N_Status() {
		return LapisAssets.get("res/i18n/status/bundle", I18NBundle.class);
	}
	public static I18NBundle getI18N_Elements() {
		return LapisAssets.get("res/i18n/elements/bundle", I18NBundle.class);
	}
	public static I18NBundle getI18N_Effects() {
		return LapisAssets.get("res/i18n/effects/bundle", I18NBundle.class);
	}
	
//	public static I18N i18n;
//
//	public class I18N {
//		public I18NBundle ux;
//		public I18NBundle creatures;
//		public I18NBundle spells;
//		public I18NBundle status;
//		public I18NBundle elements;
//		public I18NBundle effects;
//	}
//	
//	public SapphireAssets() {
//		i18n = new I18N();
//		i18n.ux = LapisAssets.assets.get("res/i18n/ux/bundle", I18NBundle.class);
//		i18n.creatures = LapisAssets.assets.get("res/i18n/creatures/bundle", I18NBundle.class);
//		i18n.spells = LapisAssets.assets.get("res/i18n/spells/bundle", I18NBundle.class);
//		i18n.status = LapisAssets.assets.get("res/i18n/status/bundle", I18NBundle.class);
//		i18n.elements = LapisAssets.assets.get("res/i18n/elements/bundle", I18NBundle.class);
//		i18n.effects = LapisAssets.assets.get("res/i18n/effects/bundle", I18NBundle.class);
//	}
	
	
}
