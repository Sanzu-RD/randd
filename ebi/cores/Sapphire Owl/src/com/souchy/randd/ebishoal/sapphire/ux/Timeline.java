package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.czyzby.lml.annotation.LmlActor;

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
		// TODO Auto-generated method stub
		
	}
	
//	
//	@LmlAction("onselect")
//	public void onSelect(TimelineIcon source) {
//		// update status bar with new status icons
//	}
	
}
