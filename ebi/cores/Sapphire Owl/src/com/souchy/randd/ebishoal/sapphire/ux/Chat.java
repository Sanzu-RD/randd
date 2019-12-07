package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table.Debug;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.kotcrab.vis.ui.Focusable;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.HighlightTextArea;
import com.kotcrab.vis.ui.widget.ScrollableTextArea;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTextArea;
import com.kotcrab.vis.ui.widget.VisTextArea.TextAreaListener;
import com.kotcrab.vis.ui.widget.VisTextField.VisTextFieldStyle;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.commons.lapis.util.LapisUtil;

public class Chat extends SapphireWidget {

	@LmlActor("scroll")
	public VisScrollPane scroll;
	
	@LmlActor("area")
	public ScrollableTextArea area; /// HighlightTextArea

	@LmlActor("field")
	public VisTextField field;

	
	@Override
	public String getTemplateId() {
		return "chat";
	}
    
//	public Chat(Skin skin) {
//		super(skin);
//		Log.info("ctor chat");
//	}

	@Override
	protected void init() {
		refresh();
	}
	
	public void refresh() {
		area.appendText("x : " + this.getX());
		area.appendText("\n");
		area.appendText("y : " + this.getY());
		area.appendText("bug.3style : " + area.getStyle());
		field.setText("w :" + field.getWidth());
		
		scroll.setOverscroll(false, false);
		scroll.setFlickScroll(false);
		scroll.setFadeScrollBars(false);
		scroll.setScrollbarsOnTop(false);
		scroll.setScrollingDisabled(true, false);
		scroll.setScrollBarPositions(true, false);
		
		Log.info("listener count : " + scroll.getListeners().size);
		
		//scroll.removeListener(scroll.getListeners().first());
		scroll.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				Log.info("click ");
//				//scroll.setStyle(VisUI.getSkin().get("default", ScrollPaneStyle.class));
//				scroll.getStyle().background = VisUI.getSkin().getDrawable("border");
//				super.clicked(event, x, y);
//			}
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				Log.info("scroll enter");
				//scroll.getStyle().background = VisUI.getSkin().getDrawable("border");
				if (getStage() != null) getStage().setScrollFocus(scroll);
				super.enter(event, x, y, pointer, fromActor);
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Log.info("scroll exit");
				//scroll.getStyle().background = VisUI.getSkin().getDrawable("window-bg");
				if(!area.hasKeyboardFocus())
					if (getStage() != null) getStage().setScrollFocus(null);
				super.exit(event, x, y, pointer, toActor);
			}
		});
		area.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Log.info("area click");
				if (getStage() != null) getStage().setScrollFocus(scroll);
				//super.clicked(event, x, y);
			}
		});
		//area.setStyle(VisUI.getSkin().get("textArea", VisTextFieldStyle.class));
		
//		area.setTextFieldListener(null);
//		//area.removeListener(area.getDefaultInputListener());
//		area.addListener(area.new TextAreaListener(){
//			@Override public boolean keyDown(InputEvent event, int keycode) { return true; } //no typing allowed
//			@Override public boolean keyTyped (InputEvent event, char character) { return true; } //no typing allowed
//			@Override
//			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//				//com.kotcrab.vis.ui.FocusManager.switchFocus(getStage(), area);
//				super.enter(event, x, y, pointer, fromActor);
//			}
//			@Override
//			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//				//com.kotcrab.vis.ui.FocusManager.switchFocus(getStage(), area);
//				super.exit(event, x, y, pointer, toActor);
//			}
//		});

		//this.setColor(1, 1, 1, 0.8f);
		//scroll.setScrollbarsVisible(true);
		
		//area.focusLost();
//		
//		this.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				// TODO Auto-generated method stub
//				super.clicked(event, x, y);
//			}
//		});

		
	}
	
//	public InputListener focusListener = new InputListener() {
//		@Override
//		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//			if (isDisabled() == false) FocusManager.switchFocus(getStage(), VisTextButton.this);
//			return false;
//		}
//		public boolean keyDown(InputEvent event, int keycode) {
//			
//		}
//		
//	};
	
	
}
