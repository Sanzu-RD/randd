package com.souchy.randd.ebishoal.commons.lapis.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
	public void render(float delta) {
		super.render(delta);
		getHud().renderHook(delta);
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		getHud().resize(width, height);
	}

	
	public abstract Screen2d getHud();
	
}
