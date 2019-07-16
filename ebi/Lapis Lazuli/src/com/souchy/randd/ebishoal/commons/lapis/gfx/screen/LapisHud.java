package com.souchy.randd.ebishoal.commons.lapis.gfx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;

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
	
	public FileHandle getStyleFile() {
		return Gdx.files.internal(getViewId() + ".json");
	}
	
}
