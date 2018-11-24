package com.souchy.randd.ebishoal.sapphire.main;

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
	protected String[] getRootPackages() {
		return new String[] { "com.souchy.randd.ebishoal.sapphire" };
	}
	
	@Override
	public SapphireGame getGame() {
		return (SapphireGame) super.getGame();
	}

	
}
