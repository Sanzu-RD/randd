package com.souchy.randd.moonstone.commons.packets;

import java.util.Date;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

/**
 * Instant chat message
 * @author Robyn Girardeau
 * @date 18 août 2020
 */
@ID(id = 12001)
public class ICM implements BBMessage {
	
	public Date date = new Date();
	public String channel = "default";
	public String author;
	public String content;

	public ICM() {
	}

	public ICM(String author, String msg) {
		this.author = author;
		this.content = msg;
	}
	
	public ICM(String channel, String author, String msg) {
		this.channel = channel;
		this.author = author;
		this.content = msg;
	}
	
	
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
