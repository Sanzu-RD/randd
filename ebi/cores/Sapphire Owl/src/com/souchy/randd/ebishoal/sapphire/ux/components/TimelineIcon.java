package com.souchy.randd.ebishoal.sapphire.ux.components;

import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireComponent;

/**
 * Icon of a character in the timeline
 * 
 * @author Blank
 * @date 22 nov. 2019
 */
public class TimelineIcon extends SapphireComponent {

	public Creature c;
	
	@Override
	public String getTemplateId() {
		return "timelineicon";
	}


	@Override
	protected void onInit() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resizeScreen(int w, int h, boolean centerCam) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
