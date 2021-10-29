package com.souchy.randd.ebishoal.sapphire.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.souchy.jeffekseer.FXLoader;
import com.souchy.randd.commons.deathebi.msg.GetSalt;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.EbiShoalCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.ebishoal.sapphire.confs.SapphireOwlConf;
import com.souchy.randd.ebishoal.sapphire.controls.Commands;
import com.souchy.randd.moonstone.white.Moonstone;

/**
 * Core now separated from Lapis
 * 
 * @author Blank
 * @date 29 oct. 2021
 */
public class SapphireOwl extends EbiShoalCore { 

	/**
	 * would be final if we didnt instantiate i
	 */
	public static SapphireOwl core;
	public static SapphireOwlConf conf;
	public static SapphireGame game;
	/**
	 * net comm
	 */
	public static Moonstone moon;
	
	
	public static void main(String[] args) throws Exception {
		LapisCore.arguments(args);

		FXLoader.read = (s) -> {
	        var handle = Gdx.files.internal(s);
	        byte[] byt = handle.readBytes();
	        return byt;
		};
		
		// init les messages & messagehandlers
		core = new SapphireOwl(args);
		
		// si active le net
		if(args.length > 5) {
			int i = 2;
			var ip = args[i++]; // "localhost";
			var port = Integer.parseInt(args[i++]); // 443;
			var username = args[i++];
			var password = args[i++];
			var fightid = Integer.parseInt(args[i++]);
			
			try {
				Log.info("SapphireOwl connect to moonstone (" + ip + ":" + port + ")  ");
				// authentifie moonstone et join le fight
				moon = new Moonstone(ip, port, core);
				moon.channel.attr(Moonstone.authKey).set(new String[] { username, password, fightid + "" });
			} catch (Exception e) {
				e.printStackTrace();
				moon = null;
			}
		}
		Log.info("******************************************************************************");
		

		
		new SapphireOwl(args);
		new SapphireLapis(args).start();
	}

	public SapphireOwl(String[] args) throws Exception {
		super(args);
	}
	
	
	@Override
	protected String[] getRootPackages(){
		return new String[] { "com.souchy.randd.commons.deathebi.msg", "com.souchy.randd.moonstone", "com.souchy.randd.moonstone.white", "com.souchy.randd.ebishoal.sapphire" };
	}
	
	
	/**
	 * Start Lapis engine
	 * 
	 * @author Blank
	 * @date 29 oct. 2021
	 */
	private static class SapphireLapis extends LapisCore {
		public SapphireLapis(String[] args) throws Exception {
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
			return game = new SapphireGame();
		}
		@Override
		public SapphireGame getGame() {
			return game;
		}
		@Override
		public void addIcon(LwjglApplicationConfiguration config) {
			config.addIcon("res/textures/appicon4.png", FileType.Internal); //.addIcon("G:/Assets/pack/fantasy bundle/tcgcardspack/Tex_krakken_icon.png", FileType.Absolute);
		}
	}
	
	
}
