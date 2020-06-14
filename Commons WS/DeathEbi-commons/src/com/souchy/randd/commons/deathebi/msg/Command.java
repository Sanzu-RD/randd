package com.souchy.randd.commons.deathebi.msg;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

@ID(id = 1003)
public class Command implements BBMessage {

	public int id;
	public String command;
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(id);
		writeString(out, command);
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		id = in.readInt();
		command = readString(in);
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new Command();
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
}
