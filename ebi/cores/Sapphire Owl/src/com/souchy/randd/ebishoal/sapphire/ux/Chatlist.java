package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.Align;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.FocusManager;
import com.kotcrab.vis.ui.Focusable;
import com.kotcrab.vis.ui.widget.ScrollableTextArea;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisList;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTextArea.TextAreaListener;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.util.LapisUtil;
import com.souchy.randd.ebishoal.sapphire.controls.DragAndResizeListener;

public class Chatlist extends SapphireWidget {

	@LmlActor("scroll")
	public VisScrollPane scroll;
	
//	@LmlActor("area")
//	public ScrollableTextArea area; /// HighlightTextArea
	@LmlActor("list")
	public VisList list;

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
	
	public void addMsg(String msg) {
		var lbl = new VisLabel(msg);
		lbl.setWrap(true);
		lbl.setAlignment(Align.left);
		var msgs = list.getItems();
		msgs.add(lbl);
		list.setItems(msgs);
	}
	
	public void refresh() {
//		List msgs = new List();
//		var s = new VisScrollPane(msgs);
		
		list.setAlignment(Align.bottom);
		
		addMsg("Lorem ipsum dolor sit amet");
		addMsg("Consectetur adipiscing consectetur adipiscing consectetur adipiscing elit");
		
		/*
		area.layout();
		area.appendText("\n");
		area.appendText("x : " + this.getX());
		area.appendText("\n");
		area.appendText("y : " + this.getY());
		area.appendText("\n");
		area.appendText("h " + area.getHeight() + ", " + scroll.getHeight());
//		area.appendText("bug.3style : " + area.getStyle());
//		field.setText("w :" + field.getWidth());
		*/
		
		scroll.setActor(list);
		
		scroll.setOverscroll(false, false);
		scroll.setFlickScroll(false);
		scroll.setFadeScrollBars(false);
		scroll.setScrollbarsOnTop(false);
		scroll.setScrollingDisabled(true, false);
		scroll.setScrollBarPositions(true, false);
		//scroll.scrollTo(0, 0, 0, 0);
		scroll.pack();
		
		
		Lambda focus = () -> {
			//this.setColor(1, 1, 1, 1f);
			getStage().setScrollFocus(scroll);
		};
		Lambda unfocus = () -> {
			if(!list.hasKeyboardFocus() && !field.hasKeyboardFocus()) {
				//this.setColor(1, 1, 1, 0.3f);
				getStage().setScrollFocus(null);
			}
		};

		// remove area listeners
		list.clearListeners();
		// focus on hover
		LapisUtil.onHover(scroll, focus, unfocus);
		// focus on click
//		LapisUtil.onClick(list, () -> FocusManager.switchFocus(getStage(), list));
		// disable area keyboard input
		list.addListener(new InputListener() {
			@Override
			public boolean keyTyped(InputEvent event, char character) {
				Log.info("list keytyped");
				return true;
			}
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				Log.info("list keydown");
				return true;
			}
		});
		// disable area keyboard input
		scroll.addListener(new InputListener() {
			@Override
			public boolean keyTyped(InputEvent event, char character) {
				Log.info("scroll keytyped");
				return true;
			}
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				Log.info("scroll keydown");
				return true;
			}
		});
		
		//LapisUtil.onHover(field, focus, unfocus);
		//LapisUtil.onClick(field, focus);
		//unfocus.call();
		
		// make chat transparent when not hovering
		var outColor = new Color(1, 1, 1, 0.5f);
//		LapisUtil.onHover(this, Color.WHITE, outColor);  //should prob do the focus lambda thing from here as well and make the background=drawable("transparent") on exit 
		LapisUtil.onHover(this, () -> scroll.setColor(Color.WHITE), () -> scroll.setColor(outColor));
		LapisUtil.setColor(scroll, outColor);
		
		this.addListener(new DragAndResizeListener(this));
	}

	
}
