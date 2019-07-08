package com.souchy.randd.mockingbird.lapismock.cleaner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;

public class GameScreenHud extends AbstractLmlView {
	
	@LmlActor("random")
	private Label result;
	
	public GameScreenHud() {
		super(new Stage());
	}
	
	@Override
	public FileHandle getTemplateFile() {
		return Gdx.files.internal("ui/test1.lml");
	}
	
	@Override
	public String getViewId() {
		return "test1";
	}
	
	@LmlAction("roll")
	public void rollNumber() {
		result.setText(String.valueOf((int) (MathUtils.random() * 1000)));
	}
	
}
