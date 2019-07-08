package com.souchy.randd.ebishoal.sapphire.main;

import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;

public class SapphireOwl extends LapisCore {
	
	/**
	 * would be final if we didnt instantiate i
	 */
	public static SapphireOwl core = new SapphireOwl();
	public static SapphireGame game;
	public static SapphireOwlConf conf;
	
	
	public static void main(String[] args) throws Exception {
		launch(core);
	}
	
	@Override
	public void init() throws Exception {
		super.init();
		conf = JsonConfig.readExternal(SapphireOwlConf.class, "./modules/");
	}
	
	@Override
	protected LapisGame createGame() {
		return game = new SapphireGame();
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
