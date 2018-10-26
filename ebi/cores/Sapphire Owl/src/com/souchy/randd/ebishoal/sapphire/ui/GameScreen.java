package com.souchy.randd.ebishoal.sapphire.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.souchy.randd.ebishoal.commons.lapis.drawing.Line;
import com.souchy.randd.ebishoal.commons.lapis.drawing.LineDrawing;
import com.souchy.randd.ebishoal.commons.lapis.screens.Cameras;
import com.souchy.randd.ebishoal.commons.lapis.screens.ComposedScreen;
import com.souchy.randd.ebishoal.commons.lapis.screens.Viewports;

public class GameScreen extends ComposedScreen {
	
	LineDrawing lines;
	ModelInstance instance;
	//Line viewLine;
	
	
	float aspectRatio = 16/9f;	// ratio à mettre dans les settings public
	float minWorldY = 50; 		// hauteur min à mettre ds settings privés
	float minWorldX = minWorldY * aspectRatio;
	

	@Override
	protected void createHook() {
		super.createHook();
		
		super.overlay1 = new GameScreenHud();
		overlay1.create();
		
		Material mat = new Material(IntAttribute.createCullFace(GL20.GL_FRONT),//For some reason, libgdx ModelBuilder makes boxes with faces wound in reverse, so cull FRONT
			    new BlendingAttribute(1f), //opaque since multiplied by vertex color
			    new DepthTestAttribute(false), //don't want depth mask or rear cubes might not show through
			    ColorAttribute.createDiffuse(Color.GREEN));
		Material mat2 = new Material(IntAttribute.createCullFace(GL20.GL_FRONT),//For some reason, libgdx ModelBuilder makes boxes with faces wound in reverse, so cull FRONT
			    new BlendingAttribute(1f), //opaque since multiplied by vertex color
			    new DepthTestAttribute(false), //don't want depth mask or rear cubes might not show through
			    ColorAttribute.createDiffuse(Color.BLUE));
		long attributes = Usage.Position | Usage.Normal;
		
		ModelBuilder builder = new ModelBuilder();

		getWorld().cache.begin();
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 15; j++) {
				Model model = builder.createBox(5f, 5f, 5f, (i+j)%2==0?mat:mat2, attributes);
				instance = new ModelInstance(model);
				instance.transform.set(new Vector3(5 * i + 1f, 5 * j + 1f, 0), new Quaternion(0, 0, 0, 0));
				getWorld().cache.add(instance);
			}
		}
		getWorld().cache.end();
		
		//getCam().position.set(1, 1, 1);
		//getCam().lookAt(0, 0, 0);
		//getCam().update();
		
		getEnvironment().clear();
		getEnvironment().set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		getEnvironment().add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		
		lines = new LineDrawing(getCam(), null);
		lines.createCross(Color.RED, Color.GREEN, Color.BLUE);
		/*lines.addLine(new Vector3(1,1,1), new Vector3(2,2,2), Color.CORAL);
		lines.addLine(new Vector3(2.5f,2.5f,5), new Vector3(-2.5f,2.5f,5), Color.CYAN);
		lines.addLine(new Vector3(2.5f,2.5f,5), new Vector3(2.5f,-2.5f,5), Color.CYAN);
		lines.addLine(new Vector3(-2.5f,2.5f,5), new Vector3(-2.5f,-2.5f,5), Color.CYAN);
		lines.addLine(new Vector3(2.5f,-2.5f,5), new Vector3(-2.5f,-2.5f,5), Color.CYAN);*/
		
		/*viewLine = new Line(Color.DARK_GRAY,
				new Vector3(a.getViewport().getScreenX(), a.getViewport().getScreenY(),0), 
				new Vector3(a.getViewport().getWorldWidth(), a.getViewport().getWorldHeight(), 0), 
				null);
		lines.addLine(viewLine);*/
		
		CameraInputController camController = new CameraInputController(getCam());
        Gdx.input.setInputProcessor(camController);

        System.out.println(getViewport().getWorldWidth());
		getCam().position.set(getViewport().getWorldWidth()/2, getViewport().getWorldHeight()/2, 60);
		getCam().direction.set(0, 0, -1);
		getCam().up.set(0, 1, 0);
        getCam().update();
	}


	@Override
	protected Camera createCam() {

		Vector3 camPos = new Vector3(40, -20, 100);
		float fieldOfView = 67; // à mettre dans les settings
		float near = 0.1f; 		  // à mettre dans les settings
		float far = 1000f; 		  // à mettre dans les settings
		Camera cam = Cameras.perspective(camPos, Vector3.Zero, fieldOfView, near, far);
		cam.lookAt(0, 0, 0);
		cam.update();
		return cam;
		/*
		PerspectiveCamera cam = new PerspectiveCamera(67, Gdx.graphics.getHeight(), Gdx.graphics.getWidth());
		//OrthographicCamera cam = new OrthographicCamera(); //Gdx.graphics.getHeight(), Gdx.graphics.getWidth());
		cam.position.set(0, 0, 100);
		//cam.fieldOfView = 67;
		cam.lookAt(0, 0, 0);
		cam.near = 0f;
		cam.far = 1000f;
		cam.update();
		return cam;
		
		/*
		var a = true;
		if(!a) {
		
		} else {
			Camera cam = new OrthographicCamera();//Gdx.graphics.getHeight(), Gdx.graphics.getWidth());
			//cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0f);
			cam.position.set(10, 10, 10);
			cam.lookAt(0,0,0);
			cam.near = 0.1f;
			cam.far = 300f;
			
			cam.update();
			return cam;
		}*/
		
	}
	
	@Override
	public void clearScreen() {
		// TODO Auto-generated method stub
		//super.clearScreen();
        //Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(getBGColor().r, getBGColor().g, getBGColor().b, getBGColor().a);
	}
	
//	@Override
//	public void render(float delta) {
//		// TODO Auto-generated method stub
//		//super.render(delta);
//		clearScreen();
//	}

	@Override
	protected Viewport createView(Camera cam) {
		// width et height sont en world units pour contrôller how much du monde qu'on voit
		// cela est ensuite scalé pour s'adapter à la grandeur de la fenêtre
		Viewport view = Viewports.extend(minWorldX, minWorldY, cam);
		//Viewport view = Viewports.scaling(Scaling.fill, minWorldX, minWorldY, getCam());
		view.apply(false);
		/*HdpiUtils.glViewport((int)-minWorldX/2, (int)-minWorldY/2, (int)minWorldX, (int)minWorldY);
		getCam().viewportWidth = minWorldX;
		getCam().viewportHeight = minWorldY;*/
		//if (centerCamera) camera.position.set(worldWidth / 2, worldHeight / 2, 0);
		//getCam().update();
		return view;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		//super.resize(width, height);
		getViewport().update(width, height, false);
		overlay1.resize(width, height);

		//centerCam();
		
		/*viewLine.start = new Vector3(a.getViewport().getScreenX(), a.getViewport().getScreenY(), 0);
		viewLine.end = new Vector3(a.getViewport().getScreenWidth(), a.getViewport().getWorldHeight(), 0);*/
		
		//getViewport().update(width, height, true);
		//getViewport().update(-1, 0, true);
	}
	
	@Override
	protected void renderHook(float delta) {
        if(Gdx.input.isKeyPressed(Keys.DOWN)) {
        	getCam().position.z += 10 * delta;
        }
        if(Gdx.input.isKeyPressed(Keys.UP)) {
        	getCam().position.z -= 10 * delta;
        }

		if(Gdx.input.isKeyPressed(Keys.SPACE)){
			//GameScreen.get().playCam.position.set(p.x, p.y, 20);
			//GameScreen.get().playCam.position.set((float)GridMap.width/2, (float)GridMap.height/2, 20);
			centerCam();
		}
        getCam().update();


       /* getBatch().begin(getCam());
        getBatch().render(getWorld().cache, getEnvironment());
        getBatch().end();*/
        
		lines.srender.setProjectionMatrix(getCam().combined);
        lines.renderLinesExceptColor(Color.DARK_GRAY);

		lines.srender.setProjectionMatrix(overlay1.getCam().combined);
        lines.renderLinesOfColor(Color.DARK_GRAY);

        super.renderHook(delta);
	}

	private void centerCam() {
		System.out.println(
				"w = " + "[" +getViewport().getWorldWidth() + ", h = " + getViewport().getWorldHeight()  + "]"
				+ " / " + "s = " + "[" + getViewport().getScreenWidth() + ", h = " + getViewport().getScreenHeight() + "]"
				+ " /// " + "aw = " +"[" + overlay1.getViewport().getWorldWidth() + "," + overlay1.getViewport().getWorldHeight() + "]"
				+ " / " + "as = " + "[" + overlay1.getViewport().getScreenWidth() + ", " + overlay1.getViewport().getScreenHeight() + "]");
		//getCam().position.set(getViewport().getWorldWidth()/2, getViewport().getWorldHeight()/2, 60);
		getCam().position.set(45, -50, 20);
		getCam().lookAt(45, 0, 0);
		//getCam().position.set(44, 25, 60);
		getCam().direction.set(0, 1, 0);
		getCam().up.set(0, 0, 1);
        getCam().update();
	}
	
	@Override
	public void dispose() {
		getBatch().dispose();
		instance.model.dispose();
	}
	
	
}
