package com.souchy.randd.ebishoal.commons.lapis.managers;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader.ParticleEffectLoadParameter;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;

/**
 * 
 * @author Blank
 * @date 29 juill. 2019
 */
public class LapisAssets {
	
	/**
	 * Assets of everything for everything Load everything into this
	 */
	private static final AssetManager assets = new AssetManager();

	public static final Model defaultModel;
	public static final Texture defaultTexture;
	private static final TextureParameter textureParams = new TextureParameter();
	
	public static String rootDir = "";
	public static boolean blocking = true;
	
	
	static {
		textureParams.genMipMaps = true;
		textureParams.minFilter = TextureFilter.MipMapLinearLinear;
		textureParams.magFilter = TextureFilter.MipMapLinearLinear;
		rootDir = Environment.root.toString().replace("\\", "/") + "/";
		
		var mat = new Material(ColorAttribute.createDiffuse(Color.FIREBRICK), new BlendingAttribute(1));
		defaultModel = new ModelBuilder().createCylinder(0.5f, 1.5f, 0.5f, 16, mat, Usage.Position | Usage.Normal);
		//defaultModel.nodes.get(0).id = "defaultModel";
		
		var pix = new Pixmap(10, 10, Format.RGBA8888);
		pix.setColor(Color.FIREBRICK);
		//pix.drawCircle(4, 4, 3);
		//pix.drawRectangle(0, 0, 10, 10);
		pix.drawLine(0, 0, 9, 9);
		pix.drawLine(0, 9, 9, 0);
		defaultTexture = new Texture(pix);
	}
	
	public static AssetManager hack() {
		return assets;
	}
	
	private static boolean finishedLoading = false;
	public static boolean update() {
		//assets.update(); 
		if(finishedLoading) return false;
		finishedLoading = assets.update(); //assets.isFinished();
		return finishedLoading;
	}
	
	/**
	 * Returns true if an asset with the specified name and type is loading, queued to be loaded, or has been loaded. 
	 */
	public static boolean contains(String path, @SuppressWarnings("rawtypes") Class type) {
		return assets.contains(path, type) || assets.contains(rootDir + path, type);
	}
	/**
	 * whether the asset is loaded 
	 */
	public static boolean isLoaded(String path, @SuppressWarnings("rawtypes") Class type) {
		return assets.isLoaded(path, type) || assets.isLoaded(rootDir + path, type);
	}
	
	public static <T> T get(String path) {
		if(assets.isLoaded(path))
			return assets.get(path);
		if(assets.isLoaded(rootDir + path))
			return assets.get(rootDir + path);
		return null;
		
//		if(assets.getAssetNames().contains(path, false)) 
//			return assets.get(path);
//		else 
//			return assets.get(rootDir + path);
	}

	public static <T> T get(String path, Class<T> c) {
		if(assets.isLoaded(path, c))
			return assets.get(path, c);
		if(assets.isLoaded(rootDir + path, c))
			return assets.get(rootDir + path, c);
		if(c == Texture.class)
			return (T) defaultTexture;
		if(c == Model.class)
			return (T) defaultModel;
		return null;
	}
	
	public static Array<String> getAssetNames(){
		return assets.getAssetNames();
	}
	
	public static <T> Array<T> getAll(Class<T> type, Array<T> out) {
		return assets.getAll(type, out);
	}
	
	/**
	 * Can accept a dir or a file
	 */
	public static void loadTextures(FileHandle... handles) {
		for(var handle : handles) {
			recurseFiles(handle, 
				f -> f.name().toLowerCase().endsWith(".png") || f.name().toLowerCase().endsWith(".jpg") || f.name().toLowerCase().endsWith(".jpeg") || f.name().toLowerCase().endsWith(".bmp"), 
				f -> {
					if(assets.contains(f.path())) assets.unload(f.path());
					assets.load(f.path(), Texture.class, textureParams);
				}
			);
		}
		try {
			if(blocking) assets.finishLoading();
		} catch (Exception e) {
			Log.warning("unable to load a texture : ", e);
		}
		
//		var textures = assets.getAll(Texture.class, new Array<>());
//		try {
//			textures.forEach(t -> t.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear));
//		} catch(Exception e) {
//			Log.error("", e);
//		}
	}
	
	/**
	 * accepts directories or files
	 */
	public static void loadModels(FileHandle... handles) {
		for(var handle : handles) {
			recurseFiles(handle, 
				f -> f.name().endsWith(".g3dj"), 
				f -> {
					if(assets.contains(f.path())) assets.unload(f.path());
					assets.load(f.path(), Model.class);
				}
			);
		}
		try {
			if(blocking) assets.finishLoading();
		} catch (Exception e) {
			Log.warning("unable to load a model : ", e);
		}
	}
	
	public static void loadI18NBundles(FileHandle... handles) {
		I18NBundle.setExceptionOnMissingKey(false);
		for(var handle : handles) {
			recurseDirectories(handle, 
				d -> d.list((f, n) -> n.startsWith("bundle") && n.endsWith(".properties")).length > 0,
				d -> {
					if(assets.contains(d.path())) assets.unload(d.path());
					assets.load(d.path() + "/bundle", I18NBundle.class);
				}
			);
		}
		if(blocking) assets.finishLoading();
	}
	
//	public static void loadParticleEffects(ParticleEffectLoadParameter params, FileHandle... handles) {
//		for(var handle : handles) {
//			recurseFiles(handle, 
//					f -> f.name().endsWith(".pfx"), 
//					f -> assets.load(f.path(), ParticleEffect.class, params));
//		}
//		try {
//			assets.finishLoading();
//		} catch (Exception e) {
//			Log.warning("unable to load a pfx : ", e);
//		}
//	}

	public static void loadMusics(FileHandle... handles) {
		for(var handle : handles) {
			recurseFiles(handle, 
				f -> f.name().endsWith(".mp3"), 
				f -> {
					if(assets.contains(f.path())) assets.unload(f.path());
					assets.load(f.path(), Music.class);
				}
			);
		}
		if(blocking) assets.finishLoading();
	}
	
	public static void loadSounds(FileHandle... handles) {
		for(var handle : handles) {
			recurseFiles(handle, 
				f -> f.name().endsWith(".mp3"), 
				f -> {
					if(assets.contains(f.path())) assets.unload(f.path());
					assets.load(f.path(), Sound.class);
				}
			);
		}
		if(blocking) assets.finishLoading();
	}
	
	public static void loadFonts(FileHandle... handles) {
//		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/myfont.ttf"));
//		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
//		parameter.size = 12;
//		BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
//		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		
		
		FileHandleResolver resolver = new InternalFileHandleResolver();
		assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assets.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
		
		
		
		var dmgParam = JsonConfig.readAny(FreeTypeFontParameter.class, "res/fonts/damage.param");
		FreeTypeFontLoaderParameter dmgLoaderParam = new FreeTypeFontLoaderParameter();
		dmgLoaderParam.fontFileName = "res/fonts/BADABB__.TTF";
		dmgLoaderParam.fontParameters = dmgParam;
		assets.load("gen_damage.ttf", BitmapFont.class, dmgLoaderParam);

		var normalParam = JsonConfig.readAny(FreeTypeFontParameter.class, "res/fonts/normal.param");
		FreeTypeFontLoaderParameter normalLoaderParam = new FreeTypeFontLoaderParameter();
		normalLoaderParam.fontFileName = "res/fonts/OptimusPrinceps.ttf";
		normalLoaderParam.fontParameters = normalParam;
		assets.load("gen_normal.ttf", BitmapFont.class, normalLoaderParam);
		

		var titleParam = JsonConfig.readAny(FreeTypeFontParameter.class, "res/fonts/title.param");
		FreeTypeFontLoaderParameter titleLoaderParam = new FreeTypeFontLoaderParameter();
		titleLoaderParam.fontFileName = "res/fonts/OptimusPrincepsSemiBold.ttf";
		titleLoaderParam.fontParameters = titleParam;
		assets.load("gen_title.ttf", BitmapFont.class, titleLoaderParam);

//		Log.info("Environment root : " + Environment.root.toAbsolutePath());
//		Log.critical("normal font params : " + normalParam);
		
		/*
		recurseFiles(dir, 
				f -> f.name().toLowerCase().endsWith(".param"), // load bitmap fonts
				f -> {
					
					assets.load(f.path(), BitmapFont.class);
				});
		
		recurseFiles(dir, 
				f -> f.name().toLowerCase().endsWith(".fnt"), // load bitmap fonts
				f -> assets.load(f.path(), BitmapFont.class));

		recurseFiles(dir, 
				f -> f.name().toLowerCase().endsWith(".ttf"),  // load truetype fonts
				f -> assets.load(f.path(), FreeTypeFontGenerator.class));
		*/
		if(blocking) assets.finishLoading();
	}
	

	private static void recurseDirectories(FileHandle dir, Predicate<FileHandle> filter, Consumer<FileHandle> action) {
		finishedLoading = false;
//		Log.info("dirtype: " + dir.type().name() + ", dir path : " + dir.path());
		for (var d : dir.list()) {
			if(d.isDirectory()) {
				if(filter.test(d)) {
					//Log.info("dtype: " + d.type().name() + ", d path : " + d.path());
					action.accept(d);
				} else {
					recurseDirectories(d, filter, action);
				}
			}
		}
	}
	
	private static void recurseFiles(FileHandle dir, Predicate<FileHandle> filter, Consumer<FileHandle> action) {
		finishedLoading = false;
		if(dir.isDirectory()) {
			for (var f : dir.list()) {
				recurseFiles(f, filter, action);
			}
		} else {
			if(filter.test(dir)) { 
//				Log.info("ftype: " + f.type().name() + ", f path : " + f.path());
				action.accept(dir);
			}
		}
		
//		Log.info("dirtype: " + dir.type().name() + ", dir path : " + dir.path());
//		for (var f : dir.list()) {
//			if(f.isDirectory()) {
//				recurseFiles(f, filter, action);
//			} else 
//			if(filter.test(f)) { 
////				Log.info("ftype: " + f.type().name() + ", f path : " + f.path());
//				action.accept(f);
//			}
//		}
	}
	
}
