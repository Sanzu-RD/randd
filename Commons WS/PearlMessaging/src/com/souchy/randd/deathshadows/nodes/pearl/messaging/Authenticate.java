package com.souchy.randd.deathshadows.nodes.pearl.messaging;

import java.nio.charset.Charset;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

@ID(id = 1004)
public class Authenticate implements BBMessage {
	
	public String user;
	public String pass;
	
	public Authenticate() {
	}
	
	public Authenticate(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(user.length());
		out.writeCharSequence(user, Charset.defaultCharset());
		out.writeInt(pass.length());
		out.writeCharSequence(pass, Charset.defaultCharset());
		return out;
	}
	
	@Override
	public BBMessage deserialize(ByteBuf in) {
		int userLength = in.readInt();
		user = in.readCharSequence(userLength, Charset.defaultCharset()).toString();
		int passLength = in.readInt();
		pass = in.readCharSequence(passLength, Charset.defaultCharset()).toString();
		return this;
	}
	
	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new Authenticate();
	}
	
	@Override
	public int getBufferCapacity() {
		return pass.length() + user.length() + 4 + 4;
	}
	
	@Override
	public String toString() {
		return this.getClass().getCanonicalName() + " { " + user + ", " + pass + " }"; // super.toString();
	}
	
}
