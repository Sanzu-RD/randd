package com.souchy.randd.commons.net.netty.bytebuf.multihandlers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.commons.tealwaters.commons.Factory;

import io.netty.buffer.ByteBuf;

@SuppressWarnings("exports")
public class BBMessageFactories {

	private final Map<Integer, Factory<Deserializer<ByteBuf, BBMessage>>> factories = new HashMap<>();

	/*
	 * public void handle(BBMessage msg) { if(canHandle(msg)) {
	 * System.out.println("BBHandlerManager handling message ["+msg+"] of ID ["+msg.
	 * getID()+"]"); handlers.get(msg.getID()).handle(msg); } else {
	 * System.out.println("BBHandlerManager cant handle message ["+msg+"] of ID ["
	 * +msg.getID()+"]"); } }
	 */

	/*
	 * public BBMessage create(int id) { // jveux pas return de null, j'préfère
	 * forcer l'utilisateur de BBMessageFactory à caller lui-même le has(id) plutot
	 * que if(f!=null) if(!has(id)) return null; return factories.get(id).create();
	 * }
	 */

	/*
	 * public void test() { Factory<Deserializer<ByteBuf, BBMessage>> f = new
	 * DummyMessage(); f.create().deserialize(null);
	 * 
	 * Deserializer<ByteBuf, BBMessage> d = new DummyMessage(); d.deserialize(null);
	 * }
	 */

	public boolean has(int id) {
		return factories.containsKey(id);
	}

	public Factory<Deserializer<ByteBuf, BBMessage>> get(int id) {
		return factories.get(id);
	}

	public void add(BBMessage factory) { // Factory<BBMessage> handler) {
		if (factory == null)
			return;
		factories.put(factory.getID(), factory);
	}

	public Factory<Deserializer<ByteBuf, BBMessage>> remove(BBMessage factory) { // Factory<BBMessage> handler) {
		return factories.remove(factory.getID());
	}

	public Factory<Deserializer<ByteBuf, BBMessage>> remove(int id) {
		return factories.remove(id);
	}

	public void clear() {
		factories.clear();
	}
	
	public void populate(Stream<BBMessage> factories) {
		factories.forEach(this::add);
	}

	public void foreach(Consumer<? super Entry<Integer, Factory<Deserializer<ByteBuf, BBMessage>>>> action) {
		factories.entrySet().forEach(action);
	}

	/*
	 * public void foreach(Consumer<? super Factory<BBMessage>> action) {
	 * factories.values().forEach(action); }
	 */

}
