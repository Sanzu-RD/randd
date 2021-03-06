package com.souchy.randd.ebishoal.sapphire.ux.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.kotcrab.vis.ui.widget.ListView;
import com.kotcrab.vis.ui.widget.ScrollableTextArea;
import com.kotcrab.vis.ui.widget.VisList;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;
import com.souchy.randd.commons.diamond.statusevents.Handler.HandlerType;
import com.souchy.randd.commons.diamond.statusevents.Handler.Reactor;
import com.souchy.randd.commons.diamond.statusevents.other.TurnEndEvent;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent;
import com.souchy.randd.commons.diamond.statusevents.status.AddTerrainEvent;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.util.DragAndResizeListener;
import com.souchy.randd.ebishoal.commons.lapis.util.LapisUtil;
import com.souchy.randd.moonstone.commons.packets.ICM;
import com.souchy.randd.moonstone.white.Moonstone;
import com.souchy.randd.ebishoal.sapphire.controls.Commands;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireComponent;

public class Chat extends SapphireComponent implements Reactor {

	@LmlActor("field")
	public VisTextField field;

	@LmlActor("scroll")
	public VisScrollPane scroll;

//	@LmlActor("area")
//	public ScrollableTextArea area; /// HighlightTextArea
	@LmlActor("area")
	public VisList<ICM> area;
	public ListView<ICM> adf;

	public static Array<ICM> messages = new Array<>();
	static {
		messages.ordered = true;
	}

	public Chat() {
		Moonstone.bus.register(this);
		//SapphireGame.fight.bus.register(this);
		SapphireGame.fight.statusbus.register(this);
	}

	@Subscribe
	public void handleEffectEvent(Event event) {
		if(!check(event)) return;
		var msg =  event.testMessage();
		if(msg == "") return;
		addMsg(new ICM("effect", "fight", msg));
	}


	@Override
	protected void onInit() {

		area.setItems(messages);
		
		scroll.scrollTo(0, 0, 0, 0);
		scroll.setOverscroll(false, false);
		scroll.setFlickScroll(false);
		scroll.setFadeScrollBars(false);
		scroll.setScrollbarsOnTop(false);
		scroll.setScrollingDisabled(true, false);
		scroll.setScrollBarPositions(true, false);
		scroll.setScrollbarsVisible(true);
		scroll.setForceScroll(false, true);
		
		Lambda focus = () -> {
			if(getStage() != null) getStage().setScrollFocus(scroll);
		};
		
		Lambda unfocus = () -> {
			if(!area.hasKeyboardFocus() && !field.hasKeyboardFocus()) {
				if(getStage() != null) getStage().setScrollFocus(null);
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
		
		field.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Keys.ENTER) {
					sendMsg();
					return true;
				} else return super.keyDown(event, keycode);
			}
		});
	}

	@LmlAction("send")
	public void sendMsg() {
		if(field.getText().startsWith("/")) {
			Commands.process(field.getText());
		}else {
			Moonstone.writes(new ICM("default", Moonstone.user.pseudo, field.getText()));
		}
		field.clearText();
	}
	
	/** moonstone event */
	@Subscribe
	public void addMsg(ICM icm) {
//		Log.info("chat add msg");
		try {
			// write the index of the message inside its content for debug purposes
			icm.content = messages.size + " " + icm.content;
			
			messages.add(icm);
			if(messages.size > SapphireOwl.conf.general.maxChatMessages) 
				messages.removeIndex(0);
			area.setItems(messages);
			if(scroll.getScrollPercentY() == 0 || scroll.getScrollPercentY() > 0.90)
				scroll.scrollTo(0, 0, 0, 0);
		} catch(Exception e) {
			
		}
	}

//	/** fight event */
//	@Subscribe
//	public void onTurnStart(TurnStartEvent e) {
////		Log.info("chat turn start");
//		addMsg(new ICM("event", "system", String.format("fight %s turn %s sta %s", e.fight.id, e.turn, e.index)));
//	}
//
//	/** fight event */
//	@Subscribe
//	public void onTurnEnd(TurnEndEvent e) {
////		Log.info("chat turn end");
//		addMsg(new ICM("event", "system", String.format("fight %s turn %s end %s", e.fight.id, e.turn, e.index)));
//	}
	
	@Override
	public String getTemplateId() {
		return "chat";
	}

	@Override
	public void resizeScreen(int w, int h, boolean centerCam) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		Moonstone.bus.unregister(this);
//		SapphireGame.fight.bus.unregister(this);
		SapphireGame.fight.statusbus.unregister(this);
	}


	public void clearChat() {
		messages.clear();
		area.setItems(messages);
	}
	
	
}
