package com.souchy.randd.ebishoal.sapphire.ux;

import com.github.czyzby.lml.annotation.LmlAction;

public class Timeline extends SapphireWidget {

	@Override
	public String getTemplateId() {
		return "timeline";
	}
	
	@LmlAction("onselect")
	public void onSelect(TimelineIcon source) {
		// update status bar with new status icons
	}
	
}
