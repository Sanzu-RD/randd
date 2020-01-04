package com.souchy.randd.commons.deathebi.msg;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.jade.meta.User;

import io.netty.buffer.ByteBuf;

public class SendUser implements BBMessage {
	
	public User user;
	
	private SendUser() {}
	public SendUser(User user) {
		this.user = user;
	}

	@Override
	public ByteBuf serialize(ByteBuf out) {
		user.serialize(out);
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		user = new User();
		user.deserialize(in);
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new SendUser();
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
}
