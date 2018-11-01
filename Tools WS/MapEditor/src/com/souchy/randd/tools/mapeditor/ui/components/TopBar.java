package com.souchy.randd.tools.mapeditor.ui.components;

import java.io.File;
import java.io.FileFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
import com.souchy.randd.situationtest.models.map.MapData;
import com.souchy.randd.tools.mapeditor.MapEditorGame;

public class TopBar extends MenuBar implements Component {
	
	
	@SuppressWarnings("deprecation")
	public TopBar() {
		
		FileChooser.setFavoritesPrefsName("com.your.package.here.filechooser");
		
		createFileMenu();
		createToolMenu();
		
		getTable().padLeft(5);
		getTable().padTop(3);
		getTable().padBottom(3);
		
		//getTable().setBackground(MapEditorGame.skin.newDrawable("white"));
		//getTable().setColor(Color.DARK_GRAY);
		///getTable().pack();
	}
	
	@Override
	public void resize(float width, float height) {
		getTable().pack();
		getTable().setWidth(width);
		//getTable().setHeight(27);
		getTable().setPosition(0, height - 1, Align.topLeft);
	}
	
	private void createFileMenu() {
		Menu file = new Menu("File");
		this.addMenu(file); 
		
		MenuItem open = new MenuItem("Open");
		open.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				FileChooser fileChooser = new FileChooser(Mode.OPEN);
				fileChooser.setSelectionMode(SelectionMode.FILES);
				fileChooser.setDirectory(Gdx.files.internal("data/maps/").file());
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
						MapEditorGame.loadMap();
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
				MapEditorGame.loadMap();
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
					MapEditorGame.cache.set(MapEditorGame.currentFile.get().file().getPath(), MapEditorGame.currentMap.get());
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
				MapEditorGame.cache.set(MapEditorGame.currentFile.get().file().getPath(), MapEditorGame.currentMap.get());
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
				System.out.println("option!!");
			}
		});
	}
	
	
}
