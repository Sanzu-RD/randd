package com.souchy.randd.ebishoal.sapphire.ux.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.google.common.eventbus.Subscribe;
import com.kotcrab.vis.ui.widget.ListView;
import com.kotcrab.vis.ui.widget.ScrollableTextArea;
import com.kotcrab.vis.ui.widget.VisList;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.ebishoal.commons.lapis.util.DragAndResizeListener;
import com.souchy.randd.ebishoal.commons.lapis.util.LapisUtil;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireComponent;

public class Chat extends SapphireComponent {

	@LmlActor("field")
	public VisTextField field;
	
	@LmlActor("scroll")
	public VisScrollPane scroll;
	
//	@LmlActor("area")
//	public ScrollableTextArea area; /// HighlightTextArea
	@LmlActor("area")
	public VisList<ChatMessage> area;
	public ListView<ChatMessage> adf;

	public Array<ChatMessage> messages = new Array<>();
	
	public static class ChatMessage {
		public Date date = new Date();
		public String author; // ObjectId author
		public String channel = "default";
		public String content = "";
		public ChatMessage(String author, String msg) {
			this.author = author;
			this.content = msg;
		}
	}
	

	@Override
	protected void onInit() {
//		area.layout();
//		area.appendText("\n");
//		area.appendText("x : " + this.getX());
//		area.appendText("\n");
//		area.appendText("y : " + this.getY());
//		area.appendText("\n");
//		area.appendText("h " + area.getHeight() + ", " + scroll.getHeight());
		
		messages.ordered = true;
		messages.add(new ChatMessage("souchy", "hey chat"));
		messages.add(new ChatMessage("souchy", "hey chat"));
		area.setItems(messages);
		
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
//		area.addListener(area.new ScrollTextAreaListener() {
//			@Override
//			public boolean keyTyped(InputEvent event, char character) {
//				return true;
//			}
//			@Override
//			public boolean keyDown(InputEvent event, int keycode) {
//				return true;
//			}
//		});
		
		
		//LapisUtil.onHover(field, focus, unfocus);
		//LapisUtil.onClick(field, focus);
		//unfocus.call();
		
		// make chat transparent when not hovering
		var out = new Color(1, 1, 1, 0.5f);
		LapisUtil.onHover(this, Color.WHITE, out);  //should prob do the focus lambda thing from here as well and make the background=drawable("transparent") on exit 
		LapisUtil.setColor(this, out);
		
		this.addListener(new DragAndResizeListener(this));
	}
	
	@LmlAction("send")
	public void sendMsg() {
		
	}
	
	@Subscribe
	public void receiveMsg() {
		
	}
	
	
	@Override
	public String getTemplateId() {
		return "chat";
	}

	@Override
	public void resizeScreen(int w, int h, boolean centerCam) {
		// TODO Auto-generated method stub
		
	}


	
}
