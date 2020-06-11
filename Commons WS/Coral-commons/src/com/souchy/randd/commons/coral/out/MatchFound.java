package com.souchy.randd.commons.coral.out;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.jade.mm.Lobby;

import io.netty.buffer.ByteBuf;

@ID(id = 7012)
public class MatchFound implements BBMessage {
	
	public Lobby lobby;
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		lobby.serialize(out);
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		lobby = new Lobby();
		lobby.deserialize(in);
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new MatchLost();
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
}
