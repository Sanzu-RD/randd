package com.souchy.randd.tools.uieditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisScreen;
import com.souchy.randd.ebishoal.sapphire.controls.SapphireController;
import com.souchy.randd.tools.uieditor.ui.Hud;

public class UIEditorScreen extends LapisScreen {

	public UIEditorScreen() {
		this.getLining().lineMap.clear();
	}
	
	@Override
	public Hud createUI() {
		return new Hud();
	}
	
	@Override
	protected void act(float delta) {
		Gdx.graphics.setTitle("UI Editor FPS : " + Gdx.graphics.getFramesPerSecond());
	}
	
}
