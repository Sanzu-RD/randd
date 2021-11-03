package com.souchy.randd.tools.mapeditor.ui.mapeditor;

import java.io.IOException;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.badlogic.gdx.video.VideoPlayerDesktop;
import com.badlogic.gdx.video.VideoPlayerInitException;
import com.kotcrab.vis.ui.VisUI;
import com.souchy.jeffekseer.EffectManager;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisScreen;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.RenderOptions;
import com.souchy.randd.ebishoal.commons.lapis.gfx.shaders.LapisShader;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.commons.lapis.world.World;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.ExtendableShader;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.ExtendableShader.ShadeProvider;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.DissolveUniforms;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.DissolveUniforms.DissolveBorderColorAttribute;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.DissolveUniforms.DissolveBorderWidthAttribute;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.DissolveUniforms.DissolveIntensityAttribute;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.DissolveUniforms.DissolveMaterial;
import com.souchy.randd.mockingbird.lapismock.shaders.ssaoshaders.uniforms.DissolveUniforms.DissolveTextureAttribute;
import com.souchy.randd.tools.mapeditor.controls.GeckoControls;
import com.souchy.randd.tools.mapeditor.main.MapEditorCore;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.main.MapWorld;
import com.souchy.randd.tools.mapeditor.shader.Shader2;
import com.souchy.randd.tools.mapeditor.shader.Shader2Config;
import com.souchy.randd.tools.mapeditor.shader.Shader2Provider;
import com.souchy.randd.tools.mapeditor.video.VidPlayer;

import net.mgsx.gltf.loaders.glb.GLBAssetLoader;
import net.mgsx.gltf.loaders.gltf.GLTFAssetLoader;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.scene3d.scene.SceneSkybox;
import net.mgsx.gltf.scene3d.utils.IBLBuilder;

public class EditorScreen extends LapisScreen {
	
	public EditorScreenHud hud;
	public MapWorld world;
	public GeckoControls controller;
	
	//ModelInstance tree;
	ShaderProgram shaderp;

	// light cycle
	public float time = 0; // current time
	private float period = 30; // period time in seconds
	private double radius = 2; // circle radius
	
	
	public Shader2Provider provider;
	public EditorImGuiHud imgui;
	
	
	public VideoPlayer player;
	
	@Override
	public void init() {
		super.init();
		
		//Shader s;
		shaderp = new ShaderProgram(Gdx.files.internal("shaders/passthrough.vsh"), Gdx.files.internal("shaders/passthrough.fsh"));
		
		//new DefaultShaderProvider(LapisShader.getVertexShader("base"), LapisShader.getFragmentShader("base"));
		
		imgui = new EditorImGuiHud();
		imgui.create();
		
		if(false) {
			var playerviewport =  new FitViewport(1, 1); // new ExtendViewport(1920, 1080);
			player =  new VidPlayer(playerviewport); //VideoPlayerCreator.createVideoPlayer(getViewport()); //new VideoPlayerDesktop(getViewport());
			final String video= "res/videos/blue_plate.webm"; 
			try {
				player.play(Gdx.files.internal(video));
			} catch (IOException e) {
				e.printStackTrace();
			}
			player.setOnCompletionListener(new VideoPlayer.CompletionListener() {
				@Override
				public void onCompletionListener (FileHandle file) {
					//Gdx.app.log("VideoTest", file.name() + " fully played.");
					try {
						player.stop();
						//player = new VidPlayer(playerviewport); 
						player.play(Gdx.files.internal(video));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			player.setOnVideoSizeListener(new VideoPlayer.VideoSizeListener() {
				@Override
				public void onVideoSize (float width, float height) {
					//Gdx.app.log("VideoTest", "The video has a size of " + width + "x" + height + ".");
				}
			});
			//player.dispose();
			//player = null;
		}
	}

	@Override
	public void drawBackground(SpriteBatch batch) {
//		super.drawBackground(batch);
		try {
			if(player != null) player.render();
		} catch (Exception e) {
			//Log.error("", e);
		}
	}
	@Override
	public void clearScreenFbo() {
//		clearScreen(0, 0, 0, 0);
		clearScreen();
	}
	@Override
	public void clearScreenFboSpriteBatch() {
//		clearScreen();
	}
	@Override
	public Color getBackgroundColor() {
		return Color.BLACK;
	}

	@Override
	protected void act(float delta) {
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
		
		// load assets and proc if finished
		if(LapisAssets.update()) {
			//((SapphireHudSkin) VisUI.getSkin()).finishLoading(); 
			//hud.reload();
			MapWorld.world.finish();
			Log.info("EditorScreen finished loading assets");
		}

	}

	
	@Override
	public ModelBatch createWorldBatch() {
//		if(true) {
			provider = new Shader2Provider((r) -> {
				// put the config loading in here so that it is reloaded whenever we call shaderProvider.reset();
				var conf = Shader2Config.defaultConfig();
				conf.add(new com.souchy.randd.tools.mapeditor.shaderimpl.DissolveUniforms());
				conf.compile();
				//Log.info("" + conf.vertexShader);
				//Log.info("" + conf.fragmentShader);
				return new Shader2(r, conf);
			});
			return new ModelBatch(provider);
//		} 
//		else
//		// shader provider with base shader + dissolve pre-compiled into the config for any shader generated by the provider
//		return new ModelBatch(provider = new ShadeProvider(r -> {
//			var conf = new ExtendableShader.ExtendableConfig();
//			conf.add(new DissolveUniforms());
//			conf.compile("base");
//			return new ExtendableShader(r, conf);
//		}));
		//return new ModelBatch(new DefaultShaderProvider(LapisShader.getVertexShader("base"), LapisShader.getFragmentShader("base"))); 
	}
	
	@Override
	public void renderView(float delta) {
		imgui.render(delta);
		super.renderView(delta);
	}
	
	@Override
	public LapisHud createUI() {
		return hud = new EditorScreenHud();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		imgui.resizeScreen(width, height);
		if(player != null) player.resize(width, height);
	}
	
	@Override
	public World createWorld() {
		return world = new MapWorld();
	}

	@Override
	public Camera createCam(boolean ortho) {
		return new OrthographicCamera();
	}

	@Override
	public Viewport createViewport(Camera cam) {
		// ratio à mettre dans les settings public
		float aspectRatio = 16 / 9f; 
		// how many meters high is the screen (ex 1920/1080 = 28.4/16) // acts as a zoom (lower number is closer zoom)
		// hauteur min à mettre ds settings privés 
		float minWorldY = 16; 
		float minWorldX = minWorldY * aspectRatio;
		// width et height sont en world units pour contrôller how much du monde qu'on voit
		// cela est ensuite scalé pour s'adapter à la grandeur de la fenêtre
		//var viewport = new ExtendViewport(minWorldX, minWorldY, cam);
		var viewport = new ExtendViewport(minWorldX, minWorldY, 100, 100, cam);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		return viewport;
	}

	@Override
	public InputProcessor createInputProcessor() {
		controller = new GeckoControls(getCamera());
		var multi = new InputMultiplexer();
		if(getView() != null) multi.addProcessor(getView().getStage());
		multi.addProcessor(controller);
		return multi;
	}
	
	@Override
	public void resetCamera() {
		topView();
		getCamera().rotate(45, getCamera().up.y, -getCamera().up.x, 0);
	}

	public void topView() {
		var center = getWorldCenter();
		getCamera().direction.set(0, 0, -1f);
		getCamera().up.set(1, 1, -1f);
		getCamera().position.set(center.x, center.y, center.z); 
		getCamera().near = -30f;
		getCamera().far = 120f;
		getCamera().update();
	}
	
	@Override
	public EffectManager createEffekseer(Camera camera, Viewport viewport) {
		return null; // super.createEffekseer(camera, viewport);
	}

	@Override
	public void dispose() {
		super.dispose();
		imgui.dispose();
		if(player != null) player.dispose();
	}
	

}
