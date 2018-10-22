package com.souchy.randd.ebishoal.commons.lapis.screens;

import com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens.Screen2d;
import com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens.Screen3d;

public class ComposedScreen extends Screen3d {

	public Screen2d a;

	
	//public ComposedScreen() {
	//}

	@Override
	protected void createHook() {
		super.createHook();
		a = new Screen2d();
		a.create();
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		a.renderHook(delta);
	}

	/*@Override
	protected void renderHook(float delta) {
		super.renderHook(delta);
		a.renderHook(delta);
	}*/
	
}
