package com.souchy.randd.ebishoal.commons.lapis.gfx.screen;

import java.nio.file.Path;
import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import com.kotcrab.vis.ui.widget.file.FileTypeFilter;
import com.kotcrab.vis.ui.widget.file.FileChooser.Mode;
import com.kotcrab.vis.ui.widget.file.FileChooser.SelectionMode;

public abstract class LapisHud extends AbstractLmlView {
	
	public LapisHud() {
		super(new Stage());
	}
	
	public LapisHud(Stage stage) {
		super(stage);
	}
	
	@Override
	public FileHandle getTemplateFile() {
		return Gdx.files.internal(getViewId() + ".lml");
	}
	
	public FileHandle getSkinFile() {
		return Gdx.files.internal(getViewId() + ".json");
	}
	
	
	
	/**
	 * Open the FileChooser to open a file
	 */
	public void open(Path folder, String extension, Consumer<Array<FileHandle>> onSelect) {
		open(folder.toAbsolutePath().toString(), extension, onSelect);
	}
	/**
	 * Open the FileChooser to open a file
	 */
	public void open(String folder, String extension, Consumer<Array<FileHandle>> onSelect) {
		FileChooser fileChooser = new FileChooser(Mode.OPEN);
		fileChooser.setSelectionMode(SelectionMode.FILES);
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setDirectory(Gdx.files.internal(folder).file()); // data/maps/
//		fileChooser.setFileFilter(new FileFilter() {
//			@Override
//			public boolean accept(File file) {
//				return file.isDirectory() || file.getName().endsWith(extension);
//			}
//		});
		if(extension != null && !extension.isEmpty()) {
			var filter = new FileTypeFilter(true);
			filter.addRule(extension, extension);
			fileChooser.setFileTypeFilter(filter);
		}
		
		
		fileChooser.setListener(new FileChooserAdapter() {
			@Override
			public void selected(Array<FileHandle> files) {
				super.selected(files);
				onSelect.accept(files);
			}
		});
		this.getStage().addActor(fileChooser.fadeIn());
	}
	/**
	 * Open the FileChooser to save a file
	 */
	public void saveAs(Path folder, String extension, Consumer<Array<FileHandle>> onSelect) {
		saveAs(folder.toAbsolutePath().toString(), extension, onSelect);
	}
	/**
	 * Open the FileChooser to save a file
	 */
	public void saveAs(String folder, String extension, Consumer<Array<FileHandle>> onSelect) {
		FileChooser fileChooser = new FileChooser(Mode.SAVE);
		fileChooser.setSelectionMode(SelectionMode.FILES);
		fileChooser.setDirectory(Gdx.files.internal(folder).file());
//		fileChooser.setFileFilter(new FileFilter() {
//			@Override
//			public boolean accept(File file) {
//				return file.isDirectory() || file.getName().endsWith(extension);
//			}
//		});
		if(extension != null && !extension.isEmpty()) {
			var filter = new FileTypeFilter(true);
			filter.addRule(extension, extension);
			fileChooser.setFileTypeFilter(filter);
		}
		
		fileChooser.setListener(new FileChooserAdapter() {
			@Override
			public void selected (Array<FileHandle> files) {
				onSelect.accept(files);
			}
		});
		this.getStage().addActor(fileChooser.fadeIn());
	}
}
