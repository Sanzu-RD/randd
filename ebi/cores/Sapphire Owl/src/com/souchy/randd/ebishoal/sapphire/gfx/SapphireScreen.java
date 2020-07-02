package com.souchy.randd.ebishoal.sapphire.gfx;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisScreen;
import com.souchy.randd.ebishoal.commons.lapis.lining.LineDrawing;
import com.souchy.randd.ebishoal.commons.lapis.world.World;
import com.souchy.randd.ebishoal.sapphire.controls.SapphireController;
import com.souchy.randd.ebishoal.sapphire.main.SapphireEntitySystem;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.main.SapphireWorld;

import Effekseer.swig.EffekseerBackendCore;
import Effekseer.swig.EffekseerEffectCore;
import Effekseer.swig.EffekseerManagerCore;
import Effekseer.swig.EffekseerTextureType;
import particles.ParticleEffekseer;

public class SapphireScreen extends LapisScreen {
	
	// light cycle
	private float time = 0; // current time
	private float period = 30; // period time in seconds
	private double radius = 2; // circle radius
	
	public SapphireHud hud;
	private SapphireController controller;

//	private EffekseerEffectCore effekseerEffectCore;
	
	private long timeStart = 0;
	
	public static ParticleEffekseer effect;
	
	public SapphireScreen() {
//		activateShadows = false;
		startPfx();
	}
	private void startPfx() {
		timeStart = System.currentTimeMillis();
		
//		String effectPath = "fx/Sample/02_Tktk03/ToonWater.efkefc";
		String effectPath = "fx/Sample/01_Pierre02/Sword_Ember.efkefc";
//		effekseerEffectCore = loadEffect(effectPath, 50.0f);
//		if(effekseerEffectCore == null) {
//			System.out.print("Failed to load.");
//			System.exit(0);
//		}
//		int efkhandle = getEffekseer().Play(effekseerEffectCore);
//		getEffekseer().SetEffectPosition(efkhandle, Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f, 3.0f);

        effect = new ParticleEffekseer(getEffekseer());
        effect.setMagnification(0.3f);
        try {
        	effect.load("fx/Sample/01_Pierre02/Sword_Ember.efkefc", true);
//        	effect.load("fx/Sample/02_Tktk03/ToonWater.efkefc", true);
        	effect.setLocation(9.5f, 1.5f, -9.5f);
        	effect.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
		if(controller != null) controller.act(delta);
		getCamera().update();
		
		// update all systems and entities
		SapphireGame.fight.update(delta);
		
		if(System.currentTimeMillis() - timeStart > 5000) {
//			startPfx();
		}
	}
	
	@Override
	public LineDrawing createLining(Camera cam, BoundingBox worldBB) {
//		return super.createLining(cam, worldBB);
		return null; 
	}
	
	@Override
	public LapisHud createUI() {
		hud = new SapphireHud();
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
		super.renderWorld();
		// render dynamic instances (cursor, creatures, terrain effects like glyphs and traps, highlighting effects ..)

		synchronized(SapphireEntitySystem.family){
			SapphireEntitySystem.family.forEach(e -> {
				var model = e.get(ModelInstance.class);
				if (model != null)
					getModelBatch().render(model);
			});
		}
		
	}

	

//	@Override
//	public void render(float delta) {
//		// cull and render shadow map into its own fbo
//		if(cullback) Gdx.gl20.glCullFace(GL20.GL_FRONT);
//		if(activateShadows) renderShadowsContainer();
//		if(cullback) Gdx.gl.glCullFace(GL20.GL_BACK);
//		
//		// render world and pfx in an FBO for later post-process
//		getFBO().begin();
//		{
//			// clear the screen with a transparent background for the fbo
//			clearScreen(0, 0, 0, 0);
//			// world
//			renderWorldContainer();
//			// particle effects
//			renderParticleEffectsContainer();
//			// particle effects effekseer
//			renderEffekseer();
//		}
//		getFBO().end();
//
//		// obtain the image generated by the fbo
//		// fbo uses lower left, TextureRegion uses upper-left
//		Texture tex = (onlyShadowMap && getShadowLight() != null) ? getShadowLight().getFrameBuffer().getColorBufferTexture() : getFBO().getColorBufferTexture();
//		var fboRegion = new TextureRegion(tex);
//		fboRegion.flip(false, true);
//		
//		// sprite batch with post processing or not 
//		var spriteBatch = activatePP ? getSpriteBatch() : cleanSpriteBatch;
//		
//		// render the resulting texture through the sprite batch of the post-processor
//		spriteBatch.begin();
//		{
//			// clear the screen with the getBackgroundColor()
//			clearScreen();
//			// start with the background
//			if(renderBackground) drawBackground(spriteBatch);
//			// draw the world texture
//			spriteBatch.draw(fboRegion, 0, 0);
//			//else getSpriteBatch().draw(fboRegion, getViewport().getScreenX(), getViewport().getScreenY());
//		}
//		spriteBatch.end();
//
//		// render lines
//		if(getLining() != null) getLining().renderLines();
//		
//		// render UI
//		if(renderUI) renderView(delta);
//	}
	

}
