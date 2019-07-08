package com.souchy.randd.commons.net.netty.bytebuf;

import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer;

public class BBMessageHandlerDiscoverer extends ClassDiscoverer<BBMessageHandler<BBMessage>> {

	@Override
	public boolean identify(Class<?> c) {
		return c != BBMessageHandler.class && BBMessageHandler.class.isAssignableFrom(c);
	}

}
