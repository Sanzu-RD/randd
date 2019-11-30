package com.souchy.randd.ebishoal.commons.lapis.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.kotcrab.vis.ui.VisUI;
import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.ebishoal.commons.lapis.discoverers.ModelDiscoverer;
import com.souchy.randd.ebishoal.commons.lapis.managers.ModelManager;


public abstract class LapisGame extends Game {


	//public final FontDiscoverer fonts;
	//public final ModelDiscoverer modelDiscoverer;
	
	//public final AssetManager assets;
	//public final ModelManager modelManager;
	
	//public List<LabelStyle> labelStyles;
	
	//public final FightContext context;
	
	
	/**
	 * Cannot have any use of Gdx. here as the application hasn't started yet
	 */
	public LapisGame() {
		//assets = new AssetManager();
		//fonts = new FontDiscoverer();
		//modelDiscoverer = new ModelDiscoverer();
		//modelManager = new ModelManager(assets);
	}
	
	
	/**
	 * Don't call this yourself. It is automatically called by LibGDX.
	 * @inheritDoc
	 */
	@Override
	public void create() {
		if(LapisCore.isEclipse) {
			Gdx.files = new LapisFiles(Environment.root.toString());//"G:/www/ebishoal/");
		}
		VisUI.load();
		/*
		//labelStyles = fonts.explore(""); //"res");
		List<FileHandle> files = modelDiscoverer.explore("g3d"); //"res");
		
		files.forEach(f -> {
			modelManager.load(f.path());
		});
		assets.finishLoading();
		*/
		
		init();
		setScreen(getStartScreen());
	}

	
	/**
	 * Create screens etc.
	 */
	public abstract void init();
	
	/**
	 * @return The first screen you want to startup with and show
	 */
	public abstract Screen getStartScreen();
	
	
}
