package com.souchy.randd.ebishoal.commons.lapis.screens;

import com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens.Screen2d;
import com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens.Screen3d;

public class ComposedScreen extends Screen3d {

	
	public Screen2d a;
	
	
	public ComposedScreen() {
		a = new Screen2d();
		a.create();
	}

	@Override
	protected void createHook() {
		super.createHook();
	}


	@Override
	protected void renderHook(float delta) {
		super.renderHook(delta);
		a.renderHook(delta);
	}
	
}
