package com.souchy.randd.tools.mapeditor.imgui.windows;

import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import com.kotcrab.vis.ui.widget.file.FileChooser.Mode;
import com.kotcrab.vis.ui.widget.file.FileChooser.SelectionMode;
import com.souchy.randd.commons.mapio.MapData;
import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.tools.mapeditor.entities.EditorEntities;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;
import com.souchy.randd.tools.mapeditor.imgui.windowsgame.CreatureEditor;
import com.souchy.randd.tools.mapeditor.imgui.windowsgame.SpellEditor;
import com.souchy.randd.tools.mapeditor.imgui.windowsgame.StatusEditor;
import com.souchy.randd.tools.mapeditor.main.MapEditorCore;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.main.MapWorld;

import imgui.ImGui;

public class TopBar implements ImGuiComponent {

	public TopBar() {
		FileChooser.setDefaultPrefsName("com.souchy.randd.tools.mapeditor.filechooser");
	}
	
	@Override
	public void render(float delta) {
    	if(ImGui.beginMainMenuBar()) { 
    		
    		renderFileMenu();
    		renderViewMenu();
    		renderModelBuilderMenu();
    		
    		ImGui.endMainMenuBar();
    	}
	}
	
	
	private void renderFileMenu() {
		if(ImGui.beginMenu("File")) {
			if(ImGui.menuItem("New")) {
				//MapWorld.world.instances.clear();
				EditorEntities.clears();
				MapEditorGame.currentFile.set(null);
				MapEditorGame.currentMap.set(new MapData());
			}
			if(ImGui.menuItem("Open")) {
				MapEditorGame.screen.hud.open(Environment.fromRes("maps/"), ".map", files -> {
					MapEditorGame.currentFile.set(files.get(0));
					MapEditorGame.screen.world.gen(); //MapEditorGame.loadMap();
				});
			}
			if(ImGui.menuItem("Save")) {
				if(MapEditorGame.currentFile.get() == null) {
					MapEditorGame.screen.hud.saveAs(Environment.fromRes("maps/"), ".map", files -> MapEditorGame.currentFile.set(files.get(0)));
					//MapEditorGame.mapCache.set(MapEditorGame.currentFile.get().file().getPath(), MapEditorGame.currentMap.get());
				} else {
					//MapEditorGame.properties.save();
				}
			}
			if(ImGui.menuItem("Save as")) {
				MapEditorGame.screen.hud.saveAs(Environment.fromRes("maps/"), ".map", files -> MapEditorGame.currentFile.set(files.get(0)));
			}
			ImGui.separator();
			if(ImGui.menuItem("Load Assets")) {
				MapEditorGame.screen.hud.open(MapEditorCore.conf.lastFolder, "", (files) -> {
					MapEditorCore.conf.lastFolder = files.get(0).parent().path();
					for(var f : files) {
						LapisAssets.loadAuto(f);
					}
				});
			}
			if(ImGui.menuItem("Clear Assets")) {
				Gdx.app.postRunnable(() -> {
					LapisAssets.hack().clear();
				});
			}
			ImGui.endMenu();
		}
	}
	
	private void renderViewMenu() {
		if(ImGui.beginMenu("View")) {
			if(ImGui.menuItem("Objects Tree")) {
				MapEditorGame.screen.imgui.tree.show();
			}
			if(ImGui.menuItem("Assets Explorer")) {
				MapEditorGame.screen.imgui.explorer.show();
			}
			if(ImGui.menuItem("Properties")) {
				MapEditorGame.screen.imgui.properties.show();
			}
			if(ImGui.menuItem("Console")) {
				MapEditorGame.screen.imgui.console.show();
			}
			if(ImGui.menuItem("Settings")) {
				MapEditorGame.screen.imgui.settings.show();
			}
			ImGui.separator();
			if(ImGui.menuItem("Creature Editor")) {
				CreatureEditor.get().show();
			}
			if(ImGui.menuItem("Spell Editor")) {
				SpellEditor.get().show();
			}
			if(ImGui.menuItem("Status Editor")) {
				StatusEditor.get().show();
			}
			ImGui.separator();
			if(ImGui.menuItem("Resolution 1600x900")) {
				MapEditorGame.game.resize(1600, 900);
				//MapEditorGame.screen.resize(1600, 900);
			}
			ImGui.endMenu();
		}
	}

	private void renderModelBuilderMenu() {
		if(ImGui.beginMenu("ModelBuilder")) {
			if(ImGui.menuItem("Plane")) {
				MapWorld.createPlane();
			}
			if(ImGui.menuItem("Cube")) {
				MapWorld.createBox();
			}
			if(ImGui.menuItem("Capsule")) {
				MapWorld.createCapsule();
			}
			ImGui.endMenu();
		}
	}
	
	
}
