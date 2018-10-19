package com.souchy.randd.ebishoal.sapphire.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.HdpiUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.souchy.randd.ebishoal.commons.lapis.drawing.LineDrawing;
import com.souchy.randd.ebishoal.commons.lapis.screens.Cameras;
import com.souchy.randd.ebishoal.commons.lapis.screens.ComposedScreen;
import com.souchy.randd.ebishoal.commons.lapis.screens.Viewports;

public class GameScreen extends ComposedScreen {
	
	LineDrawing lines;
	ModelInstance instance;
	
	
	@Override
	protected void createHook() {
		super.createHook();
		
		Material mat = new Material(ColorAttribute.createDiffuse(Color.GREEN));
		long attributes = Usage.Position | Usage.Normal;
		
		ModelBuilder builder = new ModelBuilder();
		//builder.begin();
		Model model = builder.createBox(5f, 5f, 5f, mat, attributes);
		
		instance = new ModelInstance(model);
		//instance.transform.set(new Vector3(0,0,0), new Quaternion(0,0,0,0));

		/*getWorld().cache.begin();
		getWorld().cache.add(instance);
		getWorld().cache.end();*/
		
		//getCam().position.set(1, 1, 1);
		//getCam().lookAt(0, 0, 0);
		//getCam().update();
		
		getEnvironment().clear();
		getEnvironment().set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		getEnvironment().add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		
		lines = new LineDrawing(getCam(), null);
		lines.createCross(Color.RED, Color.GREEN, Color.BLUE);
		lines.addLine(new Vector3(1,1,1), new Vector3(2,2,2), Color.CORAL);
		lines.addLine(new Vector3(2.5f,2.5f,5), new Vector3(-2.5f,2.5f,5), Color.CYAN);
		lines.addLine(new Vector3(2.5f,2.5f,5), new Vector3(2.5f,-2.5f,5), Color.CYAN);
		lines.addLine(new Vector3(-2.5f,2.5f,5), new Vector3(-2.5f,-2.5f,5), Color.CYAN);
		lines.addLine(new Vector3(2.5f,-2.5f,5), new Vector3(-2.5f,-2.5f,5), Color.CYAN);
		
		CameraInputController camController = new CameraInputController(getCam());
        Gdx.input.setInputProcessor(camController);


    	LabelStyle hongkong = new LabelStyle();
		Texture honkongTex = new Texture(Gdx.files.internal("res/hongkonghustle-hiero-100_00.png"), true); // true enables mipmaps
		honkongTex.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear); // linear filtering in nearest mipmap image
		BitmapFont hongkongFont = new BitmapFont(Gdx.files.internal("res/hongkonghustle-hiero-100.fnt"), new TextureRegion(honkongTex), false);
		hongkong.font = hongkongFont;
		VisLabel lbl = new VisLabel("HELLO T", hongkong);
		lbl.setFontScale(30 / 100f);
		lbl.setPosition(0, 0);
		a.getStage().addActor(lbl);
	}


	@Override
	protected Camera createCam() {
		
		OrthographicCamera cam = new OrthographicCamera(Gdx.graphics.getHeight(), Gdx.graphics.getWidth());
		cam.position.set(10, 10, 10);
		//cam.fieldOfView = 67;
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 1000f;
		cam.update();
		return cam;
		
		/*
		var a = true;
		if(!a) {
			Vector3 camPos = new Vector3(10, 25, 10);
			float fieldOfView = 67; // à mettre dans les settings
			float near = 0.1f; 		  // à mettre dans les settings
			float far = 200f; 		  // à mettre dans les settings
			Camera cam = Cameras.perspective(camPos, Vector3.Zero, fieldOfView, near, far);
			cam.lookAt(1,1,0);
			cam.update();
			return cam;
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
		float aspectRatio = 16/9f;	// ratio à mettre dans les settings public
		float minWorldY = 50; 		// hauteur min à mettre ds settings privés
		float minWorldX = minWorldY * aspectRatio;
		// width et height sont en world units pour contrôller how much du monde qu'on voit
		// cela est ensuite scalé pour s'adapter à la grandeur de la fenêtre
		Viewport view = Viewports.extend(minWorldX, minWorldY, minWorldX, minWorldY, cam);
		//Viewport view = Viewports.scaling(Scaling.fit, minWorldX, minWorldY, cam);
		//view.setScreenPosition(0,0); //Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		view.apply(true);
		/*HdpiUtils.glViewport((int)-minWorldX/2, (int)-minWorldY/2, (int)minWorldX, (int)minWorldY);
		getCam().viewportWidth = minWorldX;
		getCam().viewportHeight = minWorldY;*/
		//if (centerCamera) camera.position.set(worldWidth / 2, worldHeight / 2, 0);
		getCam().update();
		return view;
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
asdf		super.resize(width, height);
		a.resize(width, height);
		//getViewport().update(width, height, true);
		//getViewport().update(-1, 0, true);
	}
	
	@Override
	protected void renderHook(float delta) {
        getBatch().begin(getCam());
        getBatch().render(instance, getEnvironment());
        getBatch().end();

		lines.srender.setProjectionMatrix(getCam().combined);
        lines.renderLines();
        
        getCam().update();
	}

	@Override
	public void dispose() {
		getBatch().dispose();
		instance.model.dispose();
	}
	
	
}
