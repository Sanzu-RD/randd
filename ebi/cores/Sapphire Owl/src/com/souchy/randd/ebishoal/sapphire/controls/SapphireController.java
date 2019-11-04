package com.souchy.randd.ebishoal.sapphire.controls;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.GlobalLML;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireScreen;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;

public class SapphireController extends CameraInputController {

	public Vector3 old = Vector3.Zero.cpy();
	private Vector3 target = Vector3.Zero.cpy();
	private Vector3 temp = Vector3.Zero.cpy();

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
		if(s.getView() != null) {
			s.getView().getStage().unfocusAll();
			s.getView().getStage().cancelTouchFocus();
		}
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
	//	temp.x = screenX;
	//	temp.y = screenY;
		/*
		SapphireGame.gfx.getCamera().unproject(temp);
 
		//CellInputEvent.postMove(null, target, old); //newX, newY); //listener.mouseMoved(newBox);
		if(target.x != temp.x || target.y != temp.y){ 
			//System.out.println("-------------------MOVE");
			old.set(target);
			target.set(temp);
			CellInputEvent.postExit(old); 
			CellInputEvent.postEnter(target); 
		}
		//System.out.println("moved 1 - "+"["+x+","+y+"] = "+test);
		//GridController.mouseMoved(x, y);
		return super.mouseMoved(event, x, y);
		
		*/
		return super.mouseMoved(screenX, screenY);
	}

	
}
