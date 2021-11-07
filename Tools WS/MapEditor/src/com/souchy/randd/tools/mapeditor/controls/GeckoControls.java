package com.souchy.randd.tools.mapeditor.controls;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.CONTROL_LEFT;
import static com.badlogic.gdx.Input.Keys.CONTROL_RIGHT;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.SHIFT_LEFT;
import static com.badlogic.gdx.Input.Keys.SHIFT_RIGHT;
import static com.badlogic.gdx.Input.Keys.UP;
import static com.badlogic.gdx.Input.Keys.W;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.RenderOptions;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.commons.lapis.util.Controls3d;
import com.souchy.randd.ebishoal.commons.lapis.util.ControlsConfig;
import com.souchy.randd.ebishoal.commons.lapis.util.KeyCombination;
import com.souchy.randd.ebishoal.commons.lapis.util.KeyCombination.KeyCombinationListener;
import com.souchy.randd.jade.Constants;
import com.souchy.randd.tools.mapeditor.imgui.components.Console;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.main.MapWorld;
import com.souchy.randd.tools.mapeditor.actions.Actions;
import com.souchy.randd.tools.mapeditor.entities.EditorEntities;

import imgui.ImGui;

public class GeckoControls extends Controls3d {
	
	public float cursorZ = Constants.floorZ;

	private Model selectedModel = null;
	private ModelInstance selectedInstance = null;
	public ControlsConfig confFull;

	public GeckoControls(Camera camera) {
		super(camera, MapEditorGame.screen.getViewport());
		this.floorHeight = Constants.floorZ;
	}
	
	@Override
	public void initCombos() {
		this.confFull = JsonConfig.readExternal(ControlsConfig.class, "./modules/");
		this.conf = confFull.controls3d;
		
		super.initCombos();
		Log.info("Controls 3d initCombos " + confFull);
		keys.putCombo(confFull.cancel, () -> { 
			//MapWorld.world.instances.remove(getSelectedInstance());
			EditorEntities.removeAdaptor(getSelectedInstance());
			this.setSelectedInstance(null);
			this.setSelectedModel(null);
		});
		keys.putCombo(confFull.musicToggle, () -> { });
		keys.putCombo(confFull.controls3d.camReset, () -> {
			MapEditorGame.screen.resetCamera();
			this.cursorZ = Constants.floorZ;
			updateCursor();
		});
		keys.putCombo(confFull.controls3d.camTopView, MapEditorGame.screen::topView);
		keys.putCombo(new KeyCombination(Keys.FORWARD_DEL), () -> {
			// delete instance at position
			var pos = getCursorWorldPos();
			pos.z = this.cursorZ;
			MapWorld.world.removeInstanceAt(pos);
			//var inst = MapWorld.world.getAt(pos);
			//EditorEntities.removeAdaptor(inst);
		});
		keys.putCombo(new KeyCombination(Keys.NUM_1), () -> {
			// add instance at positionl
			String modelPath = "res/models/shapes/cube2.g3dj";
			var model = LapisAssets.get(modelPath, Model.class);
			selectBrushModel(model);
		});
		keys.putCombo(new KeyCombination(Keys.CONTROL_LEFT, Keys.NUM_1), () -> {
			// add instance at positionl
			String modelPath = "res/models/decor/cube.g3dj";
			modelPath = "res/models/shapes/cube2.g3dj";
			var pos = getCursorWorldPos();
			pos.z = this.cursorZ;
			MapWorld.world.addInstance(modelPath, pos);
			
		});
		keys.putCombo(new KeyCombination(Keys.CONTROL_LEFT, Keys.NUM_2), () -> {
			// add instance at position
			String modelPath = "res/models/decor/pinetree.g3dj";
			var pos = getCursorWorldPos();
			pos.z = this.cursorZ;
			MapWorld.world.addInstance(modelPath, pos);
		});
		keys.putCombo(new KeyCombination(Keys.Z), () -> {
			this.cursorZ--;
			updateCursor();
		});
		keys.putCombo(new KeyCombination(Keys.X), () -> {
			this.cursorZ++;
			updateCursor();
		});
		keys.putCombo(new KeyCombination(Keys.L), () -> RenderOptions.renderLines = !RenderOptions.renderLines);
		keys.putCombo(new KeyCombination(Keys.ENTER), () -> {
			Console console = MapEditorGame.screen.imgui.console;
			if(console.showContainer.get()) {
				console.add(console.input.get());
			}
		});
		keys.putCombo(new KeyCombination(Keys.R), () -> Commands.resetShaders());
		keys.putCombo(new KeyCombination(Keys.R, Keys.CONTROL_LEFT), Actions::resetConf);
		keys.putCombo(new KeyCombination(Keys.R, Keys.SHIFT_LEFT), Actions::reloadAssets);
	}

	private boolean imguiLayer() {
		return ImGui.getIO().getWantCaptureMouse();
	}
	
	@Override
	public void act(float delta) {
		if(imguiLayer()) return;
		super.act(delta);
	}

	@Override
	public boolean keyDown(int keycode) {
		if(imguiLayer()) return true;
		return super.keyDown(keycode);
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if(imguiLayer()) return true;
		return super.keyUp(keycode);
	}
	
	@Override
	public boolean keyTyped(char character) {
		if(imguiLayer()) return true;
		return super.keyTyped(character);
	}
	
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		if(imguiLayer()) return true;
		
		Rectangle space = MapEditorGame.screen.hud.getDrawingSpace();
		if(space.contains(x, y) == false) {
			return true; // pas dans la zone
		}

		boolean drag = true;

		var previousworldpos = getCursorWorldPos(previousX, previousY);
		var worldpos = getCursorWorldPos(x, y);
//		Log.info("" + previousworldpos + " -> " + worldpos);
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) && getSelectedModel() != null) {
			if(!previousworldpos.equals(worldpos)) {
				dropNewInstance(worldpos);
			}
			drag = false;
			drawn = true;
		}
//		if(selectedInstance != null) {
//			drag = false;
//		}
		
		if(drag) super.touchDragged(x, y, pointer);
		if(drag) dragged = true;
		if(drag) drawn = false;
		updateCursor();
		previousX = x;
		previousY = y;
		return true;
	}
	
	private boolean drawn = false;
	private boolean dragged = false;
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if(imguiLayer()) return true;
		//if(this.previousX == x && this.previousY == y) {
			// drop it there
			//selectedInstance = null;
		//}
		return super.touchDown(x, y, pointer, button);
	}
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if(imguiLayer()) return true;
		if(!dragged && !drawn) { //this.previousX == x && this.previousY == y) {
			if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
				dropNewInstance(getCursorWorldPos());
			} else {
				// drop it there
				//selectedModel = null;
//				selectedInstance = null;
				var last = getSelectedInstance();
				if(last != null) {
					setSelectedInstance(null);
					setProperties(last);
				} else {
					var pos = this.getCursorWorldPos();
					var pick = MapWorld.world.getAt(pos);
					if(button == this.confFull.buttonAction) {
						setSelectedInstance(pick);
					} else 
					if(button == this.confFull.buttonInfo) {
						setProperties(pick);
					}
				}
				
			}
		}
		dragged = false;
		drawn = false;
		return super.touchUp(x, y, pointer, button);
	}
	
	
	@Override
	public boolean mouseMoved(int x, int y) {
		if(imguiLayer()) return true;
		updateCursor();
		return super.mouseMoved(x, y);
	}
	
	/**
	 * just need to implement this because Lapis Controls3d is based on lwjgl2 and this runs lwjgl3 so the scroll isn't caught
	 */
	@Override
	public boolean scrolled(float amountX, float amountY) {
		if(imguiLayer()) return true;
		scrolledFloat(amountY);
		if(!activateBaseCamControl) return true;
		return super.scrolled(amountX, amountY);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void scrolledFloat(float amount) {
		super.scrolledFloat(amount);
		// resize le viewport de la shadowlight pour sa shadowmap
		if(MapEditorGame.screen.getShadowLight() != null)
			MapEditorGame.screen.getShadowLight().zoom(camera.viewportWidth, camera.viewportHeight);
	}
	

	public void updateCursor() {
		// 3d cursor
		var worldpos = getCursorWorldPos();
		MapWorld.world.translateCursor(worldpos.x, worldpos.y, this.cursorZ);
		if(getSelectedInstance() != null) {
			worldpos.add(Constants.cellHalf, 0, Constants.cellHalf + this.cursorZ - this.floorHeight);
			getSelectedInstance().transform.setTranslation(worldpos.x, worldpos.y, worldpos.z);
		}
	}
	
	
	public void dropNewInstance(Vector3 worldpos) {
		var inst = new ModelInstance(getSelectedModel());
		worldpos.add(Constants.cellHalf, 0, Constants.cellHalf + this.cursorZ - this.floorHeight);
		inst.transform.setTranslation(worldpos.x, worldpos.y, worldpos.z);
		//MapWorld.world.instances.add(inst);
		EditorEntities.addAdaptor(inst);
	}
	
	

	
	public ModelInstance getSelectedInstance() {
		return selectedInstance;
	}
	public void setSelectedInstance(ModelInstance selectedInstance) {
		this.selectedInstance = selectedInstance;
		//if(selectedInstance != null) // dont remove the properties when unselecting/placing an object i think
			setProperties(selectedInstance); 
	}
	public void setProperties(ModelInstance selectedInstance) {
		if(selectedInstance != null)
			MapEditorGame.screen.imgui.settings.props.mats = selectedInstance.materials;
		else
			MapEditorGame.screen.imgui.settings.props.mats = null;
		
		MapEditorGame.screen.imgui.properties.setObj(selectedInstance);
	}

	public Model getSelectedModel() {
		return selectedModel;
	}

	public void setSelectedModel(Model selectedModel) {
		this.selectedModel = selectedModel;
	}

	public static void selectBrushModel(Model model) {
		//Log.info("select brush " + model);
		// remove the previous instance
		if(MapEditorGame.screen.controller.getSelectedInstance() != null)
			//MapWorld.world.instances.remove(MapEditorGame.screen.controller.getSelectedInstance());
			EditorEntities.removeAdaptor(MapEditorGame.screen.controller.getSelectedInstance());
		// create new instance
		ModelInstance instance = new ModelInstance(model);
		MapEditorGame.screen.controller.setSelectedInstance(instance);
		MapEditorGame.screen.controller.setSelectedModel(model);
		EditorEntities.addAdaptor(instance);
		MapEditorGame.screen.controller.updateCursor();
	}
	
}
