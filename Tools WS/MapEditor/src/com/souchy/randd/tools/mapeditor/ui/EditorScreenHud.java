package com.souchy.randd.tools.mapeditor.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;

public class EditorScreenHud extends LapisHud {

	public EditorScreenHud() {
		var viewport = new ScreenViewport();
		var batch = new SpriteBatch();
//		var shader = new ShaderProgram(LapisShader.getVertexShader("ui"), LapisShader.getFragmentShader("ui"));
//		batch.setShader(shader);
		this.setStage(new Stage(viewport, batch));
	}
	
	@Override
	public void resize(int width, int height, boolean centerCamera) {
		//Log.debug("EditorScreenHud resize [%s %s]", width, height);
		//Log.debug("EditorScreenHud screenX " + this.getStage().getViewport().getScreenX());
		super.resize(width, height, centerCamera);
		if(true) resizeThis(width, height, centerCamera);
	}
	
	private void resizeThis(int width, int height, boolean centerCamera) {
		//top.resize(width, height); // la menubar est spéciale ...
		getStage().getActors()
			.select(a -> a instanceof Component)
			.forEach(a -> ((Component)a).resize(width, height));
	}

	@Override
	public String getViewId() {
		return "editor";
	}
}
