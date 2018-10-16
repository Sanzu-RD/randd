package com.souchy.randd.ebishoal.commons.lapis.main;

import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.souchy.randd.ebishoal.commons.lapis.discoverers.FontDiscoverer;
import com.souchy.randd.ebishoal.commons.lapis.discoverers.ModelDiscoverer;

import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;


public abstract class LapisGame extends Game {
	
	
	public final FontDiscoverer fonts;
	public final ModelDiscoverer models;
	
	public List<LabelStyle> labelStyles;
	
	/**
	 * Cannot have any use of Gdx. here as the application hasn't started yet
	 */
	public LapisGame() {
		fonts = new FontDiscoverer();
		models = new ModelDiscoverer();
	}
	
	
	/**
	 * Don't call this yourself. It is automatically called by LibGDX.
	 * @inheritDoc
	 */
	@Override
	public void create() {
		labelStyles = fonts.explore("res");
		//models.explore("");
		
		//createScreens();
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
