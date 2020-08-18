package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.github.czyzby.lml.annotation.LmlActor;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.ebishoal.commons.lapis.util.LapisUtil;

public class StatusIcon extends SapphireWidget { //Stack {
//
//	public StatusIcon(Skin skin) {
//		super(skin);
//		// TODO Auto-generated constructor stub
//	}

	public Status status;

	@LmlActor("icon")
	public Image icon;
	@LmlActor("border")
	public Image border;
	@LmlActor("stacks")
	public Label stacks;
	@LmlActor("duration")
	public Label duration;
	
	public StatusIcon() {
//		status = s;
//		this.add(icon = new Image(LapisUtil.getImage("textures.statuses." + status.getIconName())));
//		this.add(border = new Image(LapisUtil.getImage("textures.borders.blackborder")));
//		var t = new Table();
//		
//		this.add(stacks = new Label("", VisUI.getSkin()));
//		this.add(duration = new Label("", VisUI.getSkin()));

//		refresh(s);
	}

	@Override
	protected void init() {
		refresh(null);
	}

	public StatusIcon refresh(Status s) {
		if(s != null)
			this.status = s;
		LapisUtil.setImage(icon, "textures.statuses." + "1"); // + status.getIconName());
		LapisUtil.setImage(border, "textures.borders.blackborder");
//		LapisUtil.setText(stacks, status.stacks + "");
//		LapisUtil.setText(duration, status.duration + "");
		stacks.setText("1"); //status.stacks + "");
		duration.setText("2"); //status.duration + "");
		
		//var tip = new Tooltip<Actor>()
		
		return this;
	}


	@Override
	public String getTemplateId() {
		return "statusicon";
	}
	

//	/** 
//	 * statusname = i18n.status.{id} 
//	 * icon = textures.statuses.{id}
//	 */
//	public int getStatusModelID() {
//		return 0;
//	}
//	
//	public int getStatusStacks() {
//		return status.stacks;
//	}
//	
//	public int getStatusDuration() {
//		return status.duration;
//	}
//	
//	public int getStatusEffectCount() {
//		return 0; //TODO status.effects;
//	}
//	
//	public String getStatusEffect(int statusid, int effectid) {
//		return ""; //TODO status.effects.get(effectid).i18nKey();
//	}
	
}
