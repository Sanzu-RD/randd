package com.souchy.randd.ebishoal.sapphire.main;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisFiles;

import data.modules.AzurCache;

/**
 * 
 * @author Blank
 * @date 29 juill. 2019
 */
public class SapphireResources {
	
	// ça pourrait être dans une classe de sapphire, ex SapphireAsset, SapphireResource...
	// c'aurait un assetmanager STATIC
	
	/**
	 * Assets of everything for everything Load everything into this
	 */
	public static AssetManager assets = new AssetManager();
	
	private static final TextureParameter params = new TextureParameter();
	static {
		params.genMipMaps = true;
	}
	
	/**
	 * Load all resources for all AzurModules (assets for creatures, items and spells) into the global asset manager
	 * We can start with this and add lazier loading later
	 * @param cache
	 */
	public static void loadResources(AzurCache cache) {

		// load i18n
		for(var cat : I18nCategory.values())
			assets.load(getI18nPath(cat), I18NBundle.class);
		
		/*
		// load creatures icons
		cache.creatures.values().forEach(model -> {
			assets.load(getCreatureIconPath(model.getIconName()), Texture.class, params);
			assets.load(getCreatureModelPath(model.getIconName()), Model.class);
		});
		
		// load spells icons
		cache.spells.values().forEach(s -> {
			assets.load(getSpellIconPath(s.getIconName()), Texture.class, params);
		});
		*/

		loadTextures(Gdx.files.internal("res/textures"));
		loadModels(Gdx.files.internal("res/models"));
		//loadDirectory(Gdx.files.local("res/i18n"), I18NBundle.class);
		
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
	
	private static void loadTextures(FileHandle dir) {
		recurseFiles(dir, f -> true, 
				f -> assets.load(f.path().substring(f.path().indexOf("res/"), f.path().length()), Texture.class, params));
	}
	
	private static void loadModels(FileHandle dir) {
		recurseFiles(dir, f -> f.name().endsWith("g3dj"), 
				f -> assets.load(f.path().substring(f.path().indexOf("res/"), f.path().length()), Model.class));
	}
	
	public static void recurseFiles(FileHandle dir, Predicate<FileHandle> filter, Consumer<FileHandle> action) {
		for (var f : dir.list()) {
			if(f.isDirectory()) {
				recurseFiles(f, filter, action);
			} else if(filter.test(f)) { 
				action.accept(f);
			}
		}
	}
	
	
	public static String getCreatureIconPath(String iconName) {
		return "res/textures/creatures/" + iconName + ".png";
	}

	public static String getCreatureModelPath(String modelName) {
		return "res/models/creatures/" + modelName + ".g3dj";
	}
	
	public static String getSpellIconPath(String iconName) {
		return "res/textures/spells/" + iconName + ".png";
	}

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

}
