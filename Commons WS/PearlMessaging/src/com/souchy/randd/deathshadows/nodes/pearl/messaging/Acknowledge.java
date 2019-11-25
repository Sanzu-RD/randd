package com.souchy.randd.deathshadows.nodes.pearl.messaging;

import java.nio.charset.Charset;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

@ID(id = 1005)
public class Acknowledge implements BBMessage {
	
	public int msgID;
	public boolean accept;
	
	public Acknowledge() {
	}
	
	public <T extends BBMessage> Acknowledge(BBMessage msg, boolean accept) {
		this.msgID = msg.getID();
		this.accept = accept;
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeBoolean(accept);
		out.writeInt(msgID);
		return out;
	}
	
	@Override
	public BBMessage deserialize(ByteBuf in) {
		accept = in.readBoolean();
		msgID = in.readInt();
		return this;
	}
	
	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new Acknowledge();
	}
	
	@Override
	public int getBufferCapacity() {
		return 1;
	}
	
	@Override
	public String toString() {
		return this.getClass().getName() + " { " + msgID + ", " + accept + " }"; // super.toString();
	}
	
}
