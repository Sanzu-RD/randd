package com.souchy.randd.mockingbird.lapismock.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.RenderOptions;
import com.souchy.randd.ebishoal.commons.lapis.gfx.shaders.LapisShader;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.commons.lapis.world.World;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.SSAOShader;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.SSAOShader.SSAOShaderProvider;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.AdvancedShader;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.AdvancedShader.AdvancedShaderProvider;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.DepthShader.DepthShaderProvider;

public class MockScreen6 extends SapphireSetupScreen {

	private FrameBuffer ssaoFBO;
	private ModelBatch ssaoModelBatch;

	private SSAOShaderProvider shadersSsao;
	private AdvancedShaderProvider shadersAdv;
	
//	private SSAOShader shaderSSAO;
//	private AdvancedShader shaderAdv;
	
	private FrameBuffer depthFBO;
	private DepthShaderProvider depthShaders;
	private ModelBatch depthModelBatch;
	public static TextureDescriptor<Texture> depthMap;
	public static TextureDescriptor<Texture> ssaoMap;
	
	public ModelInstance tsukuyo;
	
	public MockScreen6() {
		super();
		Log.info("MockScreen 6 a");
		createFBOs(1600, 900);
		
		depthShaders = new DepthShaderProvider();
		depthModelBatch = new ModelBatch(depthShaders);
		depthMap = new TextureDescriptor<Texture>();
		depthMap.minFilter = depthMap.magFilter = Texture.TextureFilter.Nearest;
		depthMap.uWrap = depthMap.vWrap = Texture.TextureWrap.Repeat; 
		
		
		shadersSsao = new SSAOShaderProvider(null); //this.getEnvironment().shadowMap.getDepthMap());
		ssaoModelBatch = new ModelBatch(shadersSsao);
		ssaoMap = new TextureDescriptor<Texture>();
		ssaoMap.minFilter = ssaoMap.magFilter = Texture.TextureFilter.Nearest;
		ssaoMap.uWrap = ssaoMap.vWrap = Texture.TextureWrap.Repeat; 
		
		
		Log.info("MockScreen 6 b");
		
		// reload shaders
		this.controller.keyCombos.put(Keys.TAB, Keys.TAB, () -> {
			shadersSsao.clear();
			shadersAdv.clear();
			depthShaders.clear();
//			shaderSSAO = shadersSsao.getShader(null);
//			shaderAdv = shadersAdv.getShader(null)
		});
	}
	
	private void createFBOs(int width, int height) {
		depthFBO = new FrameBuffer(Format.RGBA8888, width, height, true, false);
		ssaoFBO = new FrameBuffer(Format.RGBA8888, width, height, true, false);
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		createFBOs(width, height);
	}
	
	@Override
	public ModelBatch createWorldBatch() {
		shadersAdv = new AdvancedShaderProvider();
		return new ModelBatch(shadersAdv);
	}
	
	@Override
	public ModelBatch createWorldShadowBatch() {
		return super.createWorldShadowBatch();
	}

	@Override
	public SpriteBatch createSpriteBatch() {
		return super.createSpriteBatch(); //  new SpriteBatch(1000, new ShaderProgram(LapisShader.getVertexShader("postProcess"), LapisShader.getFragmentShader("postProcess")));
	}

	@Override
	public World createWorld() {
		var world = super.createWorld();
		var path = "res/models/creatures/Tsukuyo.g3dj";
		LapisAssets.loadModels(Gdx.files.internal(path));
		Model m = LapisAssets.get("res/models/creatures/Tsukuyo.g3dj");
		tsukuyo = new ModelInstance(m);
		tsukuyo.transform.setToTranslation(3, 9, 1);
		world.instances.add(tsukuyo);
		Log.info("Tsukuyo " + tsukuyo);
		return world;
	}
	
	public void renderDepth(float delta) {
		//var shader = depthShaders.get().first();
		depthFBO.begin();
			// clear the screen with a transparent background for the fbo
			clearScreen(0, 0, 0, 0);
			// world
			depthModelBatch.begin(getCamera());
				// render the cache (static terrain)
				if(RenderOptions.renderCache) depthModelBatch.render(getWorld().cache, getEnvironment()); //, shader);
				// render dynamic instances (cursor, creatures, terrain effects like glyphs and traps, highlighting effects ..)
				depthModelBatch.render(getWorld().instances, getEnvironment()); //, shader);
			depthModelBatch.end();
		depthFBO.end();
	}
	
	
	public void renderSSAO(float delta) {
		ssaoFBO.begin();
			// clear the screen with a transparent background for the fbo
			clearScreen(0, 0, 0, 0);
			// world
			ssaoModelBatch.begin(getCamera());
				// render the cache (static terrain)
				if(RenderOptions.renderCache) ssaoModelBatch.render(getWorld().cache, getEnvironment());
				// render dynamic instances (cursor, creatures, terrain effects like glyphs and traps, highlighting effects ..)
				ssaoModelBatch.render(getWorld().instances, getEnvironment());
			ssaoModelBatch.end();
		ssaoFBO.end();
	}
	
	
	
	/**
	 * this is equivalent to LapisScreen.render
	 */
	@Override
	public void render(float delta) {
		// update input controller
		if(getInputProcessor() != null && getInputProcessor() instanceof CameraInputController) 
			((CameraInputController) getInputProcessor()).update();
		// act
		act(delta);
		bigblockrender(delta);
	}
	
	
	/**
	 * This is equivalent to LapisScreenRenderer.render
	 */
	private void bigblockrender(float delta) {

		// cull shadows and render shadow map into its own fbo
		if(RenderOptions.cullback) Gdx.gl20.glCullFace(GL20.GL_FRONT);
		if(RenderOptions.activateShadows) renderShadowsContainer();
		if(RenderOptions.cullback) Gdx.gl.glCullFace(GL20.GL_BACK);
		
		
		// render world and pfx in an FBO for later post-process
		getFBO().begin();
		{
			// clear the screen with a transparent background for the fbo
			clearScreen(0, 0, 0, 0);
			
			cleanSpriteBatch.begin();
			{
				if(RenderOptions.renderBackground) 
					drawBackground(cleanSpriteBatch);
			}
			cleanSpriteBatch.end();
			// world
			renderWorldContainer();
			// particle effects
			renderParticleEffectsContainer();
			// particle effects effekseer... either i get fbo or transparency.... even the fire has straight lines at the bottom of particles and we dont have distortion in the shader
			renderEffekseer(delta);
		}
		getFBO().end();
		
		// obtain the image generated by the fbo
		// fbo uses lower left, TextureRegion uses upper-left
		Texture tex = null;
		tex = (RenderOptions.onlyShadowMap && getShadowLight() != null) ? getShadowLight().getFrameBuffer().getColorBufferTexture() : getFBO().getColorBufferTexture();
		//tex = depthFBO.getColorBufferTexture();
		//tex = ssaoFBO.getColorBufferTexture();
		var fboRegion = new TextureRegion(tex);
		fboRegion.flip(false, true);
		
		//Log.info("world instances " + getWorld().instances.size());
		//float scl = 10f;
		//tsukuyo.transform.setToTranslation(0, 0, 0).scale(scl, scl, scl);
		
		RenderOptions.activatePP = false;
		// sprite batch with post processing or not 
		var spriteBatch = RenderOptions.activatePP ? getSpriteBatch() : cleanSpriteBatch;
		
		// render the resulting texture through the sprite batch of the post-processor
		spriteBatch.begin();
		{
			// clear the screen with the getBackgroundColor()
			clearScreen();
			// start with the background
//			if(RenderOptions.renderBackground) drawBackground(spriteBatch);
			// draw the world texture
			spriteBatch.draw(fboRegion, 0, 0);
			//else getSpriteBatch().draw(fboRegion, getViewport().getScreenX(), getViewport().getScreenY());
		}
		spriteBatch.end();

		// render lines
		if(RenderOptions.renderLines/* getLining() != null */) getLining().renderLines();
		
		// render UI
		if(RenderOptions.renderUI) renderView(delta);
	}
	

	
}
