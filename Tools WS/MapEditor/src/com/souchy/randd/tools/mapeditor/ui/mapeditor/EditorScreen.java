package com.souchy.randd.tools.mapeditor.ui.mapeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.souchy.jeffekseer.EffectManager;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisScreen;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.RenderOptions;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.commons.lapis.world.World;
import com.souchy.randd.tools.mapeditor.controls.GeckoControls;
import com.souchy.randd.tools.mapeditor.main.MapEditorCore;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.main.MapWorld;

public class EditorScreen extends LapisScreen {
	
	public EditorScreenHud hud;
	public MapWorld world;
	public GeckoControls controller;
	
	//ModelInstance tree;
	ShaderProgram shaderp;

	private float period = 5; // period time in seconds
	private float time = 0; // current time
	private float previousSplit = 0;
	
	
	public EditorImGuiHud imgui;
	
	@Override
	public void init() {
		super.init();
		
		//Shader s;
		shaderp = new ShaderProgram(Gdx.files.internal("shaders/passthrough.vsh"), Gdx.files.internal("shaders/passthrough.fsh"));
		
		imgui = new EditorImGuiHud();
		imgui.create();
	}

	@Override
	protected void act(float delta) {
		// controller
		if(controller != null) controller.act(delta);
		// camera 
		getCamera().update();

		// load assets and (one-shot proc) if finished
		if(LapisAssets.update()) {
			//((SapphireHudSkin) VisUI.getSkin()).finishLoading(); 
			//hud.reload();
			//SapphireWorld.world.finishLoading();
			MapWorld.world.finish();
			Log.info("EditorScreen finished loading assets");
		}
		
		/*
		// dessine le monde 3D dans la drawingSpace seulement
		Rectangle drawingSpace = hud.getDrawingSpace();
	    //Gdx.gl.glViewport((int) drawingSpace.x, (int) drawingSpace.y, (int) drawingSpace.width, (int) drawingSpace.height);
		float currSplit = hud.properties.getSplit();
		if(previousSplit != 0 && previousSplit != currSplit) {
			//MapEditorGame.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

			//Rectangle drawingSpace = ((EditorScreenHud) MapEditorGame.screen.getHud()).getDrawingSpace();
			MapEditorGame.screen.getViewport().update((int) drawingSpace.width, (int) drawingSpace.height); //drawingSpace[2], drawingSpace[3], false);
		}
		previousSplit = currSplit;
		*/
	}

	
	@Override
	public void renderWorld() {
		// tint world
//		Color color = Color.PINK;
//		clearScreen(color.r, color.g, color.b, color.a);
		super.renderWorld();
	}
	
	@Override
	public void renderView(float delta) {
		//getViewport().apply();
		// Met le glViewport normal pour dessiner le Hud par la suite
	    //Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		
		super.renderView(delta);
		imgui.render(delta);
	}
	
	@Override
	public LapisHud createUI() {
		return hud = new EditorScreenHud();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		/*
		// resize le hud à la bonne dimension avant pour pouvoir calculer la zone de dessin 3D correctement
//		getHud().resize(width, height);
//		Rectangle drawingSpace = hud.getDrawingSpace();
//		getViewport().update((int) drawingSpace.width, (int) drawingSpace.height); //drawingSpace[2], drawingSpace[3], false);
		
		
		// resize le hud à la bonne dimension avant pour pouvoir calculer la zone de dessin 3D correctement
		getView().resize(width, height);
		// resize le hud
		if(getView() != null) getView().resize(width, height, true);
		
		// calcule la grandeur du 3d view
		Rectangle drawingSpace = hud.getDrawingSpace();
		int x = (int) drawingSpace.x;
		int y = (int) drawingSpace.y;
		int w = (int) drawingSpace.width;
		int h = (int) drawingSpace.height;
//		w = width;
//		h = height;
		// resize tous les components du 3d view
		
		// resize le FBO
		if(fbo != null) fbo = createFBO(w, h);
		// resize le viewport du world
		if(getViewport() != null) getViewport().update(w, h, false);
		// resize le viewport de la shadowlight pour sa shadowmap
		if(getShadowLight() != null) getShadowLight().update(w, h);
		// resize la sprite batch du fbo / post processing
		if(getSpriteBatch() != null) getSpriteBatch().getProjectionMatrix().setToOrtho2D(-x, -y, width, height);
		// resize la sprite batch qui n'a pas de post processing
		cleanSpriteBatch.getProjectionMatrix().setToOrtho2D(-x, -y, width, height);
		
		getCamera().update();

		RenderOptions.activatePP = true;
		Log.debug("EditorScreen resize : full [%s, %s], 3d [%s, %s], cam [%s, %s]", width, height, w, h, getCamera().viewportWidth, getCamera().viewportHeight);
		*/
	}
	

	@Override
	public World createWorld() {
		return world = new MapWorld();
	}

	@Override
	public Environment createEnvironment() {
		return super.createEnvironment();
	}

	@Override
	public Camera createCam(boolean ortho) {
//		Camera cam = super.createCam(ortho);
//		cam.near = 1;
//		cam.far = 300;
//		return cam;
		return new OrthographicCamera();
	}

	@Override
	public Viewport createViewport(Camera cam) {
		//return super.createViewport(cam);
		
		// how many meters high is the screen (ex 1920/1080 = 28.4/16)
//		float viewportSize = 16;  // acts as a zoom (lower number is closer zoom)
//		var viewport = new ExtendViewport(25, 25, cam); //viewportSize * 16/9, viewportSize, cam);
//
//		Rectangle drawingSpace = hud.getDrawingSpace();
//		viewport.update((int) drawingSpace.width, (int) drawingSpace.height); // Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		return viewport;
		
		// ratio à mettre dans les settings public
		float aspectRatio = 16 / 9f; 

		// how many meters high is the screen (ex 1920/1080 = 28.4/16)
		// acts as a zoom (lower number is closer zoom)
		// hauteur min à mettre ds settings privés 
		float minWorldY = 16; 
		float minWorldX = minWorldY * aspectRatio;
		// width et height sont en world units pour contrôller how much du monde qu'on voit
		// cela est ensuite scalé pour s'adapter à la grandeur de la fenêtre
		//var viewport = new ExtendViewport(minWorldX, minWorldY, cam);
		var viewport = new ExtendViewport(minWorldX, minWorldY, 100, 100, cam);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // viewport.apply();
		return viewport;
	}

	@Override
	public InputProcessor createInputProcessor() {
		var multi = new InputMultiplexer();
		if(getView() != null) multi.addProcessor(getView().getStage());
		multi.addProcessor(controller = new GeckoControls(getCamera()));
		return multi;
	}
	
	
	@Override
	public void resetCamera() {
		topView();
		getCamera().rotate(45, getCamera().up.y, -getCamera().up.x, 0);
//		if(MapEditorGame.currentMap.get() == null) {
//			super.resetCamera();
//			return;
//		}
//		float w = MapEditorGame.currentMap.get().layer0Models[0].length; //.getWidth();
//		float h = MapEditorGame.currentMap.get().layer0Models.length; //.getHeight();
//		if(w == 0) w = 25;
//		if(h == 0) h = 20;
//		getCamera().position.set(w / 2f * MapEditorGame.cellSize, -h / 2f * MapEditorGame.cellSize, 90);
//		getCamera().direction.set(0, 1, -1);
//		getCamera().up.set(0, 1, 0);
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
	public Color getBackgroundColor() {
		return Color.BLACK;
	}
	
	@Override
	public EffectManager createEffekseer(Camera camera, Viewport viewport) {
		// TODO Auto-generated method stub
		return null; // super.createEffekseer(camera, viewport);
	}
	
	/*
	@SuppressWarnings("deprecation")
	@Override
	public Environment createEnvironment() {
		float db = MapEditorGame.properties.directionalBrightness.get(); // directionalBrightness  
		float dx = 0.1f; // directional light X vector
		float dy = dx; 	 // directional light Y vector
		float dz = -1f;  // directional light Z vector
		getEnvironment().set(new ColorAttribute(ColorAttribute.AmbientLight, getAmbiantBrightness(), getAmbiantBrightness(), getAmbiantBrightness() , 1f));
		getEnvironment().set(new ColorAttribute(ColorAttribute.Specular, getAmbiantBrightness(), getAmbiantBrightness(), getAmbiantBrightness() , 1f));
//		if(MapEditorGame.properties.directionalLightCount.get() >= 1) getEnvironment().add(new DirectionalLight().set(db, db, db, dx, dy, dz));
//		if(MapEditorGame.properties.directionalLightCount.get() >= 2) getEnvironment().add(new DirectionalLight().set(db, db, db, -dx, -dy, dz));
//		if(MapEditorGame.properties.directionalLightCount.get() >= 3) getEnvironment().add(new DirectionalLight().set(db, db, db, dx, -dy, dz));
//		if(MapEditorGame.properties.directionalLightCount.get() >= 4) getEnvironment().add(new DirectionalLight().set(db, db, db, -dx, dy, dz));
		
		//getEnvironment().set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, .6f, 1f));
		//getEnvironment().add((shadowLight = new DirectionalShadowLight(1024, 1024, 20f, 20f, 1f, 300f))        
		getEnvironment().add((shadowLight = new DirectionalShadowLight(1024*4, 1024*4, 600f, 600f, 0.1f, 100f)).set(db, db, db, dx, dy, dz)); 
		getEnvironment().shadowMap = shadowLight; 
		
//			Gdx.gl.glEnable(GL20.GL_POLYGON_OFFSET_FILL);
//			Gdx.gl20.glPolygonOffset(0.1f, 0.1f);
//			Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
//			Gdx.gl20.glDepthFunc(GL20.GL_LEQUAL); // GL_LESS is default
		
		//getEnvironment().set(new ColorAttribute(ColorAttribute.Diffuse, getAmbiantBrightness(), getAmbiantBrightness(), getAmbiantBrightness(), 1f));
		//getEnvironment().set(new ColorAttribute(ColorAttribute.Specular, getAmbiantBrightness(), getAmbiantBrightness(), getAmbiantBrightness(), 1f));
		return env;
	}

	
	@Override
	protected void renderShadows(float delta) {
		//int[] drawingSpace = hud.getDrawingSpace();
	  //  Gdx.gl.glViewport(drawingSpace[0], drawingSpace[1], 300, 300); //drawingSpace[2], drawingSpace[3]); //0, 0, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight());
		// TODO Auto-generated method stub

		//Gdx.gl.glCullFace(GL20.GL_FRONT);
		
		super.renderShadows(delta);
		
		time += delta;
		if(time >= period) time = 0;
		double radian = (time / period) * 2 * Math.PI;
		double radius = 2;
		double opp = Math.sin(radian) / radius;
		double adj = Math.cos(radian) / radius;
		shadowLight.direction.x = (float) adj; //+= 0.001;
		shadowLight.direction.y = (float) opp; //+= 0.001;

		//Gdx.gl.glCullFace(GL20.GL_BACK);
	}
	
	@Override
	public void renderHook(float delta) {
		controls.update(delta);
		super.renderHook(delta);
	}

	@Override
	protected void renderHud(float delta) {
		// Met le glViewport normal pour dessiner le Hud par la suite
	    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		super.renderHud(delta);
	}
	
	@Override
	public void resize(int width, int height) {
		// resize le hud à la bonne dimension avant pour pouvoir calculer la zone de dessin 3D correctement
		getHud().resize(width, height);
		Rectangle drawingSpace = hud.getDrawingSpace();
		getViewport().update((int) drawingSpace.width, (int) drawingSpace.height); //drawingSpace[2], drawingSpace[3], false);
	}
	
	
	@Override
	protected void renderWorld(float delta) {
		// dessine le monde 3D dans la drawingSpace seulement
		Rectangle drawingSpace = hud.getDrawingSpace();
	    Gdx.gl.glViewport((int) drawingSpace.x, (int) drawingSpace.y, (int) drawingSpace.width, (int) drawingSpace.height);
		float currSplit = hud.properties.getSplit();
		if(previousSplit != 0 && previousSplit != currSplit) {
			//MapEditorGame.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

			//Rectangle drawingSpace = ((EditorScreenHud) MapEditorGame.screen.getHud()).getDrawingSpace();
			MapEditorGame.screen.getViewport().update((int) drawingSpace.width, (int) drawingSpace.height); //drawingSpace[2], drawingSpace[3], false);
		}
		previousSplit = currSplit;
		
		if(MapEditorGame.properties.showGrid.get()) lining.renderLines();
		
	//	shaderp.begin();
		super.renderWorld(delta);
	//	shaderp.end();
	}
	
	
	@Override
	public Camera createCam(boolean ortho) {
		Camera cam = super.createCam(ortho);
		cam.near = 2;
		cam.far = 300;
		return cam;
	}
	
	public void resetCam() {
		float w = MapEditorGame.currentMap.get().getWidth();
		float h = MapEditorGame.currentMap.get().getHeight();
		if(w == 0) w = 25;
		if(h == 0) h = 20;
		getCamera().position.set(w / 2f * MapEditorGame.cellSize, -h / 2f * MapEditorGame.cellSize, 90);
		getCamera().direction.set(0, 1, -1);
		getCamera().up.set(0, 1, 0);
	}
	
	@Override
	public Viewport createViewport(Camera cam) {
		// return super.createView(cam);
		float aspectRatio = 16 / 9f; // ratio à mettre dans les settings public
		float minWorldY = 50; // hauteur min à mettre ds settings privés
		float minWorldX = minWorldY * aspectRatio;
		// width et height sont en world units pour contrôller how much du monde qu'on voit
		// cela est ensuite scalé pour s'adapter à la grandeur de la fenêtre
		Viewport view = Viewports.extend(minWorldX, minWorldY, cam);
		view.apply();
		return view;
	}

	@Override
	public Screen2d getHud() {
		return hud;
	}
	*/


}
