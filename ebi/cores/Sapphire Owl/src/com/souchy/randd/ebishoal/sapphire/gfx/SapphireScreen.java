package com.souchy.randd.ebishoal.sapphire.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.statusevents.damage.DamageEvent;
import com.souchy.randd.commons.diamond.statusevents.damage.DamageEvent.OnDamageHandler;
import com.souchy.randd.commons.diamond.statusevents.resource.ResourceGainLossEvent;
import com.souchy.randd.commons.diamond.statusevents.resource.ResourceGainLossEvent.OnResourceGainLossHandler;
import com.souchy.randd.ebi.ammolite.Ammolite;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisScreen;
import com.souchy.randd.ebishoal.commons.lapis.lining.LineDrawing;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.commons.lapis.world.World;
import com.souchy.randd.ebishoal.sapphire.controls.SapphireController;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.main.SapphireWorld;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireHud;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireHudSkin;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireLmlParser;


public class SapphireScreen extends LapisScreen {

	// light cycle
	private float time = 0; // current time
	private float period = 30; // period time in seconds
	private double radius = 2; // circle radius

	public SapphireHud hud;
	private SapphireController controller;

//	private EffekseerEffectCore effekseerEffectCore;

	private long timeStart = 0;

//	public static ParticleEffekseer effect;

	public SapphireScreen() {

	}
	
	@Override
	public void init() {
		super.init();
	}

	@Override
	protected void act(float delta) {
		// update title
		Gdx.graphics.setTitle("FPS : " + Gdx.graphics.getFramesPerSecond());
		// update lights
		time += delta;
		if(time >= period) time = 0;
		double radian = ((period - time) / period) * 2 * Math.PI;
		if(getShadowLight() != null) {
			getShadowLight().direction.x = (float) (Math.sin(radian) / radius);
			getShadowLight().direction.y = (float) (Math.cos(radian) / radius);
			//getShadowLight().direction.z = -0.5f;
		}
		// controller
		if(controller != null) controller.act(delta);
		
		// camera 
		getCamera().update();
		
		// update playing creature cursor (highlights which creature is currently playing)
		if(SapphireGame.playing != null) 
			SapphireWorld.world.translatePlayingCursor((float) SapphireGame.playing.pos.x, (float) SapphireGame.playing.pos.y, 0);
		
		// update all engine's systems 	 //and entities
		SapphireGame.fight.update(delta);
		
		// load assets and (one-shot proc) if finished
		if(LapisAssets.update()) {
			((SapphireHudSkin) VisUI.getSkin()).finishLoading(); 
			hud.reload();
			SapphireWorld.world.finishLoading();
		}
//		if(System.currentTimeMillis() - timeStart > 5000) {
//			startPfx();
//		}
	}

//	@Override
//	public LineDrawing createLining(Camera cam, BoundingBox worldBB) {
////		return super.createLining(cam, worldBB);
//		return null;
//	}

	@Override
	public LapisHud createUI() {
		SapphireGame.profiler.poll("SapphireScreen go createHud");
		SapphireLmlParser.init();
		hud = new SapphireHud();
		hud.reload();
		//Log.info("life : " + view.life.getColor() + ", " + view.life.getStyle().fontColor);
		return hud;
	}

	@Override
	public InputProcessor createInputProcessor() {
		var multi = new InputMultiplexer();
		if(getView() != null) multi.addProcessor(getView().getStage());
		multi.addProcessor(controller = new SapphireController(getCamera()));
		return multi;
	}

	@Override
	public Camera createCam(boolean useOrtho) {
//		float viewportSize = 17; // acts as a zoom (lower number is closer zoom)
//		var cam = new OrthographicCamera(viewportSize * 16 / 9, viewportSize);
		return new OrthographicCamera();
	}

	@Override
	public Viewport createViewport(Camera cam) {
		// how many meters high is the screen (ex 1920/1080 = 28.4/16)
		float viewportSize = 16;  // acts as a zoom (lower number is closer zoom)
		var viewport = new ExtendViewport(viewportSize * 16/9, viewportSize, cam);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		return viewport;
	}

	@Override
	public World createWorld() {
		return new SapphireWorld();
	}

	public void topView() {
//		var viewportSize = 17f; // acts as a zoom (lower number is closer zoom)
//		var factor = 0.6f;
		var center = getWorldCenter(); //getWorldBB();
//		getCamera().viewportWidth = viewportSize * 16 / 9;
//		getCamera().viewportHeight = viewportSize ;
		getCamera().direction.set(0, 0, -1f);
		getCamera().up.set(1, 1, -1f);
		getCamera().position.set(center.x, center.y, center.z); //bb.max.x * factor, bb.max.y * (1 - factor), 0); //bb.max.z * factor);
		getCamera().near = -30f;
		getCamera().far = 120f;
		getCamera().update();
	}

	@Override
	public void resetCamera() {
//		super.resetCamera();
		topView();
		getCamera().rotate(45, getCamera().up.y, -getCamera().up.x, 0);
	}


	@Override
	public void renderWorld() {
		// render World.cache and Worldinstances
		super.renderWorld();
		// render dynamic instances (creatures, terrain effects like glyphs and traps, highlighting effects ..)
		SapphireGame.renderableEntitySystem.foreach(e -> {
			var model = e.get(ModelInstance.class);
			if (model != null) {
				//e.get() // TODO invisibility
				getModelBatch().render(model, getEnvironment());
			}
		});
	}

	@Override
	public void renderShadows() {
		super.renderShadows();
		SapphireGame.renderableEntitySystem.foreach(e -> {
			var model = e.get(ModelInstance.class);
			if (model != null)
				getModelBatch().render(model, getEnvironment());
		});
	}

}
