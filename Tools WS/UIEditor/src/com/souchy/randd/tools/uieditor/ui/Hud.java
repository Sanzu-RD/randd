package com.souchy.randd.tools.uieditor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.github.czyzby.lml.parser.LmlParser;
import com.kotcrab.vis.ui.widget.Draggable;
import com.kotcrab.vis.ui.widget.MultiSplitPane;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer.DefaultClassDiscoverer;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;

import com.kotcrab.vis.ui.VisUI;

public class Hud extends LapisHud {
	
	public static Hud hud;
	
	private LmlParser parser;

	/**
	 * List of available actors
	 */
	public List<VisLabel> actorLibrary;
	
	/**
	 * Tree of instanced actors 
	 */
	public Object actorsTree;
	public Table table = new Table();
	public Container<Table> content = new Container<>(table);
	/**
	 * Property table for the currently selected actor
	 */
	public PropertiesTab properties = new PropertiesTab();
	
	
	public Hud() {
		hud = this;
		VisUI.load();
		LapisAssets.loadTextures(Gdx.files.internal("res/textures/"));
		
		//var parser = VisLml.parser().build();
		//parser.createView(this, getTemplateFile());
		
		actorLibrary = new List<VisLabel>(VisUI.getSkin()) {
			@Override
			protected GlyphLayout drawItem(Batch batch, BitmapFont font, int index, VisLabel item, float x, float y, float width) {
//				return super.drawItem(batch, font, index, item, x, y, width);
				String string = item.getText().toString(); //toString(item);
				return font.draw(batch, string, x, y, 0, string.length(), width, Align.left, false, "...");
			}
		};
		
		for(var packag : new String[]{ "com.kotcrab.vis.ui" }) { //"com.badlogic.gdx.scenes.scene2d.ui" }) {
			var actors = new DefaultClassDiscoverer<Actor>(Actor.class).explore(packag);
			for(var a : actors) {
				var label = new VisLabel(a.getName());
				label.addListener(new Draggable()); // new DragAdapter());
				label.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						Log.info("clicked library actor (" + label.getText() + ") : " + event.getTarget());
					}
				});
				
				actorLibrary.getItems().add(label);
			}
		}
		
		var left = new Container<>(actorLibrary);
		var right = new Container<>(properties);
		
		var split = new MultiSplitPane(false);
		split.setFillParent(true);
		split.setWidgets(left, content, right);
		split.setSplit(0, 0.2f);
		split.setSplit(1, 0.8f);

		actorLibrary.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Log.info("list select : " + actorLibrary.getSelected());
			}
		});
		
		table.debug();
		
		getStage().addActor(split);
		getStage().addListener(new KeyListener(Keys.SPACE, this::refresh));
		getStage().addListener(new KeyListener(Keys.ENTER, () -> {
			try {
				var clazz = Class.forName(actorLibrary.getSelected().getText().toString());
				Log.info("add actor : " + clazz);
				var actor = clazz.getDeclaredConstructor().newInstance();
				table.add(new ActorWrapper((Actor) actor));
			} catch (Exception e) {
				e.printStackTrace();
				Log.info("doesnt work");
			}
		}));
		
		
		
		refresh();
	}
	
	
	
	
	@Override
	public String getViewId() {
		return "uieditor";
	}
	
	public void refresh() {
//		parser = SapphireHud.createParser(); //VisLml.parser().build();
////		parser.getData().setDefaultSkin(new Skin(Gdx.files.internal("")));
//		var actor = parser.parseTemplate(Gdx.files.internal("res/ux/sapphire/components/chat.lml")).first();
//		content.setActor(actor);
	}
	
	
	public static class KeyListener extends InputListener {
		private final int keycode;
		private final Lambda l;
		public KeyListener(int keycode, Lambda l) {
			this.keycode = keycode;
			this.l = l;
		}
		@Override
		public boolean keyDown(InputEvent event, int keycode) {
			if(keycode == this.keycode) l.call();
			return super.keyDown(event, keycode);
		}
	}
	
	
}
