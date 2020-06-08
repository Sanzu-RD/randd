package com.souchy.randd.ebishoal.sapphire.confs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * configurations for creatures, spells, status (i18n names/descriptions + icons/textures/models/fx)
 * 
 * i18n/creatures/...
 * 
 * 
 * 
 * @author Blank
 * @date 1 mai 2020
 */
public class AssetConfs {
	
	// loads resource/property text files from i18n
	// ex : /fr/status.resources :
	//				1 : { name: "", description: "", icon: "", patch10:"", center:"" }

	public static class CreatureResource {
		public int id;
//		public String name; // name just for reference, not actually used. the 
//		public String description;
//		public String model;
//		public String skin;
		public String[] models;
		public String icon;
	}

	public static class SpellResource {
		public int id;
//		public String name;
//		public String description;
		public String icon;
		public String casterAnim;
		public String casterFx;
		public String targetFx;
	}
	
	public static class StatusResource {
		public int id;
//		public String name;
//		public String description;
		public String icon;
		public String patch10;
		public String centerTexture;
	}
	

	public static Map<Integer, CreatureResource> creatures = new HashMap<>();
	public static Map<Integer, SpellResource> spells = new HashMap<>();
	public static Map<Integer, StatusResource> statuses = new HashMap<>();
	
	
	
	@SuppressWarnings("unchecked")
	public static void loadResources() {
		var dir = Gdx.files.internal("data/");
		var creaturesFile = dir.child("creatures.json");
		var spellsFile = dir.child("spells.json");
		var statusesFile = dir.child("statuses.json");

		var creaturemaptype = new TypeToken<List<CreatureResource>>() {}.getType();
		var spellmaptype = new TypeToken<List<SpellResource>>() {}.getType();
		var statusmaptype = new TypeToken<List<StatusResource>>() {}.getType();
		
		
		var creatureList = (List<CreatureResource>) new Gson().fromJson(creaturesFile.readString(), creaturemaptype);
		var spellList = (List<SpellResource>) new Gson().fromJson(spellsFile.readString(), spellmaptype);
		var statusList = (List<StatusResource>) new Gson().fromJson(statusesFile.readString(), statusmaptype);
		
//		var creatureList = (List<CreatureResource>) JsonConfig.gson.fromJson(creaturesFile.readString(), creaturemaptype);
//		var spellList = (List<SpellResource>) JsonConfig.gson.fromJson(spellsFile.readString(), spellmaptype);
//		var statusList = (List<StatusResource>) JsonConfig.gson.fromJson(statusesFile.readString(), statusmaptype);
		
		creatureList.forEach(c -> creatures.put(c.id, c));
		spellList.forEach(c -> spells.put(c.id, c));
		statusList.forEach(c -> statuses.put(c.id, c));
		
		//Log.info("loaded creatures json : " + creatures);
		//Log.info("loaded creature json " + creatures.get(1));
		//Log.info("loaded spells json : " + spells);
		//Log.info("loaded statuses json : " + statuses);
	}
	
	
}
