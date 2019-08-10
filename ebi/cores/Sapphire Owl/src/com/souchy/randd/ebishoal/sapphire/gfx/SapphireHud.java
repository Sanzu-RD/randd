package com.souchy.randd.ebishoal.sapphire.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.vis.util.VisLml;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.GlobalLML.GlobalLMLActions;
import com.souchy.randd.ebishoal.sapphire.gfx.ui.roundImage.RoundImageLmlTagProvider;
import com.souchy.randd.ebishoal.sapphire.main.SapphireResources;

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
		// Stage(viewport, batch)
		var batch = new SapphireBatch();
		var viewport = new ScreenViewport();
		this.setStage(new Stage(viewport, batch));
		
		// Batch(shader)
		var vert = Gdx.files.absolute("F:/Users/Souchy/Desktop/Robyn/Git/r and d/ebi/PiranhaPlants/res/gdx/shaders/ui.vertex.glsl"); //Gdx.files.internal("res/gdx/shaders/postProcess.vertex.glsl");
		var frag = Gdx.files.absolute("F:/Users/Souchy/Desktop/Robyn/Git/r and d/ebi/PiranhaPlants/res/gdx/shaders/ui.fragment.glsl"); //Gdx.files.internal("res/gdx/shaders/postProcess.fragment.glsl");
		var shader = new ShaderProgram(vert, frag);
		batch.setShader(shader);

		// Parser(actions, i18n, skin, tags)
		i18n = I18NBundle.createBundle(Gdx.files.internal("i18n/bundle"));
		skin = new SapphireHudSkin(getStyleFile());
		parser = VisLml.parser()
				// Registering global action container:
				.actions("global", GlobalLMLActions.class)
				// Adding localization support:
				 .i18nBundle(i18n)
				// Set default skin
				.skin(skin) 
				// Tags
				.tag(new RoundImageLmlTagProvider(), "roundImage")
				.build();
		
		parser.createView(single = this, getTemplateFile());
		
		createListeners();
	}
	
	public static void refresh() {
		// var asd = SapphireHud.parser.createView(SapphireHud.single,SapphireHud.single.getTemplateFile());
		//SapphireHud.parser.parseTemplate(SapphireHud.single.getTemplateFile());
		SapphireHud.single.getStage().getActors().clear();
		SapphireHud.parser.fillStage(SapphireHud.single.getStage(), SapphireHud.single.getTemplateFile());
	}
	
	@Override
	public String getViewId() {
		return "sheet";
	}
	
	@Override
	public FileHandle getTemplateFile() {
		return Gdx.files.absolute("F:/Users/Souchy/Desktop/Robyn/Git/r and d/ebi/PiranhaPlants/res/gdx/ui/" + getViewId() + ".lml");
	}
	
	@Override
	public FileHandle getStyleFile() {
		return Gdx.files.absolute("F:/Users/Souchy/Desktop/Robyn/Git/r and d/ebi/PiranhaPlants/res/gdx/ui/" + getViewId() + ".json");
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
