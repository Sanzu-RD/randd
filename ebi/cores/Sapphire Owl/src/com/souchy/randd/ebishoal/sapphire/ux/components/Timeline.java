package com.souchy.randd.ebishoal.sapphire.ux.components;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.commons.diamond.statics.Constants;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.commons.lapis.util.DragAndResizeListener;
import com.souchy.randd.ebishoal.commons.lapis.util.LapisUtil;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireAssets;
import com.souchy.randd.ebishoal.sapphire.gfx.ui.roundImage.RoundImage;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireComponent;
import com.souchy.randd.moonstone.commons.packets.ICM;
import com.souchy.randd.moonstone.white.Moonstone;

public class Timeline extends SapphireComponent {

	@LmlActor("table")
	public Table table;

	@LmlActor("timer")
	public Label timer;
	/** current time remaining */
//	public int time;
	
	@Override
	public String getTemplateId() {
		return "timeline";
	}


	public Timeline() {
		Moonstone.fight.bus.register(this);
	}
	
	@Override
	protected void onInit() {
//		Log.info("Timeline.init()");
		this.addListener(new DragAndResizeListener(this));
		refresh();
	}
	

	@Subscribe
	public void onTurnStart(TurnStartEvent e) {
		if(e.fight.future != null) e.fight.future.cancel(true);
//		time = Constants.baseTimePerTurn;
		e.fight.future = e.fight.timer.scheduleAtFixedRate(() -> {
			//Moonstone.bus.post(new ICM("event", "timeline", "time " + time));
			timer.setText(e.fight.time + "s");
			if(e.fight.time > 0) e.fight.time--;
			else e.fight.future.cancel(true);
		}, 0, 1, TimeUnit.SECONDS);
	}

	
	public void refresh() {
		Log.info("UI Timeline refresh");
		table.getChildren().forEach(a -> {
			if(a instanceof Stack == false) return;
			var stack = (Stack) a;
			var img = (RoundImage) stack.getChild(0);
			LapisUtil.setImage(img, getCreatureIcon(img));
		});
//		table.clear();
//		if(SapphireGame.fight == null) return;
//		new ArrayList<>(SapphireGame.fight.timeline).forEach(id -> {
//			var creature = SapphireGame.fight.creatures.first(c -> c.id == id);
//			var icon = AssetData.creatures.get(creature.modelid).icon;
//			icon = SapphireAssets.getCreatureIconPath(icon);
//			icon = SapphireAssets.getSkinPath(icon) + "_round";
//			Log.info("UI Timeline refresh add creatrue " + creature + " icon " + icon);
//			this.table.add(new RoundImage(LapisUtil.getImage(icon)));
//			this.table.row();
//		});
	}

	@LmlAction("getCreatureCount")
	public int getCreatureCount() {
		int count = 0;
		if(SapphireGame.fight != null) {
			 count = SapphireGame.fight.timeline.size();
		}
//		Log.info("Timeline.getCreatureCount() = " + count);
		return count;
	}

	@LmlAction("getCreatureName")
	public String getCreatureName(Object actor) {
		if(actor == null) {
			Log.info("Timeline.getCreatureName(null)");
			return "";
		}
		var actorname = ((Actor) actor).getName();
//		Log.info("Timeline.getCreatureName("+actor+") with name : " + actorname);
		if(actorname == null || actorname == "null") return "";
		int index = Integer.parseInt(actorname);
		String name = "";
		if(SapphireGame.fight != null) {
			var creatureid = SapphireGame.fight.timeline.get(index);
			var creature = SapphireGame.fight.creatures.first(c -> c.id == creatureid);
			I18NBundle i18n = LapisAssets.assets.get("res/i18n/creatures/bundle", I18NBundle.class);
			name = i18n.get("creature." + creature.modelid + ".name");
		}
//		Log.info("Timeline.getCreatureName("+index+") = " + name);
		return name; // "Sungjin";
	}

	@LmlAction("getCreatureIcon")
	public String getCreatureIcon(Object actor) {
		if(actor == null) {
			Log.info("Timeline.getCreatureIcon(null)");
			return "";
		}
		var actorname = ((Actor) actor).getName();
//		Log.info("Timeline.getCreatureIcon("+actor+") with name : " + ((Actor) actor).getName());
		if(actorname == null || actorname == "null") return "";
		int index = Integer.parseInt(actorname);
		String icon = "";
		if(SapphireGame.fight != null) {
			var creatureid = SapphireGame.fight.timeline.get(index);
			var creature = SapphireGame.fight.creatures.first(c -> c.id == creatureid);
			icon = AssetData.creatures.get(creature.modelid).icon;
			icon = SapphireAssets.getCreatureIconPath(icon) + "_round";
//			icon = SapphireAssets.getSkinPath(icon) + "_round";
//			var icon = iconpath.substring(iconpath.indexOf("textures"), iconpath.lastIndexOf(".")).replace("/", ".");
		}
//		Log.info("Timeline.getCreatureIcon("+index+") = " + icon);
		return icon;
	}


	@Override
	public void resizeScreen(int w, int h, boolean centerCam) {
		// TODO Auto-generated method stub

	}


	@Override
	public void dispose() {
		SapphireGame.fight.bus.unregister(this);
	}

//
//	@LmlAction("onselect")
//	public void onSelect(TimelineIcon source) {
//		// update status bar with new status icons
//	}

}
