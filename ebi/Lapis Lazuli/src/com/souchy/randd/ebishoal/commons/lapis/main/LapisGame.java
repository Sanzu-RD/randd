package com.souchy.randd.ebishoal.commons.lapis.main;

import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kotcrab.vis.ui.VisUI;
import com.souchy.randd.ebishoal.commons.lapis.discoverers.FontDiscoverer;
import com.souchy.randd.ebishoal.commons.lapis.discoverers.ModelDiscoverer;
import com.souchy.randd.ebishoal.commons.lapis.managers.ModelManager;
import com.souchy.randd.ebishoal.commons.lapis.world.World;
import com.souchy.randd.situationtest.models.org.FightContext;


public abstract class LapisGame extends Game {


	public final FontDiscoverer fonts;
	public final ModelDiscoverer modelDiscoverer;
	
	public final AssetManager assets;
	public final ModelManager modelManager;
	
	public List<LabelStyle> labelStyles;
	
	//public final FightContext context;
	
	
	/**
	 * Cannot have any use of Gdx. here as the application hasn't started yet
	 */
	public LapisGame() {
		assets = new AssetManager();
		fonts = new FontDiscoverer();
		modelDiscoverer = new ModelDiscoverer();
		modelManager = new ModelManager(assets);
	}
	
	
	/**
	 * Don't call this yourself. It is automatically called by LibGDX.
	 * @inheritDoc
	 */
	@Override
	public void create() {
		VisUI.load();
		labelStyles = fonts.explore(""); //"res");
		List<FileHandle> files = modelDiscoverer.explore("g3d"); //"res");
		files.forEach(f -> {
			modelManager.load(f.path());
		});
		assets.finishLoading();
		
		onCreateHook();
		setScreen(getStartScreen());
	}

	/**
	 * 
	 * Create / initialize a bunch of screens for later use ? maybe dont need this, i kinda dislike going towards singletons again
	 */
	//public abstract void createScreens();
	
	/**
	 * 
	 * @return The first screen you want to startup with and show
	 */
	public abstract Screen getStartScreen();
	
	/**
	 * 
	 */
	public abstract void onCreateHook();
	
	
}
