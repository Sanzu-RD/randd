package com.souchy.randd.tools.mapeditor.ui.components;

import java.io.File;
import java.io.FileFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.Menu;
import com.kotcrab.vis.ui.widget.MenuBar;
import com.kotcrab.vis.ui.widget.MenuItem;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooser.Mode;
import com.kotcrab.vis.ui.widget.file.FileChooser.SelectionMode;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import com.souchy.randd.commons.diamond.ext.MapData;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.mapeditor.main.MapEditorCore;
import com.souchy.randd.tools.mapeditor.main.MapEditorGame;
import com.souchy.randd.tools.mapeditor.main.MapWorld;
import com.souchy.randd.tools.mapeditor.ui.*;

public class TopBar extends MenuBar implements Component {
	
	
	@SuppressWarnings("deprecation")
	public TopBar() {
		
		FileChooser.setDefaultPrefsName("com.souchy.randd.tools.mapeditor.filechooser");
		
		createFileMenu();
		createToolMenu();
		
		
		getTable().padLeft(5);
		getTable().padTop(3);
		getTable().padBottom(3);

		//getTable().setBackground(MapEditorGame.skin.newDrawable("white"));
		//getTable().setColor(Color.DARK_GRAY);
		///getTable().pack();

		getTable().pack();
		getTable().setPosition(0, Gdx.graphics.getHeight(), Align.topLeft);
	}
	
	@Override
	public void resize(float width, float height) {
		getTable().pack();
		getTable().setWidth(width);
//		getTable().setHeight(50);
//		Log.debug("TopBar resize : screen %s, bar %s", height, getTable().getHeight());
		
		if(MapEditorCore.debug) height -= 10;
		if(MapEditorCore.debug) this.getTable().setColor(1, 1, 1, 0.5f);
		getTable().setPosition(0, height, Align.topLeft);
	}
	
	private void createFileMenu() {
		Menu file = new Menu("File");
		this.addMenu(file); 
		
		MenuItem open = new MenuItem("Open");
		open.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
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
				getTable().getStage().addActor(fileChooser.fadeIn());
			}
		});
		file.addItem(open);
		
		MenuItem neww = new MenuItem("New");
		neww.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				MapEditorGame.currentFile.set(null);
				MapEditorGame.currentMap.set(new MapData());
				//MapEditorGame.loadMap();
			}
		});
		file.addItem(neww);
		
		MenuItem saveas = new MenuItem("Save as...");
		saveas.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				saveAs();
			}
		});
		file.addItem(saveas);
		
		MenuItem save = new MenuItem("Save");
		save.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				if(MapEditorGame.currentFile.get() == null) {
					saveAs();
				} else {
					//MapEditorGame.cache.set(MapEditorGame.currentFile.get().name(), MapEditorGame.currentMap.get());
					//MapEditorGame.mapCache.set(MapEditorGame.currentFile.get().file().getPath(), MapEditorGame.currentMap.get());
					MapEditorGame.properties.save();
				}
			}
		});
		file.addItem(save);
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
		getTable().getStage().addActor(fileChooser.fadeIn());
	}
	
	private void createToolMenu() {
		Menu tools = new Menu("Tool");
		this.addMenu(tools); 
		
		MenuItem options = new MenuItem("Options");
		options.align(Align.left);
		tools.addItem(options);
		options.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				Log.info("TopBar click option menu");
			}
		});

		MenuItem shaders = new MenuItem("Shaders");
		shaders.align(Align.left);
		tools.addItem(shaders);
		shaders.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				//Log.info("TopBar click option menu");
				
			}
		});
	}
	
	
}
