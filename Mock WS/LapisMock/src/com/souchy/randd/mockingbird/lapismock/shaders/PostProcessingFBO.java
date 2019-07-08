package com.souchy.randd.mockingbird.lapismock.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.souchy.randd.mockingbird.lapismock.lwjgl1.LShader;

public class PostProcessingFBO {
	
	FrameBuffer fbo;
	ModelBatch batch;
	private Camera cam;
	SpriteBatch realBatch = new SpriteBatch();
	
	public PostProcessingFBO(Camera cam) {
		fbo = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		batch = new ModelBatch(new LShader.LShaderProvider()); //"shaders/test.vertex.glsl", "shaders/test.fragment.glsl");
	}
	
	private void beginPrep() {
		final int w = fbo.getWidth();
		final int h = fbo.getHeight();
		fbo.begin();
		Gdx.gl.glViewport(0, 0, w, h);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
		Gdx.gl.glScissor(1, 1, w - 2, h - 2);
	}
	
	/*public void render(ModelCache modelCache) {
		batch.render(modelCache);
	}
	public void render(RenderableProvider renderableProvider) {
		batch.render(renderableProvider);
	}
	public void render(Iterable<RenderableProvider> renderableProviders) {
		batch.render(renderableProviders);
	}*/
	
	private void endPrep() {
		Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
		fbo.end();
	}
	
	public void begin() {
		//fbo.getColorBufferTexture(); // -> pass this to the custom model batch / shader
		beginPrep();
		//batch.begin(cam);
	}
	
	public void end() {
		//batch.end();
		endPrep();
	}

	public Texture result() {
		return fbo.getColorBufferTexture();
	}
	
	
	
	public void render() {
		Texture t = new Texture(Gdx.files.absolute("G:\\Assets\\test\\glazedTerracotta.png"));
		realBatch.begin();
		realBatch.draw(t, 100, 100);
		realBatch.end();
	}
	
	
	
	
	
}
