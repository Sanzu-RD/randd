package com.souchy.randd.moonstone.commons.packets;

import java.util.Date;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

/**
 * Instant chat message
 * @author Robyn Girardeau
 * @date 18 août 2020
 */
public class ICM implements BBMessage {
	
	public Date date;
	public String author;
	public String channel;
	public String content;
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		return null;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		return null;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return null;
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}

}
