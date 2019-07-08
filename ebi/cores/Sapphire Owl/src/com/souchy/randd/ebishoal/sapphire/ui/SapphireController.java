package com.souchy.randd.ebishoal.sapphire.ui;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.screen.GlobalLML;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;

public class SapphireController extends CameraInputController {

	public SapphireController(Camera camera) {
		super(camera);
	}

	@Override
	public boolean scrolled(int amount) {
		var ratio = SapphireOwl.game.gfx.getCamera().viewportWidth / SapphireOwl.game.gfx.getCamera().viewportHeight;
		SapphireOwl.game.gfx.getCamera().viewportWidth += amount;
		SapphireOwl.game.gfx.getCamera().viewportHeight = SapphireOwl.game.gfx.getCamera().viewportWidth / ratio;
		if (autoUpdate) camera.update();
		return true; //super.scrolled(amount);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.SPACE) {
			SapphireOwl.game.gfx.resetCamera();
			GlobalLML.lml().reloadViews();
		}
		return super.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		return super.keyUp(keycode);
	}

	@Override
	public boolean keyTyped(char character) {
		return super.keyTyped(character);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Log.info("touch down");
		var s = (SapphireScreen) SapphireOwl.game.getScreen();
		s.getView().getStage().unfocusAll();
		s.getView().getStage().cancelTouchFocus();
		return super.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return super.touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return super.touchDragged(screenX, screenY, pointer);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return super.mouseMoved(screenX, screenY);
	}

	
}
