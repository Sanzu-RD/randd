package com.souchy.randd.ebishoal.commons.lapis.screen;

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
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.souchy.randd.ebishoal.commons.lapis.drawing.LineDrawing;
import com.souchy.randd.ebishoal.commons.lapis.world.World;

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
	public FrameBuffer getFBO();
	public SpriteBatch getSpriteBatch();
	public AbstractLmlView getView();
	
	public Camera getCamera();
	public Viewport getViewport();
	public Texture getBackground();
	public InputProcessor getInputProcessor();
	public Environment getEnvironment();
	public DirectionalShadowLight getShadowLight();
	
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
	
	
	@Override
	public default void render(float delta) {
		
		// render shadow map into its own fbo
		renderShadowsContainer();
		
		// render world and pfx in a fbo
		getFBO().begin();
		{
			// clear the screen with a transparent background for the fbo
			clearScreen(0, 0, 0, 0);
			renderWorldContainer();
			renderParticleEffectsContainer();
		}
		getFBO().end();

		
		// obtain the image generated by the fbo
		// fbo uses lower left, TextureRegion uses upper-left
		Texture tex = getFBO().getColorBufferTexture();
		var fboRegion = new TextureRegion(tex);
		fboRegion.flip(false, true);
		
		
		// render the resulting texture through the sprite batch of the post-processor
		getSpriteBatch().begin();
		{
			// clear the screen with the getBackgroundColor()
			clearScreen();
			// start with the background
			drawBackground(getSpriteBatch());
			// draw the world texture
			getSpriteBatch().draw(fboRegion, 0, 0);
			//else getSpriteBatch().draw(fboRegion, getViewport().getScreenX(), getViewport().getScreenY());
		}
		getSpriteBatch().end();

		// render lines
		if(getLining() != null) getLining().renderLines();
		
		renderView(delta);
	}
	
	/**
	 * 
	 */
	public default void renderView(float delta) {
		// render UI
		if(getView() != null) getView().render(delta);
	}
	
	/*
	 * Emboxes the shadow map rendering
	 */
	public default void renderShadowsContainer() {
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
			getShadowBatch().render(getWorld().cache, getEnvironment());
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
			getModelBatch().render(getWorld().cache, getEnvironment());
		// render highlight effects like traps, glyphs, etc (might or might not render with the environment var)
//		modelBatch.render(cellHighlighterInst); // , new LShader());
		// render characters
//		if(creatures) modelBatch.render(characters, env);
	}
	/**
	 * Emboxes the particle effects rendering
	 */
	public default void renderParticleEffectsContainer() {
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
