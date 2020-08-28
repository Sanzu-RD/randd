package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.google.common.eventbus.Subscribe;
import com.kotcrab.vis.ui.VisUI;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;

public abstract class SapphireComponent extends Table implements ActionContainer {

	/**
	 * Event for all UIs to dispose themselves
	 * @author Blank
	 * @date 28 août 2020
	 */
	public static class DisposeUIEvent {
		
	}

	public SapphireComponent() { 
		this(SapphireGame.gfx.hud.getStage());
	}
	
	public SapphireComponent(Stage stage) {
		super(VisUI.getSkin());
		SapphireOwl.core.bus.register(this);
		this.setStage(stage);
		init();
	}

	protected abstract void onInit();
	public abstract void resizeScreen(int w, int h, boolean centerCam);
	public abstract String getTemplateId();
	public abstract void dispose();
	
	@Subscribe
	public void onDisposeEvent(DisposeUIEvent e) {
		SapphireOwl.core.bus.unregister(this);
		dispose();
	}
	
	protected void init() {
		var stage = this.getStage();
		this.remove();
		this.clear();
		SapphireLmlParser.parser.createView(this, getTemplateFile());
		stage.addActor(this);
		onInit();
	}
	
	
	public FileHandle getTemplateFile() {
		return Gdx.files.internal("res/ux/sapphire/components/" + getTemplateId() + ".lml");
	}
	
	public FileHandle getStyleFile() {
		return Gdx.files.internal("res/ux/sapphire/components/" + getTemplateId() + ".json");
	}
	
	
	
	
}
