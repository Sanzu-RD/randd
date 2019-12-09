package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
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
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.commons.lapis.util.LapisUtil;
import com.souchy.randd.ebishoal.sapphire.controls.DragAndResizeListener;

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
		area.layout();
		area.appendText("\n");
		area.appendText("x : " + this.getX());
		area.appendText("\n");
		area.appendText("y : " + this.getY());
		area.appendText("\n");
		area.appendText("h " + area.getHeight() + ", " + scroll.getHeight());
//		area.appendText("bug.3style : " + area.getStyle());
//		field.setText("w :" + field.getWidth());
		
		scroll.setOverscroll(false, false);
		scroll.setFlickScroll(false);
		scroll.setFadeScrollBars(false);
		scroll.setScrollbarsOnTop(false);
		scroll.setScrollingDisabled(true, false);
		scroll.setScrollBarPositions(true, false);
		//scroll.scrollTo(0, 0, 0, 0);

		Lambda focus = () -> {
			//this.setColor(1, 1, 1, 1f);
			getStage().setScrollFocus(scroll);
		};
		Lambda unfocus = () -> {
			if(!area.hasKeyboardFocus() && !field.hasKeyboardFocus()) {
				//this.setColor(1, 1, 1, 0.3f);
				getStage().setScrollFocus(null);
			}
		};
		
		// remove area listeners
		area.clearListeners();
		// focus on hover
		LapisUtil.onHover(scroll, focus, unfocus);
		// focus on click
		LapisUtil.onClick(area, focus);
		// disable area keyboard input
		area.addListener(area.new ScrollTextAreaListener() {
			@Override
			public boolean keyTyped(InputEvent event, char character) {
				return true;
			}
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				return true;
			}
		});
		
		//LapisUtil.onHover(field, focus, unfocus);
		//LapisUtil.onClick(field, focus);
		//unfocus.call();
		
		// make chat transparent when not hovering
		var out = new Color(1, 1, 1, 0.5f);
		LapisUtil.onHover(this, Color.WHITE, out);  //should prob do the focus lambda thing from here as well and make the background=drawable("transparent") on exit 
		LapisUtil.setColor(this, out);
		
		this.addListener(new DragAndResizeListener(this));
	}

	
}
