package com.souchy.randd.tools.mapeditor.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;

public class MyLml extends AbstractLmlView {

	public MyLml() {
        super(new Stage());
		// TODO Auto-generated constructor stub
	}

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal("ui/test.lml");
    }

	@Override
	public String getViewId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
