package com.souchy.randd.moonstone.commons.packets;

import java.util.Calendar;
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
	
	public Calendar date = Calendar.getInstance();
	public String channel = "default";
	public String author = "";
	public String content = "";

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
		out.writeLong(date.getTimeInMillis());
		writeString(out, channel);
		writeString(out, author);
		writeString(out, content);
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		date.setTimeInMillis(in.readLong());
		channel = readString(in);
		author = readString(in);
		content = readString(in);
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new ICM();
	}

	@Override
	public int getBufferCapacity() {
		return 16;
	}

	public String toString() {
		return String.format("[%s:%s] (%s) %s: %s", date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), channel, author, content);
	}
	
}
