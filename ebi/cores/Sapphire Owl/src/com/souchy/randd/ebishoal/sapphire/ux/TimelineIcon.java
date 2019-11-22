package com.souchy.randd.ebishoal.sapphire.ux;

import gamemechanics.models.entities.Creature;

/**
 * Icon of a character in the timeline
 * 
 * @author Blank
 * @date 22 nov. 2019
 */
public class TimelineIcon extends SapphireWidget {

	public Creature c;
	
	@Override
	public String getTemplateId() {
		return "timelineicon";
	}
	
}
