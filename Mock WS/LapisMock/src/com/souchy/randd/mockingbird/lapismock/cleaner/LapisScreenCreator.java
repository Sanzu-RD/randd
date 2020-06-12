package com.souchy.randd.mockingbird.lapismock.cleaner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;


@SuppressWarnings({ "deprecation", "unused" })
public interface LapisScreenCreator {

	/**
	 * Create LML UI
	 */
	public AbstractLmlView createUI();
	
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
	
	public default DirectionalShadowLight createShadowLight(Environment env) {
		var shadowMapWidth = 1024 * 2 * 2;
		var shadowMapHeight = 1024 * 2 * 2;
		var shadowViewportWidth = 70;
		var shadowViewportHeight = 70;
		float shadowNear = 0.1f;
		float shadowFar = 100f;
		float intensity = 0.5f;
		
		var shadowLight = new DirectionalShadowLight(shadowMapWidth, shadowMapHeight, shadowViewportWidth, shadowViewportHeight, shadowNear, shadowFar);
		shadowLight.set(intensity, intensity, intensity, -1f, -1.0f, -0.5f);
		
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
		String vert = Gdx.files.internal("shaders/default.vertex.glsl").readString();
		String frag = Gdx.files.internal("shaders/default.fragment.glsl").readString();
		return new ModelBatch(new DefaultShaderProvider(vert, frag)); 
	}

	/**
	 * Create a shadow model batch for the world
	 */
	public default ModelBatch createWorldShadowBatch() {
		String vert = Gdx.files.internal("shaders/depth.vertex.glsl").readString();
		String frag = Gdx.files.internal("shaders/depth.fragment.glsl").readString();
		return new ModelBatch(new DepthShaderProvider(vert, frag)); 
	}

	/**
	 * Create an FBO for post process
	 */
	public default FrameBuffer createFBO() {
		return new FrameBuffer(Format.RGBA8888, 1024 * 2 * 2, 1024 * 2 * 2, true, false);
	}
	
	/**
	 * Create a sprite batch for post process
	 */
	public default SpriteBatch createSpriteBatch() {
		var vert = Gdx.files.internal("shaders/postProcess.vertex.glsl");
		var frag = Gdx.files.internal("shaders/postProcess.fragment.glsl");
		return new SpriteBatch(1000, new ShaderProgram(vert, frag));
	}

	/**
	 * Create a model batch for particle effects rendering
	 */
	public default ModelBatch createPfxBatch() {
		String vert = Gdx.files.internal("shaders/default.vertex.glsl").readString();
		String frag = Gdx.files.internal("shaders/default.fragment.glsl").readString();
		return new ModelBatch(new DefaultShaderProvider(vert, frag)); 
	}
	
}
