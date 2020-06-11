package com.souchy.randd.ebishoal.sapphire.controls;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import static com.badlogic.gdx.Input.Keys.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder.VertexInfo;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.kotcrab.vis.ui.FocusManager;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireHud;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireScreen;
import com.souchy.randd.ebishoal.sapphire.main.SapphireEntitySystem;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;
import com.souchy.randd.ebishoal.sapphire.main.SapphireWorld;
import com.souchy.randd.ebishoal.sapphire.ux.CreatureSheet;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireWidget.LmlWidgets;

import data.new1.ecs.Entity;
import gamemechanics.components.Position;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;
import gamemechanics.statics.stats.properties.Resource;

public class SapphireController extends CameraInputController {

	public Vector3 old = Vector3.Zero.cpy();
	private Vector3 target = Vector3.Zero.cpy();
	private Vector3 temp = Vector3.Zero.cpy();
	
	public boolean activateBaseCamControl = false;

	private float translationSpeed = 40;
	private float rotationSpeed = 60;
	private Vector3 translation = Vector3.Zero.cpy();
	private Vector3 rotation = Vector3.Zero.cpy();
	
	private Vector3 axisZ = new Vector3(0, 0, 1);
	private Vector3 axisXY = new Vector3(-1, 1, 0).nor();
	
	private Entity draggedEntity;

	public SapphireController(Camera camera) {
		super(camera);
	}

	/**
	 * Called on every frame for overtime control
	 * @param delta
	 */
	public void act(float delta) {
		Vector3 up  = camera.up;
		Vector3 dir = camera.direction;
		Vector3 pos = camera.position;
		
		translation.set(0, 0, 0);
		rotation.set(0, 0, 0);
		
		if(Gdx.input.isKeyPressed(ALT_LEFT)) {
			// Translation XY
			if(Gdx.input.isKeyPressed(W)) translation.add( up.x,  up.y, 0);
			if(Gdx.input.isKeyPressed(S)) translation.add(-up.x, -up.y, 0);  
			if(Gdx.input.isKeyPressed(A)) translation.add(-up.y,  up.x, 0); 
			if(Gdx.input.isKeyPressed(D)) translation.add( up.y, -up.x, 0); 
		} else {
			// Rotation X
			if(Gdx.input.isKeyPressed(W)) rotation.add(-up.y, up.x, 0); // look up
			if(Gdx.input.isKeyPressed(S)) rotation.add( up.y, -up.x, 0); // look down
			// Rotation Z
			if(Gdx.input.isKeyPressed(A)) rotation.add(0, 0, -1f); // look left 
			if(Gdx.input.isKeyPressed(D)) rotation.add(0, 0,  1f); // look right
		}
		// Zoom
		if(Gdx.input.isKeyPressed(CONTROL_LEFT) || Gdx.input.isKeyPressed(Q)) scrolledFloat(0.2f);
		if(Gdx.input.isKeyPressed(SHIFT_LEFT) || Gdx.input.isKeyPressed(E)) scrolledFloat(-0.2f);
		
		// p = le point qu'on regarde avec la caméra
		float scl = Math.abs(camera.position.z / dir.z);
		Vector3 p = new Vector3(dir.x * scl + pos.x, dir.y * scl + pos.y, 0);
		
		float distance = translationSpeed * delta;
		Vector3 movement = translation.scl(distance);
		
		float angle = rotationSpeed * delta;
		
		camera.position.add(movement);
		camera.rotateAround(p, rotation, angle);
		
		if(autoUpdate) camera.update();
	}

	@Override
	public boolean scrolled(int amount) {
		scrolledFloat(amount);
		if(!activateBaseCamControl) return true;
		return super.scrolled(amount);
	}
	private void scrolledFloat(float amount) {
		var ratio = SapphireGame.gfx.getCamera().viewportWidth / SapphireGame.gfx.getCamera().viewportHeight;
		SapphireGame.gfx.getCamera().viewportWidth += amount;
		SapphireGame.gfx.getCamera().viewportHeight = SapphireGame.gfx.getCamera().viewportWidth / ratio;
		if (autoUpdate) camera.update();
	}

	/**
	 * one-hit keydown events
	 */
	@SuppressWarnings("preview")
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.SPACE) {
			SapphireGame.gfx.resetCamera();
			SapphireHud.refresh();
			//GlobalLML.lml().reloadViews();
		}
		if(keycode == Keys.R) {
			SapphireGame.gfx.resetCamera();
		}
		if(keycode == Keys.T) {
			SapphireGame.gfx.topView();
		}
		if(keycode == Keys.E) {
			SapphireHud.testCreatureSheet();
		}
		if(keycode == Keys.V) {
			SapphireGame.fight.teamA.get(0).stats.resources.get(Resource.life).fight += 10;
			Log.info(SapphireGame.fight.teamA.get(0).stats.resources.get(Resource.life).toString());
			SapphireHud.refresh();
		}
		
		switch(keycode) {
			case RIGHT 	-> camera.rotate(45, 0, 0, 1);
			case LEFT 	-> camera.rotate(45, 0, 0, -1);
			case UP 	-> camera.rotate(45, -camera.up.y, camera.up.x, 0); 
			case DOWN 	-> camera.rotate(45, camera.up.y, -camera.up.x, 0); 
		}
		
		if(!activateBaseCamControl) return true;
		return super.keyDown(keycode);
	}
	

	@Override
	public boolean keyUp(int keycode) {
		if(!activateBaseCamControl) return true;
		return super.keyUp(keycode);
	}

	@Override
	public boolean keyTyped(char character) {
		if(!activateBaseCamControl) return true;
		return super.keyTyped(character);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// remove focus from UI
		var s = (SapphireScreen) SapphireOwl.game.getScreen();
		if(s.getView() != null) {
			Log.info("unfocus");
			FocusManager.resetFocus(s.getView().getStage());
			s.getView().getStage().unfocusAll();
			s.getView().getStage().cancelTouchFocus();
		}

		// get pos & creature 
		var cellpos = getCursorWorldPos(screenX, screenY);
		Creature creature = null;
		for (var e : SapphireEntitySystem.family) {
			var epos = e.get(Position.class);
			if(epos == null) continue;
			if(cellpos.x == epos.x && cellpos.y == epos.y) {
				creature = (Creature) e;
//				Log.info("found creature [" + epos.x + "," + epos.y+"] " + creature.model.id());
				break;
			}
		}
//		var cell = SapphireGame.fight.board.cells.get((int) cellpos.x, (int) cellpos.y);
//		cell.creatures.get(0);
		
		// start drag
		if(button == Buttons.LEFT) {
			Log.info("touchdown " + cellpos);
			draggedEntity = creature;
		} else
		// toggle character sheet
		if(button == Buttons.RIGHT && creature != null) {
			CreatureSheet.toggle(creature);
		}
		
		if(!activateBaseCamControl) return true;
		return super.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		draggedEntity = null;
		if(!activateBaseCamControl) return true;
		return super.touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		var pos = getCursorWorldPos(x, y);
		if(draggedEntity != null) {
			var epos = draggedEntity.get(Position.class).set(pos.x, pos.y);
			Log.info("dragged entity " + epos);
		}
		SapphireWorld.world.cursor.transform.setTranslation(pos.sub(0.5f, 0.5f, 0f));
		
		if(!activateBaseCamControl) return true;
		return super.touchDragged(x, y, pointer);
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		SapphireWorld.world.cursor.transform.setTranslation(getCursorWorldPos(x, y).sub(0.5f, 0.5f, 0f));
		
		if(!activateBaseCamControl) return true;
		return super.mouseMoved(x, y);
	}
	
	private Vector3 getCursorWorldPos(int x, int y) {
		var floorHeight = 1f;
		Ray ray = camera.getPickRay(x, y);
		var distance = (floorHeight - ray.origin.z) / ray.direction.z;
		Vector3 v = new Vector3();
		v.set(ray.direction).scl(distance).add(ray.origin);
//		Log.info("" + v);
		v.x = (float) Math.floor(v.x) + 1f;
		v.y = (float) Math.floor(v.y) + 1f;
		v.z = floorHeight;
		return v;
	}
	

}
