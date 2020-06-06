package com.souchy.randd.commons.net.netty.bytebuf.multihandlers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;

import io.netty.channel.ChannelHandlerContext;


@SuppressWarnings({ "unchecked", "rawtypes" })
public class BBMessageHandlers { // extends ResponsibilityManager<BBMessage, BBMessage> {

	private final Map<Integer, BBMessageHandler<BBMessage>> handlers = new HashMap<>();

	public boolean canHandle(BBMessage msg) {
//		Log.info("BBMessageHandlers can handle ? " + msg.getClass() + " : " + msg);
		return handlers.containsKey(msg.getID());
	}

	public void handle(ChannelHandlerContext ctx, BBMessage msg) {
		if (canHandle(msg)) {
			Log.info("BBMessageHandlers handling message [" + msg + "] of ID [" + msg.getID() + "]");
			handlers.get(msg.getID()).handle(ctx, msg);
		} else {
			Log.error("BBMessageHandlers cant handle message [" + msg + "] of ID [" + msg.getID() + "]");
		}
	}

	public void add(BBMessageHandler handler) {
		if (handler == null)
			return;
		handlers.put(handler.getID(), (BBMessageHandler<BBMessage>) handler);
	}

	public BBMessageHandler<BBMessage> remove(BBMessageHandler handler) {
		return handlers.remove(handler.getID());
	}

	public BBMessageHandler<BBMessage> remove(int id) {
		return handlers.remove(id);
	}

	public void clear() {
		handlers.clear();
	}

	public void foreach(Consumer<? super BBMessageHandler<BBMessage>> action) {
		handlers.values().forEach(action);
	}

	public void populate(Stream<BBMessageHandler<BBMessage>> handlers) {
		handlers.forEach(this::add);
	}


	/*
	 * @Override 
	 * public void configure() { 
	 * 
	 * }
	 */

}
