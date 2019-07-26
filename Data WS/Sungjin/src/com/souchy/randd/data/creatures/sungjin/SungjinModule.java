package com.souchy.randd.data.creatures.sungjin;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.github.czyzby.lml.parser.LmlData;
import com.google.common.eventbus.Subscribe;
import com.souchy.randd.modules.api.EntryPoint;
import com.souchy.randd.modules.api.Module;
import com.souchy.randd.modules.api.ModuleClassLoader;
import com.souchy.randd.modules.api.ModuleInformation;
import com.souchy.randd.modules.node.*;

import data.modules.AzurEntryPoint;
import data.modules.AzurModule;
import data.new1.CreatureModel;

/**
 * Reference to 'Solo Leveling' manwha
 * 
 * @author Blank
 *
 */
public class SungjinModule extends AzurModule {



	/*
	public class CreateCreatureEvent {
		public final int id;
		public CreatureData data = null;
		
		public CreateCreatureEvent(int id) {
			this.id = id;
		}
	}*/
	/**
	 * Call this on every module after instantiation
	 * 
	 * every module creates its data
	 * sapphire caches it in a map <id, model>
 	 * sappphire resuses the models at will
 	 * sapphire can load a model's assets via model.loadResources or something
 	 * or actually make it load automatically when u create an instance for the first time
 	 * 
 	 * options to load resources :
 	 * 1 - manually :
 	 * 		model.loadResources(assetManager);
 	 *   	Creature creature = CreatureInstance.create(model)
 	 * 2 -  automatic with a bool to ask to load
 	 * 		Creature creature = CreatureInstance.create(model, true/false)
 	 * 3 - automatic with a different instantiator for deahtshadows and ebi :
 	 *   	Creature creature = CreatureInstance.create(model)
 	 * 		Creature creature = EbiCreatureInstance.create(model)
 	 * 
 	 * jpense le 1er est mieux pour pouvoir charger dans l'assetmanager global de sapphire
	 * 
	 * @author Blank
	 *
	 */
	public class InitCreatureModuleEvent {
		public CreatureModel model = null;
	}
	
	@Subscribe
	public void loadResources(LmlData data) {
		
		// CreateCreatureEvent
		// - create id = x;
		// - CreatureData data = null;
		// -> module checks if it's the right id, then create the creature and put it in the event
		// then sapphire takes the instance from the event object
		// 
		
		
		// TODO need to separate skin from the specific creature object or smth
		// so that we can creature multiple instances of the same creature without having multiple instances of skins 
		// spells should be specific to each instance since you can modify/gain/lose spells while in game
		// ---- CreatureData will be like a Model ----
		// ---- Creature     will be an  instance ----
		var model = new SungjinModel();
		
		var skin = model.skin; //new Skin();
		var i18n = model.i18n; //I18NBundle.createBundle(file("i18n/bundle"));
		
		data.addI18nBundle(info.getName(), i18n);
		data.addSkin(info.getName(), skin);
		
		AssetManager assets = new AssetManager();
		ParticleSystem particleSystem = null;
		
		// Load pfx
		assets.load("gdx/g3d/anims_fx_etc/laser1.pfx", ParticleEffect.class);
		assets.finishLoading();
		
		// Get the pfx model
		ParticleEffect laserFXo = assets.get("gdx/g3d/anims_fx_etc/laser1.pfx");
		
		// spell . on cast (pfxsystem)
		{
			// Create an instance of the pfx
			var laserFX = laserFXo.copy();
			laserFX.translate(new Vector3(-2, -2, 2));
			laserFX.rotate(new Vector3(0, 1, 0), 90);
			laserFX.init();
			laserFX.start(); // optional: particle will begin playing immediately
			
			// Add pfx to pfx system
			particleSystem.add(laserFX);
		}
		

		skin.getAll(TextureRegionDrawable.class).forEach(d -> {
			d.value.getRegion().getTexture().setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		});
	}

	
}
