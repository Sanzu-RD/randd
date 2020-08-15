package nodepmock.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.souchy.randd.commons.tealwaters.logging.Log;

import nodepmock.main.DrawableGenerator;

public class MockScreen extends ScreenAdapter {

	public static AbstractLmlView view;

	public ModelBatch batch = new ModelBatch();
	
	public ModelInstance highlight = DrawableGenerator.cellHighlighterModelTest();
	public Camera cam = new OrthographicCamera();
	
	@Override
	public void show() {
		Log.info("show view = " + view);
		if (view != null)
			Gdx.input.setInputProcessor(view.getStage());
	}

	@Override
	public void render(float delta) {
		Color color = getBg();
		clearScreen(color.r, color.g, color.b, color.a);
		
		batch.begin(cam);
		batch.render(highlight);
		batch.end();
		
		if (view != null) {
			view.render(delta);
		}
	}

	@Override
	public void resize(int width, int height) {
		if (view != null)
			view.getStage().getViewport().update(width, height);
	}


	public Color getBg() {
		return Color.BLACK;
	}

	public void clearScreen(float r, float g, float b, float a) {
		// Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(r, g, b, a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT
				| (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
	}
	
}
