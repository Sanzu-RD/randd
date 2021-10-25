package com.souchy.randd.ebishoal.sapphire.controls;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.google.common.collect.HashBasedTable;
import com.kotcrab.vis.ui.FocusManager;
import com.souchy.randd.commons.diamond.common.Action;
import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.common.Pathfinding;
import com.souchy.randd.commons.diamond.common.generic.Vector2;
import com.souchy.randd.commons.diamond.effects.displacement.Move;
import com.souchy.randd.commons.diamond.effects.resources.ResourceGainLoss;
import com.souchy.randd.commons.diamond.models.Board;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.ecs.Entity;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.RenderOptions;
import com.souchy.randd.ebishoal.sapphire.controls.KeyCombination.KeyCombinationArray;
import com.souchy.randd.ebishoal.sapphire.gfx.Highlight;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireScreen;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;
import com.souchy.randd.ebishoal.sapphire.main.SapphireWorld;
import com.souchy.randd.ebishoal.sapphire.ux.components.sheets.CreatureSheet;
import com.souchy.randd.jade.Constants;
import com.souchy.randd.moonstone.commons.packets.c2s.FightAction;
import com.souchy.randd.moonstone.commons.packets.c2s.PassTurn;
import com.souchy.randd.moonstone.white.Moonstone;

public class SapphireController extends CameraInputController {

	public boolean activateBaseCamControl = false;

	private float translationSpeed = 10;
	private float rotationSpeed = 60;
	private Vector3 translation = Vector3.Zero.cpy();
	private Vector3 rotationUnit = Vector3.Zero.cpy();
	private Vector3 rotation = Vector3.Zero.cpy();
	private float zoom = 0;

	private Entity draggedEntity;
	private Entity targetEntity;
	
	private int currentActionID = Action.NONE;

	public SapphireController(Camera camera) {
		super(camera);
		initCombos();
	}

	public com.google.common.collect.Table<Integer, Integer, Lambda> keyCombos = HashBasedTable.create();
	public LinkedList<Integer> keyBuffer = new LinkedList<>();
	
	public void initCombos() {
		keyCombos.clear();

		putCombo(SapphireOwl.conf.shortcut.cancel, () -> {
			currentActionID = Action.NONE;
			Highlight.clearAll();
			
			
			if(SapphireGame.gfx.hud != null) {
//				if(SapphireGame.gfx.hud.parameters.getStage() == null) {
//					SapphireGame.gfx.hud.getStage().addActor(SapphireGame.gfx.hud.parameters);
//					SapphireGame.gfx.hud.parameters.window.fadeIn();
//				} else {
//				}
				if(SapphireGame.gfx.hud.parameters.isVisible()) {
					Log.info("parameters close");
					SapphireGame.gfx.hud.parameters.close();
				} else {
					Log.info("parameters open");
					SapphireGame.gfx.hud.parameters.open();
//					SapphireGame.gfx.hud.getStage().addActor(SapphireGame.gfx.hud.parameters);
//					SapphireGame.gfx.hud.parameters.setVisible(true);
//					SapphireGame.gfx.hud.parameters.window.fadeIn();
				}
				/*
				if(SapphireGame.gfx.hud.parameters != null) {
					SapphireGame.gfx.hud.parameters.close();
//					var t = (Table) SapphireGame.gfx.hud.parameters;
//					var w = (VisWindow) t;
//					w.fadeOut();
				} else {
					SapphireGame.gfx.hud.parameters = new Parameters();
				}
				*/
//				SapphireGame.gfx.hud.parameters.toggleVisibility()
			}
			
		});
		putCombo(SapphireOwl.conf.shortcut.refreshui, () -> {
			SapphireGame.gfx.resetCamera();
			if(SapphireGame.gfx.hud != null) 
				SapphireGame.gfx.hud.reload(); //.refresh();
			//GlobalLML.lml().reloadViews();
			// reset camera movement vectors just in case. 
			translation.set(0, 0, 0);
			rotationUnit.set(0, 0, 0);
		});
		
		putCombo(SapphireOwl.conf.shortcut.passTurn, () -> Moonstone.writes(new PassTurn()));
		
		putCombo(SapphireOwl.conf.shortcut.rotateUpX, () -> camera.rotate(45, -camera.up.y, camera.up.x, 0));
		putCombo(SapphireOwl.conf.shortcut.rotateDownX, () -> camera.rotate(45, camera.up.y, -camera.up.x, 0));
		putCombo(SapphireOwl.conf.shortcut.rotateRightX, () -> camera.rotate(45, 0, 0, 1));
		putCombo(SapphireOwl.conf.shortcut.rotateLeftX, () -> camera.rotate(45, 0, 0, -1));

		putCombo(SapphireOwl.conf.shortcut.camReset, () -> SapphireGame.gfx.resetCamera());
		putCombo(SapphireOwl.conf.shortcut.camTopView, () -> SapphireGame.gfx.topView());
		putCombo(SapphireOwl.conf.shortcut.musicToggle, () -> SapphireGame.music.togglePlayPause());
		putCombo(SapphireOwl.conf.shortcut.debugUI, () -> SapphireGame.gfx.hud.getStage().setDebugAll(!SapphireGame.gfx.hud.getStage().isDebugAll()));
		putCombo(SapphireOwl.conf.shortcut.testGainLife, () -> {
//			var creature = SapphireGame.fight.teamA.get(0)
			var creature = SapphireGame.getPlayingCreature(); //.fight.creatures.first();
//			creature.stats.resources.get(Resource.life).fight += 10;
//			Log.info(creature.stats.resources.get(Resource.life).toString());
//			SapphireGame.gfx.hud.reload(); // SapphireHud.refresh();
			
			var res = Map.of(Resource.life, 10); //new HashMap<Resource, Integer>(); res.put(Resource.life, 10);
			new ResourceGainLoss(AoeBuilders.single.get(), TargetType.full.asStat(), false, Map.of(), res)
				.apply(creature, creature.getCell());
			
		});

//		addOnKeyUp(Keys.F, () -> {
////			if(targetEntity == null || targetEntity instanceof Creature == false) return;
////			highlightMove.accept((Creature) targetEntity);
//			highlightMove.accept((Creature) SapphireGame.getPlayingCreature());
//		});
		
		putCombo(SapphireOwl.conf.shortcut.testMove, () -> {
			if(targetEntity == null) targetEntity = SapphireGame.playing;
			var c = targetEntity;
			if(c == null) {
				Log.error("SapphireController key X : move c null");
				return;
			}
			this.currentActionID = Action.MOVE;
			var casterpos = c.get(Position.class);
			var targetpos = getCursorCell();
			var start = Moonstone.fight.board.get(casterpos.x, casterpos.y);
			var list = Pathfinding.aStar(Moonstone.fight.board, (Creature) c, start, targetpos);
			Highlight.movement(list.stream().map(c1 -> (Vector2) c1.pos).collect(Collectors.toList()));
			
//			if(targetEntity == null) return;
//			var casterpos = targetEntity.get(Position.class);
//			var targetpos = getCursorWorldPos(Gdx.input.getX(), Gdx.input.getY());
//			var start = Moonstone.fight.board.get(casterpos.x, casterpos.y);
//			var end = Moonstone.fight.board.get(targetpos.x, targetpos.y);
//			Log.format("caster pos %s, target pos %s", casterpos, targetpos);
//			Log.info("Moonstone.fight.board.cells " + Moonstone.fight.board.cells.size() + "" + Moonstone.fight.board.cells);
//			Log.info("Highlight mvm (" + start + " - " + end + ")");
//			var list = Pathfinding.aStar(Moonstone.fight.board, (Creature) targetEntity, start, end);
//			Log.info("vectors " + String.join(", ", list.stream().map(v -> v.pos.toString()).collect(Collectors.toList())));
//			Highlight.movement(list.stream().map(c -> (Vector2) c.pos).collect(Collectors.toList()));
		});
		
		putCombo(SapphireOwl.conf.shortcut.highlightCells, () -> {
			if(Highlight.isActive()) Highlight.clear();
			else Highlight.cellTypes();
		});
		putCombo(SapphireOwl.conf.shortcut.renderCache, () -> RenderOptions.renderCache = !RenderOptions.renderCache);
		putCombo(SapphireOwl.conf.shortcut.renderBackground, () -> RenderOptions.renderBackground = !RenderOptions.renderBackground);
		putCombo(SapphireOwl.conf.shortcut.renderLines, () -> RenderOptions.renderLines = !RenderOptions.renderLines);
		
		putCombo(SapphireOwl.conf.shortcut.spell0, () -> highlightSpellRange(0));
		putCombo(SapphireOwl.conf.shortcut.spell1, () -> highlightSpellRange(1));
		putCombo(SapphireOwl.conf.shortcut.spell2, () -> highlightSpellRange(2));
		putCombo(SapphireOwl.conf.shortcut.spell3, () -> highlightSpellRange(3));
		putCombo(SapphireOwl.conf.shortcut.spell4, () -> highlightSpellRange(4));
		putCombo(SapphireOwl.conf.shortcut.spell5, () -> highlightSpellRange(5));
		putCombo(SapphireOwl.conf.shortcut.spell6, () -> highlightSpellRange(6));
		putCombo(SapphireOwl.conf.shortcut.spell7, () -> highlightSpellRange(7));
		putCombo(SapphireOwl.conf.shortcut.spell8, () -> highlightSpellRange(8));
		putCombo(SapphireOwl.conf.shortcut.spell9, () -> highlightSpellRange(9));
	}
	private void putCombo(KeyCombinationArray arr, Lambda l) {
		for(var c : arr.combinations) putCombo(c, l);
	}
	private void putCombo(KeyCombination c, Lambda l) {
		keyCombos.put(c.key1, c.key2, l);
		keyCombos.put(c.key2, c.key1, l);
	}
	
	

	Consumer<Creature> highlightMove = (creature) -> {

//		var creature = SapphireGame.fight.creatures.first();
		if(creature != null) {
//			currentActionID = Action.MOVE;
//			targetEntity = creature;
//			draggedEntity = creature;
			
			var mvm = creature.stats.resources.get(Resource.move).value();
			Aoe aoe = AoeBuilders.circle.apply(mvm);
//			aoe.source = getCursorCell().pos; //new Vector2(cellpos.x, cellpos.y);
			
			var test = aoe.toBoard(creature.pos); //new ArrayList<Pathfinding.Node>();
			
			// TODO
//			aoe.toBoard(creature.pos).forEach(v -> {
//				var path = Pathfinding.aStar(board(), creature, creature.getCell(), board().get(v));
//				if(path.size() > 0) test.add(path.get(path.size() - 1));
//			});
			//Log.info("highlight move " + test);
			Highlight.movementPossibilities(test);
		}
	};
	
	private void highlightSpellRange(int spellindex) {
		var creature = SapphireGame.getPlayingCreature(); //.fight.creatures.first();
		if(spellindex >= creature.spellbook.size()) return;
		
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
		
		
		Highlight.spellRange(los, noLos);
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
		rotationUnit.set(0, 0, 0);

		// Translation XY
		if(SapphireOwl.conf.shortcut.translateUpFree.isPressed()) translation.add( up.x,  up.y, 0);
		if(SapphireOwl.conf.shortcut.translateDownFree.isPressed()) translation.add(-up.x, -up.y, 0);
		if(SapphireOwl.conf.shortcut.translateLeftFree.isPressed()) translation.add(-up.y,  up.x, 0);
		if(SapphireOwl.conf.shortcut.translateRightFree.isPressed()) translation.add( up.y, -up.x, 0);
		// Rotation XZ only if there's no translation (sorry)
		if(translation.isZero()) {
			if(SapphireOwl.conf.shortcut.rotateUpFree.isPressed()) rotationUnit.add(-1,  1, 0); // look up
			if(SapphireOwl.conf.shortcut.rotateDownFree.isPressed()) rotationUnit.add( 1, -1, 0); // look down
			if(SapphireOwl.conf.shortcut.rotateLeftFree.isPressed()) rotationUnit.add(0, 0, -1f); // look left
			if(SapphireOwl.conf.shortcut.rotateRightFree.isPressed()) rotationUnit.add(0, 0,  1f); // look right
		}
		
		// Zoom
		if(SapphireOwl.conf.shortcut.zoomInFree.isPressed()) 
			scrolledFloat(0.2f);
		if(SapphireOwl.conf.shortcut.zoomOutFree.isPressed())
			scrolledFloat(-0.2f);
		
		
		// keep it a unit vector even if we move in two directions at the same time
		rotationUnit.nor();
		
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
		//var viewport = SapphireGame.gfx.getViewport();
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
		// CTRL+ALT+H : hard code shortcut to init/reset key combos
		if(keycode == Keys.H && Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Keys.ALT_LEFT)) 
			initCombos();
		
		// if the buffer is already full, remove the head
		if(keyBuffer.size() == 2) keyBuffer.poll();
		
		// add the key to the buffer
		keyBuffer.offer(keycode); 
		
		// get the lambda associated to the key combo then call it if not null
		var key1 = keyBuffer.peekFirst();
		var key2 = keyBuffer.peekLast();
		Lambda c = keyCombos.get(key1, key2);
		//Log.info("keyDown (%s) (%s, %s) = %s", keycode, key1, key2, c);
		if(c != null) c.call();
		//else Log.error("keyDown (%s) (%s, %s) = %s", keycode, key1, key2, c); // not an error because of camera translation/rotation/zoom in act()
		
		if(!activateBaseCamControl) return true;
		return super.keyDown(keycode);
	}


	@Override
	public boolean keyUp(int keycode) {
		keyBuffer.removeFirstOccurrence(keycode);

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
		var s = SapphireGame.gfx; // (SapphireScreen) SapphireOwl.game.getScreen();
		if(s.getView() != null) {
//			Log.info("unfocus");
			FocusManager.resetFocus(s.getView().getStage());
			s.getView().getStage().unfocusAll();
			s.getView().getStage().cancelTouchFocus();
		}

		// get pos & creature
//		var cellpos = getCursorWorldPos(screenX, screenY);
		var cell = getCursorCell();
		//targetEntity = null; //	targetEntity = cell;
		
		if(cell != null) {
			Creature creature = cell.getFirstCreature();
//			if(creature != null && currentActionID == Action.MOVE) {
//				currentActionID = Action.NONE;
//			}
//			targetEntity = creature;
			// start drag
//			if(button == Buttons.MIDDLE) {
//				Log.info("touchdown " + cell.pos + " " + creature);
//				draggedEntity = creature;
//			} else
			// toggle character sheet
			if(button == SapphireOwl.conf.shortcut.buttonInfo) { //Buttons.RIGHT) {
				if(SapphireOwl.conf.shortcut.targetCellModifier.isPressed()) { //Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
					Commands.cell("", cell.id);
				} else 
				if(creature != null) {
					CreatureSheet.toggle(creature);
				} else {
					currentActionID = Action.NONE;
					Highlight.clearAll();
				}
			} else
			// action
			if (button == SapphireOwl.conf.shortcut.buttonAction) { //Buttons.LEFT) {
				Log.info("SapphireController execute action " + currentActionID);
				if(currentActionID > 0) {
					var caster = SapphireGame.getPlayingCreature(); //.fight.creatures.first();
					var spell = SapphireGame.fight.spells.get(currentActionID);
					if(SapphireGame.fight.timeline.current() == caster.id) {
						// if(spell.canCast(caster) && spell.canTarget(caster, cell))
						// spell.cast(caster, cell);
						Moonstone.writes(
								new FightAction(caster.id, SapphireGame.fight.timeline.turn(), currentActionID, (int) cell.pos.x, (int) cell.pos.y));
					}
				} else 
				if (currentActionID == Action.MOVE) {
					if(targetEntity != null) { 
						//targetEntity.get(Position.class).set(cell.pos.x, cell.pos.y);
						var move = new Move(AoeBuilders.single.get(), TargetType.empty.asStat());
						move.apply((Creature) targetEntity, cell);
					} else
					if(draggedEntity != null) {
						draggedEntity.get(Position.class).set(cell.pos.x, cell.pos.y);
					} else {
						if(SapphireGame.playing != null) SapphireGame.playing.get(Position.class).set(cell.pos.x, cell.pos.y);
					}
				} else
				if(currentActionID == Action.NONE) {
					if(creature != null) {
						draggedEntity = creature;
						highlightMove.accept(creature);
					}
				}
				
				currentActionID = Action.NONE;
				Highlight.clearAll();
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
		SapphireWorld.world.translateCursor(pos.x, pos.y, pos.z);

		if(!activateBaseCamControl) return true;
		return super.touchDragged(x, y, pointer);
	}

	private Position lastPos = new Position(0, 0);
	@Override
	public boolean mouseMoved(int x, int y) {
		
		var pos = getCursorWorldPos(x, y);
		var cell = (SapphireGame.fight == null) ? null : SapphireGame.fight.board.get(pos.x, pos.y); // check useful for mocking tests where we dont have a fight

		boolean moved = false;
		if(cell != null && !cell.pos.equals(lastPos)) {
			moved = true;
			lastPos = cell.pos;
			Highlight.clear(Highlight.highlightsM1);
		}
		
		// position label
		if(SapphireOwl.conf.functionality.showCursorPos) {
			SapphireGame.gfx.hud.cursorPos.setText(pos.x + ", " + pos.y);
			SapphireGame.gfx.hud.cursorPos.setPosition(x, Gdx.graphics.getHeight() - y);
		}
		// 3d cursor
		SapphireWorld.world.translateCursor(pos.x, pos.y, 0);
			

		try {
			if(cell != null && moved && cell.hasCreature()) {
				var crea = cell.getFirstCreature();
				highlightMove.accept(crea); //(Creature) SapphireGame.getPlayingCreature());
			} else
			if(cell != null && moved && currentActionID == Action.MOVE && targetEntity != null) {
				var c = targetEntity;
				var casterpos = c.get(Position.class);
				var start = Moonstone.fight.board.get(casterpos.x, casterpos.y);
				var list = Pathfinding.aStar(Moonstone.fight.board, (Creature) c, start, cell);
				Highlight.movement(list.stream().map(c1 -> (Vector2) c1.pos).collect(Collectors.toList()));
			}
			
		} catch(Exception e) {
			Log.info("", e);
		}

		if(!activateBaseCamControl) return true;
		return super.mouseMoved(x, y);
	}

	private Vector3 getCursorWorldPos(int x, int y) {
		var floorHeight = Constants.floorZ;
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
		if(SapphireGame.fight == null) return null; // check useful for mocking tests where we dont have a fight
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
