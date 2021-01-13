package com.souchy.randd.ebishoal.commons.lapis.gfx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.shadows.LapisDSL;
import com.souchy.randd.ebishoal.commons.lapis.lining.LineDrawing;
import com.souchy.randd.ebishoal.commons.lapis.world.World;

import particles.EffekseerManager;

@SuppressWarnings("deprecation")
interface LapisScreenRenderer extends Screen {

	public World getWorld();
	/** The bounding box of the world to position things */
	//public BoundingBox getWorldBB();
	/** The center of the world to position things like the shadow map */
	public Vector3 getWorldCenter();
	

	public LineDrawing getLining();
	public ModelBatch getModelBatch();
	public ModelBatch getShadowBatch();
	public ModelBatch getPfxBatch();
	public ParticleSystem getPfxSystem();
	public FrameBuffer getFBO();
	public SpriteBatch getSpriteBatch();
	public AbstractLmlView getView();
	
	public Camera getCamera();
	public Viewport getViewport();
	public Texture getBackground();
	public InputProcessor getInputProcessor();
	public Environment getEnvironment();
	public LapisDSL getShadowLight();
	public EffekseerManager getEffekseer();
	
	
	public default void clearScreen(float r, float g, float b, float a) {
		//Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(r, g, b, a);
		Gdx.gl.glClear(
				GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT| (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
	}
	
	/**
	 * Clear the screen with a background color
	 */
	public default void clearScreen() {
		Color color = getBackgroundColor();
		clearScreen(color.r, color.g, color.b, color.a);
	}
	
	public static SpriteBatch cleanSpriteBatch = new SpriteBatch();
	
	@Override
	public default void render(float delta) {
		// cull shadows and render shadow map into its own fbo
		if(RenderOptions.cullback) Gdx.gl20.glCullFace(GL20.GL_FRONT);
		if(RenderOptions.activateShadows) renderShadowsContainer();
		if(RenderOptions.cullback) Gdx.gl.glCullFace(GL20.GL_BACK);
		
		// render world and pfx in an FBO for later post-process
		getFBO().begin();
		{
			// clear the screen with a transparent background for the fbo
			clearScreen(0, 0, 0, 0);
			// world
			renderWorldContainer();
			// particle effects
			renderParticleEffectsContainer();
			// particle effects effekseer... either i get fbo or transparency.... even the fire has straight lines at the bottom of particles and we dont have distortion in the shader
			renderEffekseer();
		}
		getFBO().end();

		// obtain the image generated by the fbo
		// fbo uses lower left, TextureRegion uses upper-left
		Texture tex = (RenderOptions.onlyShadowMap && getShadowLight() != null) ? getShadowLight().getFrameBuffer().getColorBufferTexture() : getFBO().getColorBufferTexture();
		var fboRegion = new TextureRegion(tex);
		fboRegion.flip(false, true);
		
		// sprite batch with post processing or not 
		var spriteBatch = RenderOptions.activatePP ? getSpriteBatch() : cleanSpriteBatch;
		
		// render the resulting texture through the sprite batch of the post-processor
		spriteBatch.begin();
		{
			// clear the screen with the getBackgroundColor()
			clearScreen();
			// start with the background
			if(RenderOptions.renderBackground) drawBackground(spriteBatch);
			// draw the world texture
			spriteBatch.draw(fboRegion, 0, 0);
			//else getSpriteBatch().draw(fboRegion, getViewport().getScreenX(), getViewport().getScreenY());
		}
		spriteBatch.end();

		// render lines
		if(getLining() != null) getLining().renderLines();
		
		// render UI
		if(RenderOptions.renderUI) renderView(delta);
	}
	
	public default void renderEffekseer() {
//		getEffekseer().SetViewProjectionMatrixWithSimpleWindow(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		getEffekseer().Update(Gdx.graphics.getDeltaTime() / (1.0f / 60.0f));
//		getEffekseer().DrawBack();
//		getEffekseer().DrawFront();
		
		getEffekseer().draw(Gdx.graphics.getDeltaTime());
	}
	
	/**
	 * 
	 */
	public default void renderView(float delta) {
		// render UI
		if(getView() != null) {
			try {
				getView().render(delta);
			} catch (Exception e) {
				Log.error("view rendering error :\n", e);
				getView().getStage().getBatch().end();
			}
		}
	}
	
	/*
	 * Emboxes the shadow map rendering
	 */
	public default void renderShadowsContainer() {
		if(getShadowLight() == null) return;
		getShadowLight().begin(getWorldCenter(), getCamera().direction);
		{
			getShadowBatch().begin(getShadowLight().getCamera());
			{
				renderShadows();
			}
			getShadowBatch().end();
		}
		getShadowLight().end();
	}

	/*
	 * Render the shadowmap for the world
	 */
	public default void renderShadows() {
		// render shadows on the world
		//if(greedyOrInstances) 
		//	shadowBatch.render(inst, getEnvironment()); // world.cache, shadowEnv);
		//else 
			// TODO create shadows *only for characters and z>0 blocks* then project *only onto blocks z=0*
			if(RenderOptions.renderCache) getShadowBatch().render(getWorld().cache, getEnvironment()); 
//			getShadowBatch().render(getWorld().instances, getEnvironment()); 
			
		// dont render shadows on highlight effects like traps, glyphs, etc
		// shadowBatch.render(cellHighlighterInst);
		//if(creatures) shadowBatch.render(characters, getEnvironment());
	}
	
	/*
	 * Emboxes the world rendering
	 */
	public default void renderWorldContainer() {
		getModelBatch().begin(getCamera());
		{
			renderWorld();
		}
		getModelBatch().end();
	}
	
	/**
	 * Render the world models, characters, particle effects, etc
	 */
	public default void renderWorld() {
		// render world
//		if(greedyOrInstances) 
//			modelBatch.render(inst, getEnvironment()); // world.cache, env);
			//g1.meshes.forEach(m -> m.render(modelBatch.getShaderProvider().getShader(null), GL20.GL_TRIANGLES));
//		else 
			// render the cache (static terrain)
			if(RenderOptions.renderCache) getModelBatch().render(getWorld().cache, getEnvironment());
			// render dynamic instances (cursor, creatures, terrain effects like glyphs and traps, highlighting effects ..)
			getModelBatch().render(getWorld().instances, getEnvironment());

		// render highlight effects like traps, glyphs, etc (might or might not render with the environment var)
//		modelBatch.render(cellHighlighterInst); // , new LShader());
		// render characters
//		if(creatures) modelBatch.render(characters, env);
	}
	/**
	 * Emboxes the particle effects rendering
	 */
	public default void renderParticleEffectsContainer() {
		if(getPfxBatch() == null) return;
		getPfxBatch().begin(getCamera());
		{
			renderParticleEffects();
		}
		getPfxBatch().end();
	}

	/**
	 * Render particle effects
	 */
	public default void renderParticleEffects() {
		if(getPfxSystem() == null) return;
		getPfxSystem().update(); // technically not necessary for rendering
		getPfxSystem().begin();
		getPfxSystem().draw();
		getPfxSystem().end();
		getPfxBatch().render(getPfxSystem());
	}
	
	
	/**
	 * Get the color to clear the screen with
	 */
	public default Color getBackgroundColor() {
		return Color.BLACK;
	}
	
	/**
	 * Draw a background texture if wanted.
	 */
	public void drawBackground(SpriteBatch batch);

	
}
