package com.souchy.randd.moonstone.commons.packets.s2c;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

@ID(id = 11001)
public class AuthResponse implements BBMessage {

	public boolean accepted;
	
	public AuthResponse() {
		
	}
	
	public AuthResponse(boolean accepted) {
		this.accepted = accepted;
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		return out.writeBoolean(accepted);
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		this.accepted = in.readBoolean();
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new AuthResponse(false);
	}

	@Override
	public int getBufferCapacity() {
		return 1;
	}

}