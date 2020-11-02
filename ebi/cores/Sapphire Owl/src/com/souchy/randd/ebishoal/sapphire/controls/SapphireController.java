package com.souchy.randd.ebishoal.sapphire.controls;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.ALT_LEFT;
import static com.badlogic.gdx.Input.Keys.CONTROL_LEFT;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.E;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.Q;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.SHIFT_LEFT;
import static com.badlogic.gdx.Input.Keys.UP;
import static com.badlogic.gdx.Input.Keys.W;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.FocusManager;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.ecs.Entity;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.sapphire.gfx.Highlight;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireScreen;
import com.souchy.randd.ebishoal.sapphire.main.SapphireEntitySystem;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;
import com.souchy.randd.ebishoal.sapphire.main.SapphireWorld;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireHud;
import com.souchy.randd.ebishoal.sapphire.ux.components.CreatureSheet;
import com.souchy.randd.ebishoal.sapphire.ux.components.Parameters;
import com.souchy.randd.moonstone.commons.packets.c2s.PassTurn;
import com.souchy.randd.moonstone.white.Moonstone;

public class SapphireController extends CameraInputController {

//	public Vector3 old = Vector3.Zero.cpy();
//	private Vector3 target = Vector3.Zero.cpy();
//	private Vector3 temp = Vector3.Zero.cpy();

	public boolean activateBaseCamControl = false;

	private float translationSpeed = 10;
	private float rotationSpeed = 60;
	private Vector3 translation = Vector3.Zero.cpy();
	private Vector3 rotationUnit = Vector3.Zero.cpy();
	private Vector3 rotation = Vector3.Zero.cpy();
	private float zoom = 0;

//	private Vector3 axisZ = new Vector3(0, 0, 1);
//	private Vector3 axisXY = new Vector3(-1, 1, 0).nor();

	private Entity draggedEntity;


	/** <int keycode, lamba action> */
	public Map<Integer, Lambda> onKeyDown = new HashMap<>();
	/** <int keycode, lamba action> */
	public Map<Integer, Lambda> onKeyUp = new HashMap<>();


	public void addOnKeyDown(int keycode, Lambda action) {
		onKeyDown.put(keycode, action);
	}
	public void addOnKeyUp(int keycode, Lambda action) {
		onKeyUp.put(keycode, action);
	}

	public SapphireController(Camera camera) {
		super(camera);

		addOnKeyDown(Keys.ESCAPE, () -> {
			if(SapphireGame.gfx.hud.parameters != null) {
				SapphireGame.gfx.hud.parameters.close();
//				var t = (Table) SapphireGame.gfx.hud.parameters;
//				var w = (VisWindow) t;
//				w.fadeOut();
			} else {
				SapphireGame.gfx.hud.parameters = new Parameters();
			}
//			SapphireGame.gfx.hud.parameters.toggleVisibility()
		});
		addOnKeyDown(Keys.SPACE, () -> {
			SapphireGame.gfx.resetCamera();
			SapphireGame.gfx.hud.reload(); //.refresh();
			//GlobalLML.lml().reloadViews();
		});

		addOnKeyDown(Keys.C, () -> {
			Moonstone.writes(new PassTurn());
		});

		addOnKeyDown(RIGHT, () -> camera.rotate(45, 0, 0, 1));
		addOnKeyDown(LEFT, () -> camera.rotate(45, 0, 0, -1));
		addOnKeyDown(UP, () -> camera.rotate(45, -camera.up.y, camera.up.x, 0));
		addOnKeyDown(DOWN, () -> camera.rotate(45, camera.up.y, -camera.up.x, 0));

		addOnKeyDown(Keys.R, () -> SapphireGame.gfx.resetCamera());
		addOnKeyDown(Keys.T, () -> SapphireGame.gfx.topView());
		addOnKeyDown(Keys.P, () -> SapphireGame.gfx.startPfx());
		addOnKeyDown(Keys.M, SapphireGame.music::togglePlayPause);

		addOnKeyDown(Keys.F3, () -> SapphireGame.gfx.hud.getStage().setDebugAll(!SapphireGame.gfx.hud.getStage().isDebugAll()));

		addOnKeyDown(Keys.V, () -> {
//			var creature = SapphireGame.fight.teamA.get(0)
			var creature = SapphireGame.fight.creatures.first();
			creature.stats.resources.get(Resource.life).fight += 10;
			Log.info(creature.stats.resources.get(Resource.life).toString());
			SapphireGame.gfx.hud.reload(); // SapphireHud.refresh();
		});


		addOnKeyDown(Keys.W, () -> {
			Vector3 up  = camera.up;
			if(Gdx.input.isKeyPressed(ALT_LEFT)) translation.add( up.x,  up.y, 0);
			else rotationUnit.add(-1, 1, 0); // look up
		});
		addOnKeyDown(Keys.S, () -> {
			Vector3 up  = camera.up;
			if(Gdx.input.isKeyPressed(ALT_LEFT)) translation.add(-up.x, -up.y, 0);
			else rotationUnit.add( 1, -1, 0); // look down
		});
		addOnKeyDown(Keys.A, () -> {
			Vector3 up  = camera.up;
			if(Gdx.input.isKeyPressed(ALT_LEFT)) translation.add(-up.y,  up.x, 0);
			else rotationUnit.add(0, 0, -1f); // look left
		});
		addOnKeyDown(Keys.D, () -> {
			Vector3 up  = camera.up;
			if(Gdx.input.isKeyPressed(ALT_LEFT)) translation.add( up.y, -up.x, 0);
			else rotationUnit.add(0, 0,  1f); // look right
		});
		addOnKeyDown(Keys.Q, () -> zoom += 0.2f);
		addOnKeyDown(Keys.E, () -> zoom += -0.2f);

		
		addOnKeyUp(Keys.W, () -> {
			Vector3 up  = camera.up;
			if(Gdx.input.isKeyPressed(ALT_LEFT)) translation.add( -up.x,  -up.y, 0);
			else rotationUnit.add( 1, -1, 0); // look up
		});
		addOnKeyUp(Keys.S, () -> {
			Vector3 up  = camera.up;
			if(Gdx.input.isKeyPressed(ALT_LEFT)) translation.add( up.x,  up.y, 0);
			else rotationUnit.add(-1,  1, 0); // look down
		});
		addOnKeyUp(Keys.A, () -> {
			Vector3 up  = camera.up;
			if(Gdx.input.isKeyPressed(ALT_LEFT)) translation.add( up.y, -up.x, 0);
			else rotationUnit.add(0, 0,  1f); // look left
		});
		addOnKeyUp(Keys.D, () -> {
			Vector3 up  = camera.up;
			if(Gdx.input.isKeyPressed(ALT_LEFT)) translation.add(-up.y,  up.x, 0);
			else rotationUnit.add(0, 0, -1f); // look right
		});
		
		addOnKeyUp(Keys.Q, () -> zoom += -0.2f);
		addOnKeyUp(Keys.E, () -> zoom += 0.2f);
		
	}

	/**
	 * Called on every frame for overtime control
	 * @param delta
	 */
	public void act(float delta) {
		Vector3 up  = camera.up;
		Vector3 dir = camera.direction;
		Vector3 pos = camera.position;

		/*
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
		*/

		scrolledFloat(zoom);

		// p = le point qu'on regarde avec la caméra
		float scl = Math.abs(camera.position.z / dir.z);
		Vector3 p = new Vector3(dir.x * scl + pos.x, dir.y * scl + pos.y, 0);

		float distance = translationSpeed * delta;
		Vector3 movement = translation.cpy().scl(distance);

		float angle = rotationSpeed * delta;
		rotation.x = rotationUnit.x * up.y;
		rotation.y = rotationUnit.y * up.x;
		rotation.z = rotationUnit.z;

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
		var viewport = SapphireGame.gfx.getViewport();
		var cam = SapphireGame.gfx.getCamera();
//		var ratio = viewport.getWorldWidth() / viewport.getWorldHeight();
//		viewport.setWorldWidth(viewport.getWorldWidth() + amount);
//		viewport.setWorldHeight( viewport.getWorldWidth() / ratio);
//		Log.info("viewport 1 : " + viewport.getWorldWidth() + "; " + viewport.getWorldHeight());
//		Log.info("cam 1 : " + cam.viewportWidth + "; " + cam.viewportHeight);

		var ratio = cam.viewportWidth / cam.viewportHeight;
		cam.viewportWidth += amount;
		cam.viewportHeight = cam.viewportWidth / ratio;
		if (autoUpdate)
			camera.update();

//		Log.info("viewport 2 : " + viewport.getWorldWidth() + "; " + viewport.getWorldHeight());
//		Log.info("cam 2 : " + cam.viewportWidth + "; " + cam.viewportHeight);

		// resize le viewport de la shadowlight pour sa shadowmap
		if(SapphireGame.gfx.getShadowLight() != null)
			SapphireGame.gfx.getShadowLight().zoom(cam.viewportWidth, cam.viewportHeight);
	}

	/**
	 * one-hit keydown events
	 */
	@SuppressWarnings("preview")
	@Override
	public boolean keyDown(int keycode) {
		var lambda = onKeyDown.get(keycode);
		if(lambda != null) {
			lambda.call();
			return true;
		}

		if(!activateBaseCamControl) return true;
		return super.keyDown(keycode);
	}


	@Override
	public boolean keyUp(int keycode) {
		var lambda = onKeyUp.get(keycode);
		if(lambda != null) {
			lambda.call();
			return true;
		}

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
//		for (var e : SapphireEntitySystem.family) {
//			var epos = e.get(Position.class);
//			if(epos == null) continue;
//			if(cellpos.x == epos.x && cellpos.y == epos.y) {
//				creature = (Creature) e;
////				Log.info("found creature [" + epos.x + "," + epos.y+"] " + creature.model.id());
//				break;
//			}
//		}
		creature = SapphireGame.fight.creatures.first(e -> {
			var epos = e.get(Position.class);
			if(epos == null) return false;
			return (cellpos.x == epos.x && cellpos.y == epos.y);
		});
//		var cell = SapphireGame.fight.board.cells.get((int) cellpos.x, (int) cellpos.y);
//		cell.creatures.get(0);

		// start drag
		if(button == Buttons.LEFT) {
			Log.info("touchdown " + cellpos);
			draggedEntity = creature;
			Highlight.movement(new ArrayList<>() {{
				add(new Vector3(0, 0, 0));
				add(new Vector3(5, 5, 0));
				add(new Vector3(5, 6, 0));
				add(new Vector3(6, 7, 0));
				add(new Vector3(5, 8, 0));
				add(new Vector3(5, 9, 0));
			}});
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
//			var epos = draggedEntity.get(Position.class).set(pos.x, pos.y);
			//Log.info("dragged entity " + epos);
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
