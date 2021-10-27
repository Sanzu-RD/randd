package com.souchy.randd.tools.mapeditor.ui.mapeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.widget.VisTable;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.commons.lapis.gfx.shaders.LapisShader;
import com.souchy.randd.tools.mapeditor.ui.components.BottomBar;
import com.souchy.randd.tools.mapeditor.ui.components.Component;
import com.souchy.randd.tools.mapeditor.ui.components.PropertiesPanel;
import com.souchy.randd.tools.mapeditor.ui.components.TopBar;

public class EditorScreenHud extends LapisHud {
	
	public PropertiesPanel properties;
	//private EditorLayoutPanel layout;
	public TopBar top;
	public BottomBar bottom;
	
	
	public EditorScreenHud() {
		var viewport = new ScreenViewport();
		var batch = new SpriteBatch();
//		var shader = new ShaderProgram(LapisShader.getVertexShader("ui"), LapisShader.getFragmentShader("ui"));
//		batch.setShader(shader);
		this.setStage(new Stage(viewport, batch));
		
		top = new TopBar();
		bottom = new BottomBar();
		properties = new PropertiesPanel();
		
		if(false) {
			getStage().addActor(top.getTable());
			getStage().addActor(bottom);
			getStage().addActor(properties);
		}
	}
	
	@Override
	public void resize(int width, int height, boolean centerCamera) {
		//Log.debug("EditorScreenHud resize [%s %s]", width, height);
		//Log.debug("EditorScreenHud screenX " + this.getStage().getViewport().getScreenX());
		super.resize(width, height, centerCamera);
		if(true) resizeThis(width, height, centerCamera);
	}
	
	private void resizeThis(int width, int height, boolean centerCamera) {
		top.resize(width, height); // la menubar est spéciale ...
		
		getStage().getActors()
			.select(a -> a instanceof Component)
			.forEach(a -> ((Component)a).resize(width, height));
	}

	/**
	 * Size of the 3d space, so window minus the hud
	 * @return
	 */
	public Rectangle getDrawingSpace() {
		int x = 0;
		int y = (int) (bottom.getHeight() + bottom.getY());
		
		int spaceWidth = (int) Gdx.graphics.getWidth();
		//spaceWidth -= 4;
		spaceWidth -= properties.getWidth(); // (properties.getChildren().get(0).getWidth()) - 4; // getViewport().getScreenWidth()
		//Log.debug("EditorScreenHud getDrawingSpace %s", properties.getWidth());
		
		int spaceHeight = (int) Gdx.graphics.getHeight();
		spaceHeight -= bottom.getHeight();
		spaceHeight -= bottom.getY();
		//spaceHeight -= top.getTable().getHeight(); //(int) (top.getTable().getY() - top.getTable().getHeight()) - y; //.getHeight();
		spaceHeight -= (Gdx.graphics.getHeight() - top.getTable().getY()); 
		//int[] space = new int[] {x + 5, y + 5, spaceWidth - 10, spaceHeight - 10};
		//System.out.println(spaceHeight);
		
		int wtf = 0; //5;
		int wtf2 = 0; //10;
		return new Rectangle(x + wtf, y + wtf, spaceWidth - wtf2, spaceHeight - wtf2);
	}

	@Override
	public String getViewId() {
		return "editor";
	}
	
	
}
