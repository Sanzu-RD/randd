package com.souchy.randd.tools.mapeditor.imgui.components;

import java.io.File;
import java.io.FileFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import com.kotcrab.vis.ui.widget.file.FileChooser.Mode;
import com.kotcrab.vis.ui.widget.file.FileChooser.SelectionMode;
import com.souchy.randd.commons.mapio.MapData;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;
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
    				MapWorld.world.instances.clear();
    				MapWorld.world.cache.begin();
    				MapWorld.world.cache.end();
    				MapEditorGame.currentFile.set(null);
    				MapEditorGame.currentMap.set(new MapData());
    			}
    			if(ImGui.menuItem("Open")) {
    				open();
    			}
    			if(ImGui.menuItem("Save")) {
					if(MapEditorGame.currentFile.get() == null) 
						saveAs();
					else 
						MapEditorGame.properties.save();
    			}
    			if(ImGui.menuItem("Save as")) {
    				saveAs();
    			}
    			ImGui.endMenu();
    		}
    		
    		if(ImGui.beginMenu("View")) {
    			if(ImGui.menuItem("Tree view")) {
    				MapEditorGame.screen.imgui.tree.show();
    			}
    			if(ImGui.menuItem("Explorer view")) {
    				MapEditorGame.screen.imgui.explorer.show();
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
	
	

	private void open() {
		FileChooser fileChooser = new FileChooser(Mode.OPEN);
		fileChooser.setSelectionMode(SelectionMode.FILES);
		fileChooser.setDirectory(Gdx.files.internal("res/maps").file()); // data/maps/
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory() || file.getName().endsWith(".map");
			}
		});
		fileChooser.setListener(new FileChooserAdapter() {
			@Override
			public void selected(Array<FileHandle> files) {
				super.selected(files);
				/*System.out.print("files : ");
				files.forEach(f -> System.out.print(f.name() + ", "));
				System.out.println("");*/
				
				MapEditorGame.currentFile.set(files.get(0));
				MapEditorGame.screen.world.gen(); //MapEditorGame.loadMap();
			}
		});
		MapEditorGame.screen.hud.getStage().addActor(fileChooser.fadeIn());
	}
	private void saveAs() {
		FileChooser fileChooser = new FileChooser(Mode.SAVE);
		fileChooser.setSelectionMode(SelectionMode.FILES);
		fileChooser.setDirectory(Gdx.files.internal("data/maps/").file());
		fileChooser.setListener(new FileChooserAdapter() {
			@Override
			public void selected (Array<FileHandle> files) {
				//textField.setText(file.get(0).file().getAbsolutePath());
				//System.out.println("Files : " + String.join(", ", Arrays.asList(files.items).stream().map(f -> f.name()).collect(Collectors.toList())));

				MapEditorGame.currentFile.set(files.get(0));
				//MapEditorGame.mapCache.set(MapEditorGame.currentFile.get().file().getPath(), MapEditorGame.currentMap.get());
				//fileChooser.remove();
			}
		});
		MapEditorGame.screen.hud.getStage().addActor(fileChooser.fadeIn());
	}
	
	
}
