package com.souchy.randd.tools.mapeditor.imgui.components;

import java.io.File;
import java.io.FileFilter;
import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import com.kotcrab.vis.ui.widget.file.FileChooser.Mode;
import com.kotcrab.vis.ui.widget.file.FileChooser.SelectionMode;
import com.souchy.randd.commons.mapio.MapData;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.tools.mapeditor.entities.EditorEntities;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;
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
    		
    		if(ImGui.beginMenu("File")) {
    			if(ImGui.menuItem("New")) {
    				//MapWorld.world.instances.clear();
    				EditorEntities.clears();
    				MapEditorGame.currentFile.set(null);
    				MapEditorGame.currentMap.set(new MapData());
    			}
    			if(ImGui.menuItem("Open")) {
    				open("res/maps", ".map", files -> {
    					MapEditorGame.currentFile.set(files.get(0));
    					MapEditorGame.screen.world.gen(); //MapEditorGame.loadMap();
    				});
    			}
    			if(ImGui.menuItem("Save")) {
					if(MapEditorGame.currentFile.get() == null) {
						saveAs("res/maps/", files -> MapEditorGame.currentFile.set(files.get(0)));
						//MapEditorGame.mapCache.set(MapEditorGame.currentFile.get().file().getPath(), MapEditorGame.currentMap.get());
					} else {
						//MapEditorGame.properties.save();
					}
    			}
    			if(ImGui.menuItem("Save as")) {
    				saveAs("res/maps/", files -> MapEditorGame.currentFile.set(files.get(0)));
    			}
    			ImGui.separator();
    			if(ImGui.menuItem("Load Assets")) {
    				open(MapEditorCore.conf.lastFolder, "", (files) -> {
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
    		
    		if(ImGui.beginMenu("View")) {
    			if(ImGui.menuItem("Tree view")) {
    				MapEditorGame.screen.imgui.tree.show();
    			}
    			if(ImGui.menuItem("Assets view")) {
    				MapEditorGame.screen.imgui.explorer.show();
    			}
    			if(ImGui.menuItem("Properties view")) {
    				MapEditorGame.screen.imgui.properties.show();
    			}
    			if(ImGui.menuItem("Console view")) {
    				MapEditorGame.screen.imgui.console.show();
    			}
    			if(ImGui.menuItem("Settings view")) {
    				MapEditorGame.screen.imgui.settings.show();
    			}
    			ImGui.endMenu();
    		}
    		
    		ImGui.endMainMenuBar();
    	}
	}
	
	

	private void open(String folder, String extension, Consumer<Array<FileHandle>> onSelect) {
		FileChooser fileChooser = new FileChooser(Mode.OPEN);
		fileChooser.setSelectionMode(SelectionMode.FILES);
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setDirectory(Gdx.files.internal(folder).file()); // data/maps/
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory() || file.getName().endsWith(extension);
			}
		});
		fileChooser.setListener(new FileChooserAdapter() {
			@Override
			public void selected(Array<FileHandle> files) {
				super.selected(files);
				MapEditorCore.conf.lastFolder = files.get(0).parent().path();
				//System.out.print("files : ");
				//files.forEach(f -> System.out.print(f.name() + ", "));
				//System.out.println("");
				onSelect.accept(files);
			}
		});
		MapEditorGame.screen.hud.getStage().addActor(fileChooser.fadeIn());
	}
	private void saveAs(String folder, Consumer<Array<FileHandle>> onSelect) {
		FileChooser fileChooser = new FileChooser(Mode.SAVE);
		fileChooser.setSelectionMode(SelectionMode.FILES);
		fileChooser.setDirectory(Gdx.files.internal(folder).file());
		fileChooser.setListener(new FileChooserAdapter() {
			@Override
			public void selected (Array<FileHandle> files) {
				//textField.setText(file.get(0).file().getAbsolutePath());
				//System.out.println("Files : " + String.join(", ", Arrays.asList(files.items).stream().map(f -> f.name()).collect(Collectors.toList())));
				onSelect.accept(files);
			}
		});
		MapEditorGame.screen.hud.getStage().addActor(fileChooser.fadeIn());
	}
	
	
}
