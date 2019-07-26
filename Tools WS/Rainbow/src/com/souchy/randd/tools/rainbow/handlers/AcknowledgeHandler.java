package com.souchy.randd.tools.rainbow.handlers;

import java.util.HashMap;
import java.util.Map;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.commons.ClassInstanciator;
import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.Acknowledge;
import com.souchy.randd.tools.rainbow.main.Rainbow;

import io.netty.channel.ChannelHandlerContext;

@SuppressWarnings("unchecked")
public class AcknowledgeHandler implements BBMessageHandler<Acknowledge> {

	private static final Map<Integer, Acknowledger<?>> acknowledgers = new HashMap<>();
	static {
		var path = Rainbow.class.getPackageName();
		path = path.substring(0, path.lastIndexOf("."));
		var acks = new AcknowledgerDiscoverer().explore(path);
		Log.info("trying to register acks from path : " + path);
		ClassInstanciator.list(acks).forEach(c -> {
			Log.info("register ack " + c.getClass().getName());
			acknowledgers.put(((Class<BBMessage>) c.getMsgClass()).getAnnotation(ID.class).id(), c);
		});
	}

	@Override
	public void handle(ChannelHandlerContext ctx, Acknowledge message) {
		if(acknowledgers.containsKey(message.msgID)) {
			acknowledgers.get(message.msgID).acknowledge(message);
		} else {
			Log.error("Unhandled acknowledge message : " + message.toString());
		}
	}

	@Override
	public Class<Acknowledge> getMessageClass() {
		return Acknowledge.class;
	}
	
	public static interface Acknowledger<T extends BBMessage> {
		public void acknowledge(Acknowledge msg);
		public Class<T> getMsgClass();
	}
	
	public static class AcknowledgerDiscoverer extends ClassDiscoverer<Acknowledger<?>> {
		@Override
		public boolean identify(Class<?> c) {
			return c != Acknowledger.class && Acknowledger.class.isAssignableFrom(c);
		}
	}
	
}
