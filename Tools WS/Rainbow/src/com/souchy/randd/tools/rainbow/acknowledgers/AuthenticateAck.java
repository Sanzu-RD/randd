package com.souchy.randd.tools.rainbow.acknowledgers;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.Acknowledge;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.Authenticate;
import com.souchy.randd.tools.rainbow.handlers.AcknowledgeHandler.Acknowledger;
import com.souchy.randd.tools.rainbow.main.RainbowApp;

import javafx.application.Platform;

public class AuthenticateAck implements Acknowledger<Authenticate> {

	public AuthenticateAck() {
		
	}
	
	@Override
	public void acknowledge(Acknowledge msg) {
		Log.info("acknowledge : " + msg);
    	if(msg.accept) 
    		Platform.runLater(() -> RainbowApp.stage.setScene(RainbowApp.mainScene));
    		//RainbowApp.stage.setScene(RainbowApp.mainScene);
	}

	@Override
	public Class<Authenticate> getMsgClass() {
		return Authenticate.class;
	}
	
}
