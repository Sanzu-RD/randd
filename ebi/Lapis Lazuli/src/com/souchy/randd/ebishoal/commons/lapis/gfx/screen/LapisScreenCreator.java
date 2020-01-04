package com.souchy.randd.ebishoal.commons.lapis.gfx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.ModelInstanceParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.souchy.randd.ebishoal.commons.lapis.gfx.shadows.LapisDSL;
import com.souchy.randd.ebishoal.commons.lapis.lining.LineDrawing;
import com.souchy.randd.ebishoal.commons.lapis.world.World;


@SuppressWarnings({ "deprecation", "unused" })
interface LapisScreenCreator {

	/**
	 * Create LML UI
	 */
	public LapisHud createUI();
	
	/**
	 * Create line drawing utility
	 */
	public default LineDrawing createLining(Camera cam, BoundingBox worldBB) {
		var lining = new LineDrawing(cam);
		lining.createGrid(1, (int) worldBB.getWidth(), (int) worldBB.getHeight(), true);
		return lining;
	}
	
	public default Texture createBackground() {
		return new Texture(Gdx.files.absolute("G:/Assets/pack/fantasy bundle/tcgcardspack/Tex_krakken.PNG"));
	}
	
	/**
	 * Create either a perspective or orthographic camera
	 */
	public default Camera createCam(boolean useOrtho) {
		Camera cam;
		if(useOrtho) {
			// cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(),
			// Gdx.graphics.getHeight());
			float viewportSize = 30;
			cam = new OrthographicCamera(viewportSize * 16 / 9, viewportSize);
			// cam.position.set(worldCenter.x, worldCenter.y*0.6f, worldCenter.x*1.5f);
			// cam.lookAt(worldCenter.x, worldCenter.y*0.9f, 0);
			cam.direction.set(-1, 1, -1f);
			cam.up.set(-1, 1, 1f);
			cam.position.set(14, 14, 0);
			cam.near = -30f;
			cam.far = 300f;
			cam.update();
		} else {
			cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			// cam.position.set(worldCenter.x, worldCenter.y*0.6f, worldCenter.x*1.8f);
			// cam.lookAt(worldCenter.x, worldCenter.y*0.9f, 0);
			// cam.lookAt(14, 14, 0);
			cam.direction.set(-1, 1, -1f);
			cam.up.set(-1, 1, 1f);
			cam.position.set(25, -5, 15);
			cam.near = 1f;
			cam.far = 300f;
			cam.update();
		}
		return cam;
	}

	public default Viewport createViewport(Camera cam) {
		return null; // new ExtendViewport(16, 9, cam);
	}
	
	/**
	 * Create an environment with an ambiant color light and a directional light
	 */
	public default Environment createEnvironment() {
		var env = new Environment();
		var ambiant = 0.6f;
		//var dir = 0.6f;
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, ambiant, ambiant, ambiant, 1f));
		//env.add(new DirectionalLight().set(dir, dir, dir, -1f, -0.8f, -0.2f));
		return env;
	}
	
	public default LapisDSL createShadowLight(Environment env) {
		var shadowMapWidth = Gdx.graphics.getWidth() * 2; //1024 * 2;// * 2 * 2;
		var shadowMapHeight = Gdx.graphics.getHeight() * 2; //shadowMapWidth;
		var shadowViewportWidth = 26f; // * 16f/9f;
		var shadowViewportHeight = shadowViewportWidth; //20f;
		float shadowNear = 0.01f;
		float shadowFar = 60f;
		float intensity = 0.5f;
		
		var shadowLight = new LapisDSL(shadowMapWidth, shadowMapHeight, shadowViewportWidth, shadowViewportHeight, shadowNear, shadowFar);
		shadowLight.set(intensity, intensity, intensity, -1f, -1f, -0.5f);
		
		env.shadowMap = shadowLight;
		env.add(shadowLight);
		return shadowLight;
	}
	
	/**
	 * Create a world
	 */
	public default World createWorld() {
		return new World();
	}
	
	/**
	 * Create a model batch for the world
	 */
	public default ModelBatch createWorldBatch() {
		String vert = Gdx.files.internal("res/shaders/default.vertex.glsl").readString();
		String frag = Gdx.files.internal("res/shaders/default.fragment.glsl").readString();
		return new ModelBatch(new DefaultShaderProvider(vert, frag)); 
	}

	/**
	 * Create a shadow model batch for the world
	 */
	public default ModelBatch createWorldShadowBatch() {
		String vert = Gdx.files.internal("res/shaders/depth.vertex.glsl").readString();
		String frag = Gdx.files.internal("res/shaders/depth.fragment.glsl").readString();
		return new ModelBatch(new DepthShaderProvider(vert, frag)); 
	}

	/**
	 * Create an FBO for post process
	 */
	public default FrameBuffer createFBO() {
		return new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, false); // 1024 * 2 * 2
	}
	
	/**
	 * Create a sprite batch for post process
	 */
	public default SpriteBatch createSpriteBatch() {
		var vert = Gdx.files.internal("res/shaders/postProcess.vertex.glsl");
		var frag = Gdx.files.internal("res/shaders/postProcess.fragment.glsl");
		return new SpriteBatch(1000, new ShaderProgram(vert, frag));
	}

	public default ParticleSystem createPfxSystem(Camera cam) {
		var particleSystem = new ParticleSystem();
		// Since some of our particle effects are made of PointSprites, we create a PointSpriteParticleBatch
		PointSpriteParticleBatch pointSpriteBatch = new PointSpriteParticleBatch();
		pointSpriteBatch.setCamera(cam);
		particleSystem.add(pointSpriteBatch);
		// Since some of our particle effects are  made of ModelInstances, we create a ModelInstanceParticleBatch
		ModelInstanceParticleBatch modelParticleBatch = new ModelInstanceParticleBatch();
		particleSystem.add(modelParticleBatch);
		return particleSystem;
	}
	
	/**
	 * Create a model batch to render the whole Particle Effects System
	 */
	public default ModelBatch createPfxBatch() {
		String vert = Gdx.files.internal("res/shaders/default.vertex.glsl").readString();
		String frag = Gdx.files.internal("res/shaders/default.fragment.glsl").readString();
		return new ModelBatch(new DefaultShaderProvider(vert, frag)); 
	}
	
}
