package com.souchy.randd.ebishoal.commons.lapis.screens;

import com.badlogic.gdx.Gdx;
import com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens.Screen2d;
import com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens.Screen3d;

public abstract class ComposedScreen extends Screen3d {
	
	
	@Override
	protected void createHook() {
		super.createHook();
		
		getHud().create();
        Gdx.input.setInputProcessor(getHud().getStage());
	}
	
	
	@Override
	protected void renderHook(float delta) {
		if(orderHudToFront()) {
			super.renderHook(delta);
			renderHud(delta);
		} else {
			renderHud(delta);
			super.renderHook(delta);
		}
	}
	
	protected void renderHud(float delta) {
		getHud().renderHook(delta);
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		getHud().resize(width, height);
	}

	
	public abstract Screen2d getHud();

	/**
	 * True to put the hud screen over the 3d screen
	 * False to put the 3d screen over the hud screen
	 * @return
	 */
	protected abstract boolean orderHudToFront() ;
	
}
