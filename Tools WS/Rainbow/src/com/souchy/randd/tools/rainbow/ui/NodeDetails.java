package com.souchy.randd.tools.rainbow.ui;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.tools.rainbow.main.Rainbow;
import com.souchy.randd.tools.rainbow.ui.events.RefreshEvent;

public class NodeDetails {
	
	public NodeDetails() {
		Rainbow.core.bus.register(this);
	}

	@Subscribe
	public void onRefresh(RefreshEvent r) {
//		if(r.node == this) {
//			
//		}
	}
	
}
