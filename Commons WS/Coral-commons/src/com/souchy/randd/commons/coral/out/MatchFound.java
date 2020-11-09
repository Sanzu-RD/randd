package com.souchy.randd.commons.coral.out;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.jade.matchmaking.Lobby;

import io.netty.buffer.ByteBuf;

/**
 * When a match is found
 * 
 * @author Blank
 * @date 16 juin 2020
 */
@ID(id = 7012)
public class MatchFound implements BBMessage {
	
	public Lobby lobby;
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		try {
			lobby.serialize(out);
		}catch(Exception e) {
			Log.error("", e);
		}
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		lobby = new Lobby();
		lobby.deserialize(in);
		return this;
	}

	@Override
	public MatchFound create() {
		return new MatchFound();
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
}
