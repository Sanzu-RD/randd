package com.souchy.randd.tools.mapeditor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.souchy.randd.ebishoal.commons.lapis.drawing.LineDrawing;
import com.souchy.randd.ebishoal.commons.lapis.screens.ComposedScreen;
import com.souchy.randd.ebishoal.commons.lapis.screens.Viewports;
import com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens.Screen2d;
import com.souchy.randd.tools.mapeditor.MapEditorCore;
import com.souchy.randd.tools.mapeditor.MapEditorGame;
import com.souchy.randd.tools.mapeditor.listeners.Controls3d;

public class EditorScreen extends ComposedScreen {
	
	private EditorScreenHud hud;
	private Controls3d controls;
	private LineDrawing lining;
	
	//ModelInstance tree;
	
	@Override
	protected void createHook() {
		hud = new EditorScreenHud();
		super.createHook();
		
		// CameraInputController camController = new CameraInputController(getCam());
		// Gdx.input.setInputProcessor(camController);
		
		lining = new LineDrawing(getCam(), null);
		lining.createGrid(5, 3000, 3000);
		lining.createCross();
		controls = new Controls3d(getCam());
		hud.getStage().addListener(controls);
		
		var modelManager = MapEditorCore.core.getGame().modelManager;
		String name = "tree1";
		FileHandle file = Gdx.files.internal("g3d/object_models/" + name + ".g3dj");
		var f = file.file();
		var s = f.getAbsolutePath();
		System.out.println("---- " + file.file().exists());
		System.out.println("---- " + file.file().getAbsolutePath());
		modelManager.load(file);
		modelManager.finishLoading();
		// assets.load(file.path(), Model.class);
		// assets.finishLoading();
		
		var obj = modelManager.get(name);
		System.out.println(obj);
		
		// obj.meshes.forEach(m -> m.scale(2, 2, 2));
		BoundingBox bb = obj.calculateBoundingBox(new BoundingBox());
		System.out.println(bb);
		
		ModelInstance tree = new ModelInstance(obj);
		
		tree.transform.translate(new Vector3(3, 7, 0).scl(MapEditorGame.cellSize));
		tree.transform.rotateRad(Vector3.X, (float) (Math.PI/2));
		getWorld().addTemp(tree);
		
		
		//Shader s;
		ShaderProgram shader = new ShaderProgram(Gdx.files.internal("shaders/passthrough.vsh"), Gdx.files.internal("shaders/passthrough.fsh"));
	}
	

	@Override
	protected float getAmbiantBrightness() {
		return MapEditorGame.properties.ambiantBrightness.get(); // super.getAmbiantBrightness();
	}
	

	@SuppressWarnings("deprecation")
	@Override
	public void createEnvironment() {
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
		getEnvironment().add((shadowLight = new DirectionalShadowLight(5024, 5024, 600f, 600f, 0.1f, 100f))                  
	                .set(db, db, db, dx, dy, dz)); //.set(1f, 1f, 1f, 40.0f, -35f, -35f));   
		getEnvironment().shadowMap = shadowLight; 
		
//			Gdx.gl.glEnable(GL20.GL_POLYGON_OFFSET_FILL);
//			Gdx.gl20.glPolygonOffset(0.1f, 0.1f);
//			Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
//			Gdx.gl20.glDepthFunc(GL20.GL_LEQUAL); // GL_LESS is default
		
		//getEnvironment().set(new ColorAttribute(ColorAttribute.Diffuse, getAmbiantBrightness(), getAmbiantBrightness(), getAmbiantBrightness(), 1f));
		//getEnvironment().set(new ColorAttribute(ColorAttribute.Specular, getAmbiantBrightness(), getAmbiantBrightness(), getAmbiantBrightness(), 1f));
	}

	private float period = 5; // period time in seconds
	private float time = 0; // current time
	
	@Override
	protected void renderShadows(float delta) {
		//int[] drawingSpace = hud.getDrawingSpace();
	  //  Gdx.gl.glViewport(drawingSpace[0], drawingSpace[1], 300, 300); //drawingSpace[2], drawingSpace[3]); //0, 0, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight());
		// TODO Auto-generated method stub
		super.renderShadows(delta);
		
		time += delta;
		if(time >= period) time = 0;
		double radian = (time / period) * 2 * Math.PI;
		double radius = 2;
		double opp = Math.sin(radian) / radius;
		double adj = Math.cos(radian) / radius;
		shadowLight.direction.x = (float) adj; //+= 0.001;
		shadowLight.direction.y = (float) opp; //+= 0.001;
	}
	
	@Override
	public void renderHook(float delta) {
		controls.update(delta);
		super.renderHook(delta);
	}
	
	@Override
	protected boolean orderHudToFront() {
		return false;
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
	
	private float previousSplit = 0;
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
		super.renderWorld(delta);
	}
	
	
	
	@Override
	protected Camera createCam() {
		Camera cam = super.createCam();
		cam.near = 2;
		cam.far = 300;
		return cam;
	}
	
	public void resetCam() {
		float w = MapEditorGame.currentMap.get().getWidth();
		float h = MapEditorGame.currentMap.get().getHeight();
		if(w == 0) w = 25;
		if(h == 0) h = 20;
		getCam().position.set(w / 2f * MapEditorGame.cellSize, -h / 2f * MapEditorGame.cellSize, 90);
		getCam().direction.set(0, 1, -1);
		getCam().up.set(0, 1, 0);
	}
	
	@Override
	protected Viewport createView(Camera cam) {
		// return super.createView(cam);
		float aspectRatio = 16 / 9f; // ratio à mettre dans les settings public
		float minWorldY = 50; // hauteur min à mettre ds settings privés
		float minWorldX = minWorldY * aspectRatio;
		// width et height sont en world units pour contrôller how much du monde qu'on
		// voit
		// cela est ensuite scalé pour s'adapter à la grandeur de la fenêtre
		Viewport view = Viewports.extend(minWorldX, minWorldY, cam);
		view.apply();
		return view;
	}

	@Override
	public Screen2d getHud() {
		return hud;
	}


	
}
