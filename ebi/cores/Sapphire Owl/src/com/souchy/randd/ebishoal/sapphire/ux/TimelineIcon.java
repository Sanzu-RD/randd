package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import gamemechanics.models.entities.Creature;

/**
 * Icon of a character in the timeline
 * 
 * @author Blank
 * @date 22 nov. 2019
 */
public class TimelineIcon extends SapphireWidget {

//	public TimelineIcon(Skin skin) {
//		super(skin);
//		// TODO Auto-generated constructor stub
//	}


	public Creature c;
	
	@Override
	public String getTemplateId() {
		return "timelineicon";
	}


	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}
	
}