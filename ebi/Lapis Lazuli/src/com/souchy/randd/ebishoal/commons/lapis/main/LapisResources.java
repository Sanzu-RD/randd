package com.souchy.randd.ebishoal.commons.lapis.main;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader.ParticleEffectLoadParameter;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.logging.Log;

/**
 * 
 * @author Blank
 * @date 29 juill. 2019
 */
public class LapisResources {
	
	/**
	 * Assets of everything for everything Load everything into this
	 */
	public final static AssetManager assets = new AssetManager();
	public static String rootDir = "";
	
	private static final TextureParameter params = new TextureParameter();
	static {
		params.genMipMaps = true;
		rootDir = Environment.root.toString().replace("\\", "/") + "/";
	}
	
	public static <T> T get(String filePath) {
		if(assets.getAssetNames().contains(filePath, false)) return assets.get(filePath);
		else return assets.get(rootDir + filePath);
	}
	
	
	public static void loadTextures(FileHandle dir) {
		recurseFiles(dir, 
				f -> f.name().toLowerCase().endsWith(".png") || f.name().toLowerCase().endsWith(".jpg") || f.name().toLowerCase().endsWith(".jpeg") || f.name().toLowerCase().endsWith(".bmp"), 
				f -> assets.load(f.path(), Texture.class, params)); // f.path().substring(f.path().indexOf("res/"), f.path().length())

		try {
			assets.finishLoading();
		} catch (Exception e) {
			Log.warning("unable to load a texture : ", e);
		}
		var textures = assets.getAll(Texture.class, new Array<>());
		try {
			textures.forEach(t -> t.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear));
		} catch(Exception e) {
			Log.error("", e);
		}
	}
	
	public static void loadModels(FileHandle dir) {
		recurseFiles(dir, 
				f -> f.name().endsWith(".g3dj"), 
				f -> assets.load(f.path(), Model.class));
		try {
			assets.finishLoading();
		} catch (Exception e) {
			Log.warning("unable to load a model : ", e);
		}
	}
	
	public static void loadI18NBundles(FileHandle dir) {
		recurseFiles(dir, 
				d -> d.list((f, n) -> n.startsWith("bundle") && n.endsWith(".properties")).length > 0,
				d -> assets.load(d.path() + "/bundle", I18NBundle.class)
		);
		assets.finishLoading();
	}
	
	public static void loadParticleEffects(FileHandle dir, ParticleEffectLoadParameter params) {
		recurseFiles(dir, 
				f -> f.name().endsWith(".pfx"), 
				f -> assets.load(f.path(), ParticleEffect.class, params));
		try {
			assets.finishLoading();
		} catch (Exception e) {
			Log.warning("unable to load a pfx : ", e);
		}
	}

	public static void loadMusics(FileHandle dir) {
		recurseFiles(dir, 
				f -> f.name().endsWith(".mp3"), 
				f -> assets.load(f.path(), Music.class));
		assets.finishLoading();
	}
	
	public static void loadSounds(FileHandle dir) {
		recurseFiles(dir, 
				f -> f.name().endsWith(".mp3"), 
				f -> assets.load(f.path(), Sound.class));
		assets.finishLoading();
	}
	

	public static void recurseDirectories(FileHandle dir, Predicate<FileHandle> filter, Consumer<FileHandle> action) {
		for (var d : dir.list()) {
			if(d.isDirectory()) {
				recurseDirectories(d, filter, action);
				if(filter.test(d)) {
					action.accept(d);
				}
			}
		}
	}
	
	public static void recurseFiles(FileHandle dir, Predicate<FileHandle> filter, Consumer<FileHandle> action) {
		//Log.info("dirtype: " + dir.type().name() + ", dir path : " + dir.path());
		for (var f : dir.list()) {
			if(f.isDirectory()) {
				recurseFiles(f, filter, action);
			} else if(filter.test(f)) { 
				//Log.info("ftype: " + f.type().name() + ", f path : " + f.path());
				action.accept(f);
			}
		}
	}
	
	
	
}
