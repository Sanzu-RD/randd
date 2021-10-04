package com.souchy.randd.mockingbird.lapismock.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.souchy.randd.mockingbird.lapismock.worlds.World;

public class MockScreen4 extends BaseScreen {

	
	
	SpriteBatch sb = new SpriteBatch();
	
	@Override
	public void setupShader() {
//		modelBatch = new ModelBatch(new LShaderProvider());
//		LShader s = (LShader) modelBatch.getShaderProvider().getShader(world.instances.get(0).getRenderable(new Renderable()));
		
	}

	@Override
	public void renderWorld(World world) {
		// TODO Auto-generated method stub
		
		FrameBuffer a = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, false);
		a.begin();
		
		a.end();
		sb.begin();
		sb.draw(a.getColorBufferTexture(), 0, 0);
		sb.end();
		
		if(true) return;
		modelBatch.begin(cam);
		modelBatch.render(world.cache, env);
		modelBatch.end();
	}

	@Override
	protected void updateLight(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void act(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderShadows(World world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderParticleEffects() {
		// TODO Auto-generated method stub
		
	}
	
}
