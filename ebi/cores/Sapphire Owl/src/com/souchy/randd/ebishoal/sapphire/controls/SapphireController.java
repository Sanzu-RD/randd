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
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.FocusManager;
import com.souchy.randd.commons.diamond.common.Action;
import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.common.Pathfinding;
import com.souchy.randd.commons.diamond.common.generic.BoolTable;
import com.souchy.randd.commons.diamond.common.generic.Vector2;
import com.souchy.randd.commons.diamond.effects.resources.ResourceGainLoss;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.Board;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.ecs.Entity;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisScreen;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.RenderOptions;
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
	private Entity targetEntity;


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
	
	private int currentActionID = Action.NONE;

	public SapphireController(Camera camera) {
		super(camera);

		addOnKeyDown(Keys.ESCAPE, () -> {
			currentActionID = Action.NONE;
			Highlight.clear();
			
			
//			if(SapphireGame.gfx.hud.parameters.getStage() == null) {
//				SapphireGame.gfx.hud.getStage().addActor(SapphireGame.gfx.hud.parameters);
//				SapphireGame.gfx.hud.parameters.window.fadeIn();
//			} else {
//			}
			if(SapphireGame.gfx.hud.parameters.isVisible()) {
				Log.info("parameters close");
				SapphireGame.gfx.hud.parameters.close();
			} else {
				Log.info("parameters open");
				SapphireGame.gfx.hud.parameters.open();
//				SapphireGame.gfx.hud.getStage().addActor(SapphireGame.gfx.hud.parameters);
//				SapphireGame.gfx.hud.parameters.setVisible(true);
//				SapphireGame.gfx.hud.parameters.window.fadeIn();
			}
			
			/*
			if(SapphireGame.gfx.hud.parameters != null) {
				SapphireGame.gfx.hud.parameters.close();
//				var t = (Table) SapphireGame.gfx.hud.parameters;
//				var w = (VisWindow) t;
//				w.fadeOut();
			} else {
				SapphireGame.gfx.hud.parameters = new Parameters();
			}
			*/
//			SapphireGame.gfx.hud.parameters.toggleVisibility()
		});
		addOnKeyDown(Keys.SPACE, () -> {
			SapphireGame.gfx.resetCamera();
			SapphireGame.gfx.hud.reload(); //.refresh();
			//GlobalLML.lml().reloadViews();
			// reset camera movement vectors just in case. 
			translation.set(0, 0, 0);
			rotationUnit.set(0, 0, 0);
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
//			creature.stats.resources.get(Resource.life).fight += 10;
//			Log.info(creature.stats.resources.get(Resource.life).toString());
//			SapphireGame.gfx.hud.reload(); // SapphireHud.refresh();
			
			var res = new HashMap<Resource, Integer>();
			res.put(Resource.life, 10);
			new ResourceGainLoss(AoeBuilders.single.get(), TargetType.full.asStat(), false, new HashMap<>(), res)
				.apply(creature, creature.getCell());
			
		});
		
		addOnKeyDown(Keys.X, () -> {
			if(targetEntity == null) return;
			var casterpos = targetEntity.get(Position.class);
			var targetpos = getCursorWorldPos(Gdx.input.getX(), Gdx.input.getY());
			var start = Moonstone.fight.board.get(casterpos.x, casterpos.y);
			var end = Moonstone.fight.board.get(targetpos.x, targetpos.y);
//			Log.format("caster pos %s, target pos %s", casterpos, targetpos);
//			Log.info("Moonstone.fight.board.cells " + Moonstone.fight.board.cells.size() + "" + Moonstone.fight.board.cells);
//			Log.info("Highlight mvm (" + start + " - " + end + ")");
			var list = Pathfinding.aStar(Moonstone.fight.board, (Creature) targetEntity, start, end);
//			Log.info("vectors " + String.join(", ", list.stream().map(v -> v.pos.toString()).collect(Collectors.toList())));
			Highlight.movement(list.stream().map(c -> (Vector2) c.pos).collect(Collectors.toList()));
		});
		addOnKeyDown(Keys.J, () -> {
			if(Highlight.isActive()) Highlight.clear();
			else Highlight.cellTypes();
		});
		addOnKeyDown(Keys.K, () -> {
			RenderOptions.renderCache = !RenderOptions.renderCache;
		});
		addOnKeyDown(Keys.B, () -> {
			RenderOptions.renderBackground = !RenderOptions.renderBackground;
		});
		addOnKeyUp(Keys.L, () -> {
			RenderOptions.renderLines = !RenderOptions.renderLines;
		});
		addOnKeyDown(Keys.N, () -> {
			var targetpos = getCursorWorldPos(Gdx.input.getX(), Gdx.input.getY());
			Log.format("target %s, %s", Gdx.input.getX(), Gdx.input.getY());
			Highlight.spell(List.of(new Vector2(targetpos.x, targetpos.y)));
		});

		addOnKeyUp(Keys.F, () -> {
			if(targetEntity == null || targetEntity instanceof Creature == false) return;
			highlightMove.accept((Creature) targetEntity);
		});
		
		addOnKeyUp(Keys.NUM_1, () -> highlightSpellRange(0));
		addOnKeyUp(Keys.NUM_2, () -> highlightSpellRange(1));
		addOnKeyUp(Keys.NUM_3, () -> highlightSpellRange(2));
		addOnKeyUp(Keys.NUM_4, () -> highlightSpellRange(3));
		addOnKeyUp(Keys.NUM_5, () -> highlightSpellRange(4));
		addOnKeyUp(Keys.NUM_6, () -> highlightSpellRange(5));
		addOnKeyUp(Keys.NUM_7, () -> highlightSpellRange(6));
		addOnKeyUp(Keys.NUM_8, () -> highlightSpellRange(7));
		
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
	

	Consumer<Creature> highlightMove = (creature) -> {

//		var creature = SapphireGame.fight.creatures.first();
		if(creature != null) {
			currentActionID = Action.MOVE;
			targetEntity = creature;
			draggedEntity = creature;
			
			var mvm = creature.stats.resources.get(Resource.move).value();
			Aoe aoe = AoeBuilders.circle.apply(mvm);
//			aoe.source = getCursorCell().pos; //new Vector2(cellpos.x, cellpos.y);
			
			var test = new ArrayList<Pathfinding.Node>();

			// TODO
//			aoe.toBoard(creature.pos).forEach(v -> {
//				var path = Pathfinding.aStar(board(), creature, creature.getCell(), board().get(v));
//				if(path.size() > 0) test.add(path.get(path.size() - 1));
//			});
//			Log.info("highlight move " + test);
			Highlight.movementPossibilities(test);
		}
	};
	
	private void highlightSpellRange(int spellindex) {
		var creature = SapphireGame.fight.creatures.first();
		var spellid = creature.spellbook.get(spellindex);
		var spell = SapphireGame.fight.spells.get(spellid);
		currentActionID = spellid;
		
		var range = spell.getRange();
//		range.source = creature.pos;
		
		Log.info("selected spell: " + spell.id + " " + spell.getClass().getSimpleName() + ", range: " + spell.stats.maxRangeRadius.value());
		

		List<Vector2> los = new ArrayList<>();
		List<Vector2> noLos = new ArrayList<>();
		
		range.toBoard(creature.pos).forEach(pos -> {
			var cell = SapphireGame.fight.board.get(pos);
			if(spell.canTarget(creature, cell)) { // SapphireGame.fight.board.checkView(creature, pos)) {
				los.add(pos);
			} else {
				noLos.add(pos);
			}
		});
		
		
		Highlight.spell(los, noLos);
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
//			Log.info("unfocus");
			FocusManager.resetFocus(s.getView().getStage());
			s.getView().getStage().unfocusAll();
			s.getView().getStage().cancelTouchFocus();
		}

		// get pos & creature
//		var cellpos = getCursorWorldPos(screenX, screenY);
		var cell = getCursorCell();
		targetEntity = null; //	targetEntity = cell;
		
		if(cell != null) {
			Creature creature = cell.getFirstCreature();
			targetEntity = creature;
			// start drag
//			if(button == Buttons.MIDDLE) {
//				Log.info("touchdown " + cell.pos + " " + creature);
//				draggedEntity = creature;
//			} else
			// toggle character sheet
			if(button == Buttons.RIGHT) {
				if(creature != null) {
					CreatureSheet.toggle(creature);
				} else {
					currentActionID = Action.NONE;
					Highlight.clear();
				}
			} else
			// action
			if (button == Buttons.LEFT) {
				Log.info("SapphireController execute action " + currentActionID);
				if(currentActionID > 0) {
					var caster = SapphireGame.fight.creatures.first();
					var spell = SapphireGame.fight.spells.get(currentActionID);
					if(spell.canCast(caster) && spell.canTarget(caster, cell)) 
						spell.cast(caster, cell);
				} else 
				if (currentActionID == Action.MOVE) {
					if(draggedEntity != null) {
						draggedEntity.get(Position.class).set(cell.pos.x, cell.pos.y);
					}
				} else
				if(currentActionID == Action.NONE) {
					if(creature != null) {
						draggedEntity = creature;
						highlightMove.accept(creature);
					}
				}
				
				currentActionID = Action.NONE;
				Highlight.clear();
			}
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
		// set dragged entity pos
		if(draggedEntity != null) {
			draggedEntity.get(Position.class).set(pos.x, pos.y);
		}
		// position label
		if(SapphireOwl.conf.functionality.showCursorPos) {
			SapphireGame.gfx.hud.cursorPos.setText(pos.x + ", " + pos.y);
			SapphireGame.gfx.hud.cursorPos.setPosition(x, Gdx.graphics.getHeight() - y);
		}
		// 3d cursor
		SapphireWorld.world.cursor.transform.setTranslation(pos.add(0.5f, 0.5f, 0f));

		if(!activateBaseCamControl) return true;
		return super.touchDragged(x, y, pointer);
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		var pos = getCursorWorldPos(x, y);
		// position label
		if(SapphireOwl.conf.functionality.showCursorPos) {
			SapphireGame.gfx.hud.cursorPos.setText(pos.x + ", " + pos.y);
			SapphireGame.gfx.hud.cursorPos.setPosition(x, Gdx.graphics.getHeight() - y);
		}
		// 3d cursor
		SapphireWorld.world.cursor.transform.setTranslation(pos.add(0.5f, 0.5f, 0f));

		if(!activateBaseCamControl) return true;
		return super.mouseMoved(x, y);
	}

	private Vector3 getCursorWorldPos(int x, int y) {
		var floorHeight = 1f;
		Ray ray = camera.getPickRay(x, y);
		var distance = (floorHeight - ray.origin.z) / ray.direction.z;
		Vector3 v = new Vector3();
		v.set(ray.direction).scl(distance).add(ray.origin);
		v.x = (float) Math.floor(v.x) + 0f;
		v.y = (float) Math.floor(v.y) + 0f;
		v.z = floorHeight;
		return v;
	}

	private Vector3 getCursorWorldPos() {
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		return getCursorWorldPos(x, y);
	}
	private Vector2 getCursorWorldPos2() {
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		var v = getCursorWorldPos(x, y);
		return new Vector2(v.x, v.y);
	}


	private Cell getCursorCell() {
		var pos = getCursorWorldPos();
		return SapphireGame.fight.board.get(pos.x, pos.y);
	}

	private Fight fight() {
		return SapphireGame.fight;
	}
	private Board board() {
		return SapphireGame.fight.board;
	}
	
}
