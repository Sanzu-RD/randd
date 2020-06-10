package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.commons.lapis.util.LapisUtil;
import com.souchy.randd.ebishoal.sapphire.confs.AssetConfs;
import com.souchy.randd.ebishoal.sapphire.controls.DragAndResizeListener;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireAssets;
import com.souchy.randd.ebishoal.sapphire.gfx.ui.roundImage.RoundImage;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;

public class Timeline extends SapphireWidget {

	//@LmlActor() ?
	//public Array<TimelineIcon> creatures;
//	
//	public Timeline(Skin skin) {
//		super(skin);
//		// TODO Auto-generated constructor stub
//	}


	@LmlActor("table")
	public Table table;
	
	
	@Override
	public String getTemplateId() {
		return "timeline";
	}


	@Override
	protected void init() {
//		Log.info("Timeline.init()");
		this.addListener(new DragAndResizeListener(this));
		refresh();
	}
	
	public void refresh() {
		table.getChildren().forEach(a -> {
			var stack = (Stack) a;
			var img = (RoundImage) stack.getChild(0);
			LapisUtil.setImage(img, getCreatureIcon(img)); 
		});
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
			var creature = SapphireGame.fight.timeline.get(index);
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
			var creature = SapphireGame.fight.timeline.get(index);
			icon = AssetConfs.creatures.get(creature.modelid).icon;
			icon = SapphireAssets.getCreatureIconPath(icon);
			icon = SapphireAssets.getSkinPath(icon) + "_round";
//			var icon = iconpath.substring(iconpath.indexOf("textures"), iconpath.lastIndexOf(".")).replace("/", ".");
		}
//		Log.info("Timeline.getCreatureIcon("+index+") = " + icon);
		return icon;
	}
	
//	
//	@LmlAction("onselect")
//	public void onSelect(TimelineIcon source) {
//		// update status bar with new status icons
//	}
	
}
