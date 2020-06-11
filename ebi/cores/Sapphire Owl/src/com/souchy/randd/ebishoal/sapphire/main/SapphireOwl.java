package com.souchy.randd.ebishoal.sapphire.main;

import java.io.File;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.ebishoal.sapphire.confs.SapphireOwlConf;
import com.souchy.randd.moonstone.white.Moonstone;

public class SapphireOwl extends LapisCore { 


	/**
	 * would be final if we didnt instantiate i
	 */
	public static SapphireOwl core;
	public static SapphireOwlConf conf;
	public static final SapphireGame game = new SapphireGame();
	/**
	 * net comm
	 */
	public static Moonstone moon;
	
	
	public static void main(String[] args) throws Exception {
		LapisCore.arguments(args);
		
		// init les messages & messagehandlers
		core = new SapphireOwl(args);
		
		// si active le net
		if(args.length > 5) {
			var ip = args[1]; // "localhost";
			var port = Integer.parseInt(args[2]); // 443;
			var username = args[3];
			var pass = args[4];
			var fightid = Integer.parseInt(args[5]);

			// authentifie moonstone et join le fight 
			moon = new Moonstone(ip, port, core); 
			moon.auth(username, pass, fightid);
		}
		
		core.start();
	}

	public SapphireOwl(String[] args) throws Exception {
		super(args);
	}
	
	@Override
	public void init() throws Exception {
		super.init();
		// load sapphire config
		conf = JsonConfig.readExternal(SapphireOwlConf.class, "./modules/");
		
		// TODO À l'avenir, on aura un fichier qui map les ressources aux creatureID, spellID, statusID
		// ex CreatureResources : {creatureID} : { "modelpath" }
		// ex SpellResources : {spellid} : {spellIcon}, {spellFX}
		// ex StatusResources : {statusid} : {statusIcon}, {status9patch}, {statusCenterCell} // 9patch et centercell pour les terrainstatus 
		// i18n/francais/spells : {spellid} : { name:"", description:"" } 
		// i18n/francais/statuses : {statusid} : { name:"", description:"" } 
		// peut-être aussi un fichier resources pour des presets de modèles/voxels à utiliser dans les fichiers de maps
		// 		pour les rendre global
		
	}
	
	@Override
	protected LapisGame createGame() {
		return game;
	}

	@Override
	protected String[] getRootPackages(){
		return new String[] { "com.souchy.randd.commons.deathebi.msg", "com.souchy.randd.moonstone", "com.souchy.randd.moonstone.white", "com.souchy.randd.ebishoal.sapphire" };
	}
	
	@Override
	public SapphireGame getGame() {
		return game;
	}

	@Override
	public void addIcon(LwjglApplicationConfiguration config) {
		config.addIcon("res/textures/appicon3.png", FileType.Internal); //.addIcon("G:/Assets/pack/fantasy bundle/tcgcardspack/Tex_krakken_icon.png", FileType.Absolute);
	}

	
}
