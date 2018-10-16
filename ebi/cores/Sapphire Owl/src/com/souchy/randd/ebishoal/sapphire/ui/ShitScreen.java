package com.souchy.randd.ebishoal.sapphire.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;


public class ShitScreen implements Screen {
	
	//public PerspectiveCamera cam;
	public PerspectiveCamera playCam;
	
	public Stage hud;
	public OrthographicCamera hudCam;
	
	private float playCamWidth = 1600; //AConstants.WINDOW_WIDTH;
	private float playCamHeight = 900; //AConstants.WINDOW_HEIGHT;
	private float hudCamWidth = 1600; //AConstants.WINDOW_WIDTH;;
	private float hudCamHeight = 900; //AConstants.WINDOW_HEIGHT;
	
	/**
	 * Détermine si on devrait clear et re-draw l'écran <p>
	 * Les if(dirty) sont à implémenter soi-même, ne fait rien sinon.
	 */
	public boolean dirty = true;

	LabelStyle hongkong = new LabelStyle();

	public ShitScreen(){ 

		Texture honkongTex = new Texture(Gdx.files.internal("res/hongkonghustle-hiero-100_00.png"), true); // true enables mipmaps
		honkongTex.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear); // linear filtering in nearest mipmap image
		BitmapFont hongkongFont = new BitmapFont(Gdx.files.internal("res/hongkonghustle-hiero-100.fnt"), new TextureRegion(honkongTex), false);
		hongkong.font = hongkongFont;
		
	}
	
	@Override
	public void show() {
        Gdx.input.setInputProcessor(hud);
		Gdx.graphics.setWindowedMode((int)hudCamWidth, (int)hudCamHeight); // Gdx.graphics.setDisplayMode((int)hudCamWidth, (int)hudCamHeight, false);
	}

	public void create(){
		
		Gdx.graphics.setWindowedMode((int)hudCamWidth, (int)hudCamHeight); // Gdx.graphics.setDisplayMode((int)hudCamWidth, (int)hudCamHeight, false);
		
		// Cam
		//cam = setupCameraPers();
		playCam = setupPlayCamera(); //

		// Hud Cam
		hudCam = setupHudCamera();
		hud = new Stage(new ScreenViewport(hudCam));
		//hud.getViewport().setCamera(hudCam);
		
        //postCreateHook();

		VisLabel a = new VisLabel("Hello", hongkong);
		a.setColor(Color.WHITE);
		a.setFontScale(50 / 100f);
		a.setPosition(30, 165);
		hud.addActor(a);
	}

	@Override
	public void render(float delta) {
		hudCam.update();
		playCam.update();
		
        //preDrawHook(delta);
        
		clearScreen();
		draw(delta);
		drawHud(delta);
		
		//postDrawHook(delta);
	}
	
	/**
	 * Je donne la possibilité d'override pour ajouter la fonction de dirty
	 */
	protected void clearScreen(){
		// OpenGL cleaning
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(getBGColor().r, getBGColor().g, getBGColor().b, getBGColor().a);
	}

	public OrthographicCamera setupHudCamera(){
		OrthographicCamera camera = new OrthographicCamera(hudCamWidth, hudCamHeight); // space dimensions
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f); // camera view position (x,y) on the space (center of the space dimension)
        camera.update();
        return camera;
    }
	
	public PerspectiveCamera setupPlayCamera(){
		//PerspectiveCamera cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		PerspectiveCamera cam = new PerspectiveCamera(67, playCamWidth, playCamHeight);
		cam.position.set(0, 3, 13);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();
		return cam;
	}

	/**
	 * Call ceci si tu veux changer la LARGEUR de viewport prise pour cr�er la cam�ra de JEU
	 */
	protected void setPlayCamWidth(float width){
		playCamWidth = width;
	}
	/**
	 * Call ceci si tu veux changer la HAUTEUR de viewport prise pour cr�er la cam�ra de JEU
	 */
	protected void setPlayCamHeight(float height){
		playCamHeight = height;
	}
	/**
	 * Call ceci si tu veux changer la LARGEUR de viewport prise pour cr�er la cam�ra du HUD
	 */
	protected void setHudCamWidth(float width){
		hudCamWidth = width;
	}
	/**
	 * Call ceci si tu veux changer la HAUTEUR de viewport prise pour cr�er la cam�ra du HUD
	 */
	protected void setHudCamHeight(float height){
		hudCamHeight = height;
	}
	
	
	
	public Color getBGColor(){ return Color.SKY; }
	
	public void draw(float delta){}
	public void drawHud(float delta){
		hudCam.update();
		hud.act(delta);
		hud.draw();
		
    }
	
	@Override public void pause(){}
	@Override public void resume(){}
	@Override public void hide(){}
	@Override public void dispose(){ VisUI.dispose(); }
	@Override public void resize(int width, int height){ 
		hud.getViewport().update(width, height, true); 
	}

}
