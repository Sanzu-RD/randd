package com.souchy.randd.commons.net.netty.bytebuf.reflect;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer;

public class BBMessageHandlerDiscoverer extends ClassDiscoverer<BBMessageHandler<BBMessage>> {

	@Override
	public boolean identify(Class<?> c) {
		return c != BBMessageHandler.class && BBMessageHandler.class.isAssignableFrom(c);
	}

}
