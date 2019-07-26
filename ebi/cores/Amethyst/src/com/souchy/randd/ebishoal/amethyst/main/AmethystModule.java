package com.souchy.randd.ebishoal.amethyst.main;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.modules.api.EntryPoint;
import com.souchy.randd.modules.node.NodeInformation;
import com.souchy.randd.modules.node.NodeModule;

import javafx.application.Platform;

public class AmethystModule extends NodeModule {

	
	@Override
	public void askUpdate() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void dispose() {
		Log.info("Disposing Amethyst Module");
		Platform.exit();
		super.dispose();
		Log.info("Disposed Amethyst Module");
		// TODO dispose amethyst threads for javafx etc
	}
	
	
	@Override
	public void enter(EntryPoint entry, NodeInformation info) {
		try {
			Amethyst.module = this;
			Amethyst.main(null);
		} catch (Exception e) {
			Log.warning("Amethyst module enter exception : ", e);
		}
	}
	
	
}
