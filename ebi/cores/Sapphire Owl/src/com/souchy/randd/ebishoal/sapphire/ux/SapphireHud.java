package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.czyzby.lml.annotation.LmlAction;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.LapisShader;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireBatch;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;
//import com.souchy.randd.ebishoal.sapphire.ux.SapphireWidget.LmlWidgets;
//import com.souchy.randd.ebishoal.sapphire.ux.SapphireWidget.SapphireWidgetTagProvider;
import com.souchy.randd.ebishoal.sapphire.ux.components.Chat;
import com.souchy.randd.ebishoal.sapphire.ux.components.Parameters;
import com.souchy.randd.ebishoal.sapphire.ux.components.PlayBar;
import com.souchy.randd.ebishoal.sapphire.ux.components.QuickOptions;
import com.souchy.randd.ebishoal.sapphire.ux.components.Timeline;

public class SapphireHud extends LapisHud {

//	public static SapphireHud single;
//	public static LmlParser parser;
//	public static SapphireHudSkin skin;
//	public static I18NBundle i18n;
	
//	@LmlActor("content")
//	public Stack content;
//	//@LmlActor("chat")
//	public static Chat chat;
//	@LmlActor("playbar")
//	public static PlayBar playbar;
//	@LmlActor("statusbar")
//	public StatusBar statusBar;
//	@LmlActor("timeline")
//	public static Timeline timeline;
//	@LmlActor("timer")
//	public Timer timer;
//	@LmlActor("parameters")
//	public static Parameters parameters;
	

	public Chat chat;
	public PlayBar playbar;
	public Timeline timeline;
	public Parameters parameters;
	
	public boolean isLoaded = false;
	
	public SapphireHud() {
		// SpriteBatch, Viewport, Shader, Stage
		var batch = new SapphireBatch();
		var viewport = new ScreenViewport();
		var shader = new ShaderProgram(LapisShader.getVertexShader("ui"), LapisShader.getFragmentShader("ui"));
		batch.setShader(shader);
		this.setStage(new Stage(viewport, batch));
	}
	
	public void reload() {
		this.getStage().clear();
		SapphireLmlParser.parser.createView(this, getTemplateFile());
		
		chat = new Chat();
		playbar = new PlayBar();
		timeline = new Timeline();
		new QuickOptions();
		
		parameters = new Parameters();
		parameters.setVisible(false);
		
//		getStage().setDebugAll(true);
		isLoaded = true;
	}
	
	@Override
	public void resize(int width, int height, boolean centerCamera) {
		super.resize(width, height, centerCamera);
		this.getStage().getActors().forEach(a -> {
			if(a instanceof SapphireComponent) {
				((SapphireComponent) a).resizeScreen(width, height, centerCamera);
			}
		});
	}
	
	@Override
	public String getViewId() {
		return "main";
	}
	
	@Override
	public FileHandle getTemplateFile() {
		return Gdx.files.internal("res/ux/sapphire/" + getViewId() + ".lml");
	}
	
	@Override
	public FileHandle getSkinFile() {
		return Gdx.files.internal("res/ux/sapphire/" + getViewId() + ".json");
	}

	
}
