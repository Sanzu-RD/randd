package com.souchy.randd.tools.mapeditor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.souchy.randd.ebishoal.commons.lapis.drawing.LineDrawing;
import com.souchy.randd.ebishoal.commons.lapis.screens.ComposedScreen;
import com.souchy.randd.ebishoal.commons.lapis.screens.Viewports;
import com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens.Screen2d;
import com.souchy.randd.tools.mapeditor.MapEditorGame;
import com.souchy.randd.tools.mapeditor.listeners.Controls3d;

public class EditorScreen extends ComposedScreen {

	private EditorScreenHud hud;
	private Controls3d controls;
	private LineDrawing lining;

	@Override
	protected void createHook() {
		VisUI.load();
		hud = new EditorScreenHud();
		super.createHook();
		
		//CameraInputController camController = new CameraInputController(getCam());
        //Gdx.input.setInputProcessor(camController);
		
		lining = new LineDrawing(getCam(), null);
		lining.createGrid(5, 3000, 3000);
		lining.createCross();
		controls = new Controls3d(getCam());
		hud.getStage().addListener(controls);
	}
	
	@Override
	protected float getAmbiantBrightness() {
		return MapEditorGame.properties.ambiantBrightness.get(); // super.getAmbiantBrightness();
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
		getCam().position.set(w/2f * MapEditorGame.cellSize, -h/2f * MapEditorGame.cellSize, 90);
		getCam().direction.set(0, 1, -1);
		getCam().up.set(0, 1, 0);
	}
	
	@Override
	protected Viewport createView(Camera cam) {
		//return super.createView(cam);
		float aspectRatio = 16/9f;	// ratio à mettre dans les settings public
		float minWorldY = 50; 		// hauteur min à mettre ds settings privés
		float minWorldX = minWorldY * aspectRatio;
		// width et height sont en world units pour contrôller how much du monde qu'on voit
		// cela est ensuite scalé pour s'adapter à la grandeur de la fenêtre
		Viewport view = Viewports.extend(minWorldX, minWorldY,  cam);
		view.apply();
		return view;
	}

	@Override
	public void renderHook(float delta) {
		controls.update(delta);
		lining.renderLines();
		super.renderHook(delta);
	}

	
	@Override
	public Screen2d getHud() {
		return hud;
	}
	
	

	@Override
	public Color getBGColor() {
		return Color.BLACK;
	}
	
	
	
}
