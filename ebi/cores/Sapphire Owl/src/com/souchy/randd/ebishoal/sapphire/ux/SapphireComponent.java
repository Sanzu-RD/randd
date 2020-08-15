package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.kotcrab.vis.ui.VisUI;

public abstract class SapphireComponent extends Table implements ActionContainer {

	public SapphireComponent() {
		super(VisUI.getSkin());
	}
	public SapphireComponent(Stage stage) {
		this();
		this.setStage(stage);
		init();
	}

	protected abstract void onInit();
	
	protected void init() {
		var stage = this.getStage();
		this.remove();
		this.clear();
		SapphireLmlParser.parser.createView(this, getTemplateFile());
		stage.addActor(this);
		onInit();
	}
	
	public abstract void resizeScreen(int w, int h, boolean centerCam);
	
	public abstract String getTemplateId();
	
	public FileHandle getTemplateFile() {
		return Gdx.files.internal("res/ux/sapphire/components/" + getTemplateId() + ".lml");
	}
	
	public FileHandle getStyleFile() {
		return Gdx.files.internal("res/ux/sapphire/components/" + getTemplateId() + ".json");
	}
	
	
	
	
}
