package com.souchy.randd.ebishoal.sapphire.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kotcrab.vis.ui.VisUI;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.ebishoal.sapphire.ui.GameScreenHud;

public class SapphireGame extends LapisGame {
	

	public Skin skin;
	
	@Override
	public void onCreateHook() {

		VisUI.load();
		skin = new Skin(Gdx.files.internal("res/uiskin.json"));
		System.out.println("skin =" + skin);
		//labelStyles.add(new LabelStyle());
	}


	@Override
	public Screen getStartScreen() {
		var hud = new GameScreenHud(); 
		//var hud = new ShitScreen(); 
		hud.create();
		return hud; //new GameScreen();
	}
	
}
