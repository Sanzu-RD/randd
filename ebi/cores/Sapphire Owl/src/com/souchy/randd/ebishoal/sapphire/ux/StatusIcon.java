package com.souchy.randd.ebishoal.sapphire.ux;

import data.new1.timed.Status;

public class StatusIcon extends SapphireWidget {

	public Status status;
	
	public StatusIcon(Status s) {
		status = s;
	}


	@Override
	public String getTemplateId() {
		return "statusicon";
	}
	

	/** 
	 * statusname = i18n.status.{id} 
	 * icon = textures.statuses.{id}
	 */
	public int getStatusModelID() {
		return 0;
	}
	
	public int getStatusStacks() {
		return status.stacks;
	}
	
	public int getStatusDuration() {
		return status.duration;
	}
	
	public int getStatusEffectCount() {
		return 0; //TODO status.effects;
	}
	
	public String getStatusEffect(int statusid, int effectid) {
		return ""; //TODO status.effects.get(effectid).i18nKey();
	}
	
}
