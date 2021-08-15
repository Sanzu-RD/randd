package com.souchy.randd.ebishoal.sapphire.ux.components;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.google.common.eventbus.Subscribe;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.souchy.randd.commons.diamond.statusevents.Handler.Reactor;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.commons.lapis.util.DragAndResizeListener;
import com.souchy.randd.ebishoal.commons.lapis.util.LapisUtil;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireComponent;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireHud;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireHudSkin;
import com.souchy.randd.moonstone.white.Moonstone;

public class Timeline extends SapphireComponent implements Reactor {

	@LmlActor("pointer")
	public Image pointer;
	
	@LmlActor("table")
	public Table table;

	@LmlActor("timer")
	public Label timer;
	
	@Override
	public String getTemplateId() {
		return "timeline";
	}

	public Timeline() {
		Moonstone.fight.statusbus.register(this);
	}
	
	@Override
	protected void onInit() {
		this.addListener(new DragAndResizeListener(this));
//		timer.setStyle(SapphireHud.styleNormal);
		refresh();
	}
	

	@Subscribe
	public void onTurnStart(TurnStartEvent e) {
		if(e.fight.future != null) e.fight.future.cancel(true);
		if(this.getStage() == null) return;
		
		e.fight.future = e.fight.timer.scheduleAtFixedRate(() -> {
			this.timer.setText(e.fight.time + "s"); // set timer text
			if(e.fight.time > 0) e.fight.time--;
			else e.fight.future.cancel(true);
		}, 0, 1, TimeUnit.SECONDS);
		
		// label + moving action
		var lbl = new VisLabel("New turn");
		var a = new MoveByAction() {
			@Override
			public void end() {
				this.actor.remove(); // enlève l'actor du stage
				this.actor = null;
			}
		};
		a.setAmountY(20);
		a.setDuration(1);
		lbl.setAlignment(Align.bottomRight);
		lbl.setPosition(this.getX() - lbl.getWidth(), this.getY() - 40);
		lbl.addAction(a);
		this.getStage().addActor(lbl);
		
		refresh();
	}

	public void refresh() {
		Gdx.app.postRunnable(this::refresh0);
	}
	
	private void refresh0() {
		var index = SapphireGame.fight.timeline.index();
//		Log.format("UI Timeline refresh %s", index);
		
		table.getChildren().forEach(a -> {
			if(a instanceof Stack == false) return;
			var stack = (Stack) a;
			var img = (Image) stack.getChild(0);
//			LapisUtil.setImage(img, getCreatureIcon(img));
			getCreatureIcon(img);
			
//			Log.format("Timeline img cell %s, %s", table.getCell(a).getColumn(), table.getCell(a).getRow());
		});

		// var c = table.getChild(e.index);
		// var stack = table.getChild(e.index);
//		table.removeActor(pointer);
		pointer.remove();
		pointer.setDrawable(LapisUtil.getImage("textures.icons.arrow_right"));
		table.getCells().forEach(c -> {
			if(c.getColumn() == 0 && c.getRow() == index) { //.index) {
				c.setActor(pointer);
			}
		});
		
		try {
//			Log.format("pointer at %s, %s", table.getCell(pointer).getColumn(), table.getCell(pointer).getRow());
		} catch (Exception e) {
			
		}
		
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
	public String getCreatureIcon(Image actor) {
		if(actor == null) {
			Log.info("Timeline.getCreatureIcon(null)");
			return "";
		}
		var actorname = actor.getName();
//		Log.info("Timeline.getCreatureIcon("+actor+") with name : " + ((Actor) actor).getName());
		if(actorname == null || actorname == "null") return "";
		int index = Integer.parseInt(actorname);
		String icon = "";
		if(SapphireGame.fight != null) {
			var creatureid = SapphireGame.fight.timeline.get(index);
			var creature = SapphireGame.fight.creatures.first(c -> c.id == creatureid);
//			icon = AssetData.creatures.get(creature.modelid).icon;
//			icon = SapphireAssets.getCreatureIconPath(icon) + "_round";
			
//			icon = SapphireAssets.getSkinPath(icon) + "_round";
//			var icon = iconpath.substring(iconpath.indexOf("textures"), iconpath.lastIndexOf(".")).replace("/", ".");
		
			actor.setDrawable(SapphireHudSkin.getIcon(creature));
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
		SapphireGame.fight.statusbus.unregister(this);
	}

//
//	@LmlAction("onselect")
//	public void onSelect(TimelineIcon source) {
//		// update status bar with new status icons
//	}

}
