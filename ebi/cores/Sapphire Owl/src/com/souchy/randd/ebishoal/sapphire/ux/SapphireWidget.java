package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.github.czyzby.lml.parser.action.ActorConsumer;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;

public abstract class SapphireWidget extends Actor implements ActionContainer {

	public abstract String getTemplateId();
	
	public FileHandle getTemplateFile() {
		return Gdx.files.internal("res/ux/sapphire/" + getTemplateId() + ".lml");
	}
	
	public FileHandle getStyleFile() {
		return Gdx.files.internal("res/ux/sapphire/" + getTemplateId() + ".json");
	}

}
