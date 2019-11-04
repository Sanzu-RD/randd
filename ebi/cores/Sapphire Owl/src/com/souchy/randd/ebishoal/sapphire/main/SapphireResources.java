package com.souchy.randd.ebishoal.sapphire.main;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.souchy.randd.commons.tealwaters.logging.Log;

import data.modules.AzurCache;
import gamemechanics.statics.creatures.CreatureType;

/**
 * 
 * @author Blank
 * @date 29 juill. 2019
 */
public class SapphireResources {
	
	// ça pourrait être dans une classe de sapphire, ex SapphireAsset,
	// SapphireResource...
	// c'aurait un assetmanager STATIC
	
	/**
	 * Assets of everything for everything Load everything into this
	 */
	public static AssetManager assets = new AssetManager();
	
	/**
	 * Load all resources for all AzurModules (assets for creatures, items and spells) into the global asset manager
	 * We can start with this and add lazier loading later
	 * @param cache
	 */
	public static void loadResources(AzurCache cache) {
		var params = new TextureParameter();
		params.genMipMaps = true;
		
		cache.creatures.values().forEach(model -> {
			// load i18n
			var i18nPath = getI18nPath(model.getStrID());
			assets.load(i18nPath, I18NBundle.class);
		
			// load creature avatar
			var avatarPath = getCreatureGfx(model.getStrID(), model.getAvatarName());
			assets.load(avatarPath, Texture.class, params);
		});
		cache.items.values().forEach(i -> {
//			var iconPath = getItemIconPath(model.getStrID(), s.getIconName());
//			assets.load(iconPath, Texture.class);
		});
		cache.spells.values().forEach(s -> {
			int typeid = s.id() / 1000000 * 1000000;
			int creatureid = s.id() / 1000 * 1000;
			int sid = s.id() - typeid - creatureid;
			var path = "";
			if(typeid > 0) {
				// res/creaturestypes/necromancer/gfx/spellName.png
				path = getCreatureTypeGfx(CreatureType.values()[typeid].name(), s.getIconName()); 
			} else 
			if (creatureid > 0) {
				// res/creatures/sungjin/gfx/spellName.png
				path = getCreatureGfx(cache.creatures.get(creatureid).getStrID(), s.getIconName());
			}
			assets.load(path, Texture.class, params);

//			var iconPath = getSpellIconPath(model.getStrID(), s.getIconName());
//			assets.load(iconPath, Texture.class);
		});

		//assets.load("G:/www/ebishoal/res/creatures/sungjin/gfx/avatar.png", Texture.class);
		assets.finishLoading();
		Log.info("sapphire resources : { " + String.join(",", assets.getAssetNames()) + " }");
		var textures = assets.getAll(Texture.class, new Array<>());
		try {
			textures.forEach(t -> t.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear));
		} catch(Exception e) {
			Log.error("", e);
		}
	}

	
	public static String getCreatureGfx(String creatureName, String iconName) {
		return "res/creatures/" + creatureName + "/gfx/" + iconName;
	}

	public static String getCreatureTypeGfx(String creatureTypeName, String iconName) {
		return "res/creaturestypes/" + creatureTypeName + "/gfx/" + iconName;
	}
	
	public static String getI18nPath(String creatureName) {
		return "res/creatures/" + creatureName + "/i18n/bundle";
	}
	
	public static Texture getCreatureIcon(String iconName) {
		return assets.get(iconName, Texture.class);
	}
	
	public static Model getCreatureModel(int creatureModelID) {
		return assets.get("" + creatureModelID, Model.class);
	}
	
	public static Texture getSpellIcon(String iconName) {
		return assets.get(iconName, Texture.class);
	}
	
	public static Texture getPfx(String iconName) {
		return assets.get(iconName, Texture.class);
	}


}
