package com.souchy.randd.ebishoal.sapphire.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.deathebi.msg.GetSalt;
import com.souchy.randd.commons.diamond.common.BoardGenerator;
import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.ebi.ammolite.Ammolite;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.RenderOptions;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.sapphire.gfx.Highlight;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireScreen;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireHud;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireHudSkin;
import com.souchy.randd.moonstone.commons.packets.c2s.GetUpdate;
import com.souchy.randd.moonstone.commons.packets.s2c.FullUpdate;
import com.souchy.randd.moonstone.white.Moonstone;

public class SapphireGame extends LapisGame {

	public static Fight fight; // FIXME replace this with Moonstone.fight or use the reference for now i guess

	public static SapphireScreen gfx;

	public static MusicPlayer music;

	public static SapphireEntitySystem renderableEntitySystem;

	@Override
	public void init() {
		SapphireGame.fight = Moonstone.fight; // = new Fight();
		renderableEntitySystem = new SapphireEntitySystem(Moonstone.fight);
		
		
		// init elements
		Elements.values();
		// models configurations (creatures, spells, statuses)
		AssetData.loadResources();
		// init creatures & spells models
		DiamondModels.instantiate("com.souchy.randd.data.s1");

		// load asssets
		//LapisResources.loadResources(SapphireOwl.data);
		LapisAssets.loadTextures(Gdx.files.internal("res/textures/"));
		LapisAssets.loadModels(Gdx.files.internal("res/models/"));
		LapisAssets.loadMusics(Gdx.files.internal("res/music/"));
		LapisAssets.loadSounds(Gdx.files.internal("res/sounds/"));
		LapisAssets.loadI18NBundles(Gdx.files.internal("res/i18n/"));

		// init music player
		music = new MusicPlayer();
		
		Highlight.init();
		
		// screen
		gfx = new SapphireScreen();
		gfx.init();
		
		// pfx
		ParticleEffectLoader.ParticleEffectLoadParameter params = new ParticleEffectLoader.ParticleEffectLoadParameter(gfx.getPfxSystem().getBatches());
		LapisAssets.loadParticleEffects(Gdx.files.internal("res/fx/"), params);

		Log.info("LapisResources : { " + String.join(", ", LapisAssets.assets.getAssetNames()) + " }");

		Log.info("data.creatures : " + DiamondModels.creatures);
		
		// after assets have been loaded, log into moonstone then ask for fight data on
		// JoinFight
		if(Moonstone.moon != null) {
			Moonstone.bus.register(this);
			Moonstone.writes(new GetSalt(Moonstone.moon.channel.attr(Moonstone.authKey).get()[0]));
		}
	}

	@Override
	public void render() {
		// render 3D
		if (screen != null) screen.render(Gdx.graphics.getDeltaTime());
		// render UI
		if(RenderOptions.renderUI && gfx.hud.isLoaded) gfx.renderView(Gdx.graphics.getDeltaTime());
	}


	@Override
	public SapphireScreen getStartScreen() {
		return gfx;
	}
	
	/**
	 * Update UI when receiving FullUpdate msg
	 */
	@Subscribe
	public void fullUpdateHandler(FullUpdate msg) {
		// player hud
		gfx.hud.reload();
//		SapphireHudSkin.play(fight.creatures.first()); //.family.get(0)); // fight.teamA.get(0));
//		SapphireHud.timeline.refresh();
//		SapphireHud.refreshTimeline();
//		gfx.hud.timeline.init();
//		gfx.startPfx(fight.creatures.first());
	}


}
