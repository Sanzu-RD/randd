package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;

public class Timeline extends SapphireWidget {

	//@LmlActor() ?
	//public Array<TimelineIcon> creatures;
	
	@LmlActor("table")
	public Table table;
	
	
	@Override
	public String getTemplateId() {
		return "timeline";
	}


	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}
	
//	
//	@LmlAction("onselect")
//	public void onSelect(TimelineIcon source) {
//		// update status bar with new status icons
//	}
	
}
