package com.souchy.randd.ebishoal.sapphire.main;

import com.badlogic.gdx.math.Vector2;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;

public class SapphireOwl extends LapisCore {
	
	public final static SapphireOwl core = new SapphireOwl();
	
	public static void main(String[] args) throws Exception {
		launch(core);
	}
	
	@Override
	protected LapisGame createGame() {
		return new SapphireGame();
	}
	
	@Override
	public String getAppTitle() {
		return "Sapphire Owl";
	}
	
	@Override
	protected String[] getRootPackages() {
		return new String[] { "com.souchy.randd.ebishoal.sapphire" };
	}
	
	@Override
	public Vector2 getSize() {
		// TODO Auto-generated method stub
		return new Vector2(1600, 900);
	}
	
	@Override
	public int getSamples() {
		return 2;
	}
	
	@Override
	public SapphireGame getGame() {
		return (SapphireGame) super.getGame();
	}

	@Override
	public int getMaxFps() {
		return 0;
	}
	
}
