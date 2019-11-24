package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.github.czyzby.lml.annotation.LmlActor;

import data.new1.timed.Status;

public class StatusIcon extends SapphireWidget {

	public Status status;

	@LmlActor("icon")
	public Image icon;
	@LmlActor("border")
	public Image border;
	@LmlActor("stacks")
	public Label stacks;
	@LmlActor("duration")
	public Label duration;
	
//	public StatusIcon(Status s) {
//		status = s;
//	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

	public StatusIcon refresh(Status s) {
		if(s != null)
			this.status = s;
		setImage(icon, "textures.statuses." + status.getIconName());
		setImage(border, "textures.borders.blackborder");
		setText(stacks, status.stacks + "");
		setText(duration, status.duration + "");
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
