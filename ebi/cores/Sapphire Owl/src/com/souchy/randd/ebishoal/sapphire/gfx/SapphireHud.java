package com.souchy.randd.ebishoal.sapphire.gfx;

import javax.swing.text.AbstractDocument.Content;

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
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.tag.LmlTag;
import com.github.czyzby.lml.util.Lml;
import com.github.czyzby.lml.vis.util.VisLml;
import com.kotcrab.vis.ui.widget.VisTable;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.GlobalLML.GlobalLMLActions;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisResources;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.sapphire.gfx.ui.roundImage.RoundImageLmlTagProvider;
import com.souchy.randd.ebishoal.sapphire.ux.Chat;
import com.souchy.randd.ebishoal.sapphire.ux.CreatureSheet;
import com.souchy.randd.ebishoal.sapphire.ux.PlayBar;
import com.souchy.randd.ebishoal.sapphire.ux.StatusIcon;
import com.souchy.randd.ebishoal.sapphire.ux.*;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireWidget.LmlWidgets;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireWidget.SapphireWidgetTagProvider;

public class SapphireHud extends LapisHud {

	public static LmlParser parser;
	public static SapphireHud single;
	public static SapphireHudSkin skin;
	public static I18NBundle i18n;
	
//	@LmlActor("content")
//	public Stack content;
//	//@LmlActor("chat")
	public static Chat chat;
//	@LmlActor("playbar")
	public static PlayBar playbar;
//	@LmlActor("statusbar")
//	public StatusBar statusBar;
//	@LmlActor("timeline")
//	public Timeline timeline;
//	@LmlActor("timer")
//	public Timer timer;
	
	
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
		single = this;
		
		Log.info("labelstyles", skin.getAll(LabelStyle.class).keys());

//		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("truetypefont/Amble-Light.ttf"));
//		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		
		//Log.info("macro tags : " + String.join(", ", parser.getSyntax().getMacroTags().keys()));
		
		// create view
		//parser.createView(single, getTemplateFile());
		refresh();

		//createListeners();
	}
	
	public static LmlParser createParser() {
		var parser = Lml.parser()
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
				.tag(new SapphireWidgetTagProvider<>(StatusFlow.class), "statusbar")
				.tag(new SapphireWidgetTagProvider<>(Timeline.class), "timeline")
				.tag(new SapphireWidgetTagProvider<>(CreatureSheet.class), "creaturesheet")
				.tag(new SapphireWidgetTagProvider<>(QuickOptions.class), "quickoptions")
				.actions("creaturesheet", CreatureSheet.class)
//				.actions("quickoptions", QuickOptions.class)
				//.macro(new SapphireWidgetTagProvider<>(Chat.class), ":chat")
				.build();
		parser.setStrict(false);

		// parse all templates to put them in memory
		//LapisResources.recurseFiles(Gdx.files.internal("res/ux/sapphire/components/"), f -> f.name().endsWith(".lml"), parser::parseTemplate);
		
		return parser;
	}
	
	public static void refresh() {
		parser = createParser();
		// var asd = SapphireHud.parser.createView(SapphireHud.single,SapphireHud.single.getTemplateFile());
		//SapphireHud.parser.parseTemplate(SapphireHud.single.getTemplateFile());
		SapphireHud.single.getStage().getActors().clear();
		//SapphireHud.parser.fillStage(SapphireHud.single.getStage(), SapphireHud.single.getTemplateFile());
		parser.createView(single, SapphireHud.single.getTemplateFile());

		//testCreatureSheet();
//		var chat = LmlWidgets.createGroup("res/ux/sapphire/components/chat.lml");
//		chat.setSize(200, 200);
//		chat.setPosition(20, 15);
		SapphireHud.single.getStage().addActor(chat = LmlWidgets.createGroup("res/ux/sapphire/components/chat.lml"));
		SapphireHud.single.getStage().addActor(playbar = LmlWidgets.createGroup("res/ux/sapphire/components/playbar.lml"));
		SapphireHud.single.getStage().addActor(LmlWidgets.createGroup("res/ux/sapphire/components/creaturesheet.lml"));
		SapphireHud.single.getStage().addActor(LmlWidgets.createGroup("res/ux/sapphire/components/quickoptions.lml"));
		
		var textfield = new TextField("", skin);
		textfield.setSize(200, 30);
		textfield.setText("size : " + textfield.getWidth() + ", " + textfield.getHeight());
		textfield.setPosition(100, 800);
//		chat.removeActor(chat.field);
//		chat.add(textfield);
		
		var t = new Table();
		t.add(new TextArea("Description. Lorem ipsum dolor sit amet, \r\n" + 
				"                  consectetur adipiscing consectetur adipiscing consectetur adipiscing elit, sed do eiusmod tempor \r\n" + 
				"                  incididunt ut labore et dolore magna aliqua asdkjfnka.sdjfn kasjdnfkjsdanf ksajdbnfkjasbdf k", skin)).expand();
		t.row();
		//t.add(textfield).expand().width(200);
		t.setPosition(500, 400);
		t.pack();
		t.invalidate();
		t.debug();
		t.setSize(250, 250);
		SapphireHud.single.getStage().addActor(t);
		

		SapphireHud.single.getStage().addActor(textfield);
		
//		SapphireHud.parser.fillStage(SapphireHud.single.getStage(), Gdx.files.internal("res/ux/sapphire/chat.lml"));
//		SapphireHud.parser.fillStage(SapphireHud.single.getStage(), Gdx.files.internal("res/ux/sapphire/timer.lml"));
//		SapphireHud.parser.fillStage(SapphireHud.single.getStage(), Gdx.files.internal("res/ux/sapphire/timeline.lml"));
//		SapphireHud.parser.fillStage(SapphireHud.single.getStage(), Gdx.files.internal("res/ux/sapphire/statusbar.lml"));
//		SapphireHud.parser.fillStage(SapphireHud.single.getStage(), Gdx.files.internal("res/ux/sapphire/playbar.lml"));
//		SapphireHud.parser.fillStage(SapphireHud.single.getStage(), Gdx.files.internal("res/ux/sapphire/creaturesheet.lml"));
	}
	
	public static void testCreatureSheet() {
		var widget = LmlWidgets.createGroup("res/ux/sapphire/components/creaturesheet.lml");
		//var w = new Window("Hi Window", skin);
		//var d = new Dialog("", skin);
		//new WindowStyle();
		//w.add(widget);
		//w.setBackground("");
		//Log.info("bg = " + w.getBackground());
		//widget.setBackground(skin.getDrawable("window"));
		SapphireHud.single.getStage().addActor(widget);
	}
	
	public static class TableDrag extends Target {
		public TableDrag(Actor actor) {
			super(actor);
		}

		@Override
		public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
			return true;
		}

		@Override
		public void drop(Source source, Payload payload, float x, float y, int pointer) {
			
		}
		
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
