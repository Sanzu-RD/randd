package com.souchy.randd.ebishoal.sapphire.main;

import java.util.function.Consumer;

import org.bson.types.ObjectId;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.utils.I18NBundle;
import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.deathebi.msg.GetSalt;
import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.statusevents.Handler.Reactor;
import com.souchy.randd.commons.diamond.statusevents.other.TurnEndEvent;
import com.souchy.randd.commons.diamond.statusevents.other.TurnEndEvent.OnTurnEndHandler;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent.OnTurnStartHandler;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.RenderOptions;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.sapphire.controls.Commands;
import com.souchy.randd.ebishoal.sapphire.gfx.Highlight;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireScreen;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireHudSkin;
import com.souchy.randd.ebishoal.sapphire.ux.components.PlayBar;
import com.souchy.randd.moonstone.commons.packets.s2c.FullUpdate;
import com.souchy.randd.moonstone.white.Moonstone;

public class SapphireGame extends LapisGame implements Reactor, OnTurnEndHandler, OnTurnStartHandler {

	public static Fight fight; // FIXME replace this with Moonstone.fight or use the reference for now i guess

	public static SapphireScreen gfx;
	
	public static SapphireAssets assets;
	
	public static MusicPlayer music;

	public static SapphireEntitySystem renderableEntitySystem;

	public static Creature playing;
	
	
	@Override
	public void init() {
		SapphireGame.fight = Moonstone.fight; // = new Fight();
		SapphireGame.fight.statusbus.register(this);
//		SapphireGame.fight.pipe.setPostRunnable(Gdx.app::postRunnable);
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
		LapisAssets.loadFonts(Gdx.files.internal("res/fonts/"));
		assets = new SapphireAssets();
		
		// init commands
		Commands.init();

		// init music player
		music = new MusicPlayer();
		
		Highlight.init();
		
		// screen
		gfx = new SapphireScreen();
		gfx.init();
		
		// pfx
//		ParticleEffectLoader.ParticleEffectLoadParameter params = new ParticleEffectLoader.ParticleEffectLoadParameter(gfx.getPfxSystem().getBatches());
//		LapisAssets.loadParticleEffects(Gdx.files.internal("res/fx/"), params);

		Log.info("LapisResources : { " + String.join(", ", LapisAssets.assets.getAssetNames()) + " }");

		Log.info("data.creatures : " + DiamondModels.creatures);
		
		
		// after assets have been loaded, log into moonstone then ask for fight data on
		// JoinFight
		if(Moonstone.moon != null) {
			Moonstone.bus.register(this);
			Moonstone.setPostRunnable(Gdx.app::postRunnable);
			Moonstone.writes(new GetSalt(Moonstone.moon.channel.attr(Moonstone.authKey).get()[0]));
		}
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
		if(gfx.hud.getStage() != null) gfx.hud.reload();
//		SapphireHudSkin.play(fight.creatures.first()); //.family.get(0)); // fight.teamA.get(0));
//		SapphireHud.timeline.refresh();
//		SapphireHud.refreshTimeline();
//		gfx.hud.timeline.init();
//		gfx.startPfx(fight.creatures.first());
	}
	

	@Override
	public void onTurnStart(TurnStartEvent e) {
////		Log.format("SapphireGame event fight %s turn %s start %s", e.fight.id, e.turn, e.index);
		playing = SapphireGame.getPlayingCreature();
//		if(playing == null) return;
//		//gfx.hud.reload();
//		if(SapphireGame.gfx.hud != null) {
//			Log.info("SapphireGame turn start " + playing.id);
//			SapphireHudSkin.play(playing);
//			//SapphireGame.gfx.hud.playbar.clear();
//			//SapphireGame.gfx.hud.playbar = new PlayBar();
//			SapphireGame.gfx.hud.playbar.refresh();
//		}
//		if(SapphireWorld.world != null && SapphireWorld.world.playingcursor != null) {
//			// 3d cursor
////			SapphireWorld.world.playingcursor.transform.setTranslation((float) playing.pos.x + 0.5f, (float) playing.pos.y + 0.5f, 1f);
//		}
	}
	
	/**
	 * Event handler
	 */
	@Override
	public void onTurnEnd(TurnEndEvent e) {
//		Log.format("SapphireGame event fight %s turn %s end %s", e.fight.id, e.turn, e.index);
		//gfx.hud.reload();
	}

	/**
	 * Get the next creature to play owned by the current user
	 */
	public static Creature getPlayingCreature() {
		// find next playing creature 
		var val = fight.timeline.findNext(cid -> 
			// filter for ownership
			true //SapphireGame.fight.creatures.get(cid).get(ObjectId.class).equals(Moonstone.user._id)
		);
		if(val == null) return null;
		Creature creature = SapphireGame.fight.creatures.get(val);
		return creature;
	}



}
