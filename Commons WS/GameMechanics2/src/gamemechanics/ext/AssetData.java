package gamemechanics.ext;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.logging.Log;

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
public class AssetData {
	
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

		public Path getIcon() {
			return Environment.fromRoot("res/textures/creatures/" + icon + ".png"); 
		}
		public Path getModel(int modelId) {
			return Environment.fromRoot("res/models/creatures/" + models[modelId] + ".g3dj");
		}
	}

	public static class SpellResource {
		public int id;
//		public String name;
//		public String description;
		public String icon;
		public String casterAnim;
		public String casterFx;
		public String targetFx;
		
		public Path getIcon() {
			return Environment.fromRoot("res/textures/spells/" + icon + ".png"); 
		}
	}
	
	public static class StatusResource {
		public int id;
//		public String name;
//		public String description;
		public String icon;
		public String patch10;
		public String centerTexture;
		
		public Path getIcon() {
			return Environment.fromRoot("res/textures/statuses/" + icon + ".png"); 
		}
	}
	

	public static Map<Integer, CreatureResource> creatures = new HashMap<>();
	public static Map<Integer, SpellResource> spells = new HashMap<>();
	public static Map<Integer, StatusResource> statuses = new HashMap<>();
	
	
	
	@SuppressWarnings("unchecked")
	public static void loadResources() {
		try {
	//		var dir = Gdx.files.internal("data/");
			Log.info("thing 1 : " + Environment.fromRoot("res/data/creatures.json"));
			
			var creaturesFile = Environment.fromRoot("res/data/creatures.json"); //new File("data/creatures.json"); //dir.child("creatures.json");
			var spellsFile = Environment.fromRoot("res/data/spells.json"); //new File("data/spells.json"); //dir.child("spells.json");
			var statusesFile = Environment.fromRoot("res/data/statuses.json"); //new File("data/statuses.json"); //dir.child("statuses.json");
	
			var creaturemaptype = new TypeToken<List<CreatureResource>>() {}.getType();
			var spellmaptype = new TypeToken<List<SpellResource>>() {}.getType();
			var statusmaptype = new TypeToken<List<StatusResource>>() {}.getType();
			
			var creatureList = (List<CreatureResource>) new Gson().fromJson(Files.readString(creaturesFile), creaturemaptype);
			var spellList = (List<SpellResource>) new Gson().fromJson(Files.readString(spellsFile), spellmaptype);
			var statusList = (List<StatusResource>) new Gson().fromJson(Files.readString(statusesFile), statusmaptype);
			
	//		var creatureList = (List<CreatureResource>) JsonConfig.gson.fromJson(creaturesFile.readString(), creaturemaptype);
	//		var spellList = (List<SpellResource>) JsonConfig.gson.fromJson(spellsFile.readString(), spellmaptype);
	//		var statusList = (List<StatusResource>) JsonConfig.gson.fromJson(statusesFile.readString(), statusmaptype);
			
			creatureList.forEach(c -> creatures.put(c.id, c));
			spellList.forEach(c -> spells.put(c.id, c));
			statusList.forEach(c -> statuses.put(c.id, c));
			
			Log.info("loaded creatures json : " + creatures);
			Log.info("loaded creature json " + creatures.get(1));
			Log.info("loaded spells json : " + spells);
			Log.info("loaded statuses json : " + statuses);
			
		} catch (Exception e) {
			Log.error("", e);
		}
	}
	


	public static String getSkinPath(String texturepath) {
		return texturepath.substring(texturepath.indexOf("textures"), texturepath.lastIndexOf(".")).replace("/", ".");
	}
	
	
}
