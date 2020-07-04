package com.souchy.randd.ebishoal.sapphire.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.vis.util.VisLml;
import com.kotcrab.vis.ui.VisUI;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.GlobalLML.GlobalLMLActions;
import com.souchy.randd.ebishoal.commons.lapis.gfx.LapisShader;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.sapphire.gfx.ui.roundImage.RoundImageLmlTagProvider;
import com.souchy.randd.ebishoal.sapphire.ux.Chat;
import com.souchy.randd.ebishoal.sapphire.ux.CreatureSheet;
import com.souchy.randd.ebishoal.sapphire.ux.PlayBar;
import com.souchy.randd.ebishoal.sapphire.ux.QuickOptions;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireWidget.LmlWidgets;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireWidget.SapphireWidgetTagProvider;
import com.souchy.randd.ebishoal.sapphire.ux.StatusFlow;
import com.souchy.randd.ebishoal.sapphire.ux.StatusIcon;
import com.souchy.randd.ebishoal.sapphire.ux.Timeline;

public class SapphireHud extends LapisHud {

	public static SapphireHud single;
	public static LmlParser parser;
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
	@LmlActor("timeline")
	public static Timeline timeline;
//	@LmlActor("timer")
//	public Timer timer;
	
	
	public SapphireHud() {
		single = this;
		
		//Log.info("labelstyles", skin.getAll(LabelStyle.class).keys());

//		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("truetypefont/Amble-Light.ttf"));
//		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		
		//Log.info("macro tags : " + String.join(", ", parser.getSyntax().getMacroTags().keys()));

		var batch = new SapphireBatch();
		var viewport = new ScreenViewport();

		// Batch(shader)
//		var vert = Gdx.files.internal("res/shaders/ui.vertex.glsl"); 
//		var frag = Gdx.files.internal("res/shaders/ui.fragment.glsl"); 
//		var shader = new ShaderProgram(vert, frag);
		var shader = new ShaderProgram(LapisShader.getVertexShader("ui"), LapisShader.getFragmentShader("ui"));
		batch.setShader(shader);

		this.setStage(new Stage(viewport, batch));

		// Parser(actions, i18n, skin, tags)
		//i18n = I18NBundle.createBundle(Gdx.files.internal("res/i18n/ui/bundle"));
		skin = new SapphireHudSkin(getSkinFile());

		VisUI.dispose(true);
		VisUI.load(skin);
		
		// create view
		//parser.createView(single, getTemplateFile());
		refresh();

		//createListeners();
	}
	
	public static LmlParser createParser() {
		var parser = VisLml.parser()
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
//				.attribute(new MovableLmlAttribute(), "movable")
//				.attribute(new ResizeableLmlAttribute(), "resizeable", "resizable")
//				.attribute(new ResizeBorderLmlAttribute(), "resizeBorder", "border")
				.actions("sheet", CreatureSheet.class)
				.actions("timeline", Timeline.class)
//				.actions("creaturesheet", CreatureSheet.class)
//				.actions("quickoptions", QuickOptions.class)
				//.macro(new SapphireWidgetTagProvider<>(Chat.class), ":chat")
				.build();
		parser.setStrict(false);

		// parse all templates to put them in memory
		//LapisResources.recurseFiles(Gdx.files.internal("res/ux/sapphire/components/"), f -> f.name().endsWith(".lml"), parser::parseTemplate);
		
		return parser;
	}
	
	public static void refresh() {
		//SapphireHud.single.init();
		parser = createParser();

		// var asd = SapphireHud.parser.createView(SapphireHud.single,SapphireHud.single.getTemplateFile());
		//SapphireHud.parser.parseTemplate(SapphireHud.single.getTemplateFile());
		SapphireHud.single.getStage().getActors().clear();
		//SapphireHud.parser.fillStage(SapphireHud.single.getStage(), SapphireHud.single.getTemplateFile());
		parser.createView(single, SapphireHud.single.getTemplateFile());

		refreshChat();
		refreshPlaybar();
		refreshTimeline();
		SapphireHud.single.getStage().addActor(LmlWidgets.createGroup("res/ux/sapphire/components/quickoptions.lml"));
		
		/*
		var status = LmlWidgets.createGroup("res/ux/sapphire/components/statusicon.lml");
		for(int i = 0; i < 17; i++)
			if(sheet != null) sheet.flowstatus.addActor(LmlWidgets.createGroup("res/ux/sapphire/components/statusicon.lml"));
		*/
	}
	
	public static void refreshChat() {
		SapphireHud.single.getStage().addActor(chat = LmlWidgets.createGroup("res/ux/sapphire/components/chat.lml"));
	}
	public static void refreshPlaybar() {
		SapphireHud.single.getStage().addActor(playbar = LmlWidgets.createGroup("res/ux/sapphire/components/playbar.lml"));
	}
	public static void refreshTimeline() {
		SapphireHud.single.getStage().addActor(timeline = LmlWidgets.createGroup("res/ux/sapphire/components/timeline.lml"));
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
