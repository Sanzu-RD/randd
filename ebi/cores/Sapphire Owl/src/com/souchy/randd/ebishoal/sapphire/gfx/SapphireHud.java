package com.souchy.randd.ebishoal.sapphire.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.vis.util.VisLml;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.GlobalLML.GlobalLMLActions;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.sapphire.gfx.ui.roundImage.RoundImageLmlTagProvider;
import com.souchy.randd.ebishoal.sapphire.ux.Chat;
import com.souchy.randd.ebishoal.sapphire.ux.CreatureSheet;
import com.souchy.randd.ebishoal.sapphire.ux.PlayBar;
import com.souchy.randd.ebishoal.sapphire.ux.StatusIcon;
import com.souchy.randd.ebishoal.sapphire.ux.*;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireWidget.SapphireWidgetTagProvider;

public class SapphireHud extends LapisHud {

	@LmlActor("life")
	public Label life;

	@LmlActor("pageUp")
	public Button pageUp;

	@LmlActor("pageUpImage")
	public Image pageUpImage;

	@LmlActor("pageDownImage")
	public Image pageDownImage;
	
	public static LmlParser parser;
	public static SapphireHud single;
	public static SapphireHudSkin skin;
	public static I18NBundle i18n;
	
	public SapphireHud() {

		var batch = new SapphireBatch();
		var viewport = new ScreenViewport();
		
		// Batch(shader)
		var vert = Gdx.files.internal("res/shaders/ui.vertex.glsl"); //Gdx.files.internal("res/gdx/shaders/postProcess.vertex.glsl");
		var frag = Gdx.files.internal("res/shaders/ui.fragment.glsl"); //Gdx.files.internal("res/gdx/shaders/postProcess.fragment.glsl");
		var shader = new ShaderProgram(vert, frag);
		batch.setShader(shader);

		this.setStage(new Stage(viewport, batch));

		// Parser(actions, i18n, skin, tags)
		//i18n = I18NBundle.createBundle(Gdx.files.internal("res/i18n/ui/bundle"));
		skin = new SapphireHudSkin(getSkinFile());
		parser = VisLml.parser()
				// Registering global action container:
				.actions("global", GlobalLMLActions.class)
				// Adding localization support:
				//.i18nBundle(i18n)
				// Set default skin
				.skin(skin) 
				// Tags
				.tag(new RoundImageLmlTagProvider(), "roundImage")
				.tag(new SapphireWidgetTagProvider<>(Chat.class), "chat")
				.tag(new SapphireWidgetTagProvider<>(PlayBar.class), "playbar")
				.tag(new SapphireWidgetTagProvider<>(StatusIcon.class), "statusicon")
				.tag(new SapphireWidgetTagProvider<>(StatusBar.class), "statusbar")
				.tag(new SapphireWidgetTagProvider<>(Timeline.class), "timeline")
				.tag(new SapphireWidgetTagProvider<>(CreatureSheet.class), "creaturesheet")
				.build();
		
		single = this;
		parser.createView(single, getTemplateFile());
		
		Dialog d;
		//parser.parseTemplate(lmlTemplateFile)

		parser.createView(CreatureSheet.class, "res/ux/sapphire/creaturesheet.lml");
		
		
		createListeners();
	}
	
	public static void refresh() {
		// var asd = SapphireHud.parser.createView(SapphireHud.single,SapphireHud.single.getTemplateFile());
		//SapphireHud.parser.parseTemplate(SapphireHud.single.getTemplateFile());
		SapphireHud.single.getStage().getActors().clear();
		SapphireHud.parser.fillStage(SapphireHud.single.getStage(), SapphireHud.single.getTemplateFile());
//		SapphireHud.parser.fillStage(SapphireHud.single.getStage(), Gdx.files.internal("res/ux/sapphire/chat.lml"));
//		SapphireHud.parser.fillStage(SapphireHud.single.getStage(), Gdx.files.internal("res/ux/sapphire/timer.lml"));
//		SapphireHud.parser.fillStage(SapphireHud.single.getStage(), Gdx.files.internal("res/ux/sapphire/timeline.lml"));
//		SapphireHud.parser.fillStage(SapphireHud.single.getStage(), Gdx.files.internal("res/ux/sapphire/statusbar.lml"));
//		SapphireHud.parser.fillStage(SapphireHud.single.getStage(), Gdx.files.internal("res/ux/sapphire/playbar.lml"));
//		SapphireHud.parser.fillStage(SapphireHud.single.getStage(), Gdx.files.internal("res/ux/sapphire/creaturesheet.lml"));
	}
	
	@Override
	public String getViewId() {
		return "sheet";
	}
	
	@Override
	public FileHandle getTemplateFile() {
		return Gdx.files.internal("res/ux/sapphire/" + getViewId() + ".lml");
	}
	
	@Override
	public FileHandle getSkinFile() {
		return Gdx.files.internal("res/ux/sapphire/" + getViewId() + ".json");
	}

	private void createListeners() {
		pageUp.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				Log.info("enter");
				var light = 1.3f;
				//view.pageUpImage.setColor(light, light, light, view.pageUpImage.getColor().a);
				super.enter(event, x, y, pointer, fromActor);
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Log.info("exit");
				pageUpImage.setColor(1, 1, 1, pageUpImage.getColor().a);
				super.exit(event, x, y, pointer, toActor);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Log.info("touch down");
				//view.pageUpImage.setDrawable(parser.getData().getDefaultSkin().getDrawable("down"));
				var shade = 0.7f;
				pageUpImage.setColor(shade, shade, shade, pageUpImage.getColor().a);
				return super.touchDown(event, x, y, pointer, button);
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Log.info("touch up");
				var light = 1.0f;
				pageUpImage.setColor(light, light, light, pageUpImage.getColor().a);
				//view.pageUpImage.setDrawable(parser.getData().getDefaultSkin().getDrawable("up"));
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}
	
}
