package com.souchy.randd.commons.net.netty.bytebuf;

import com.souchy.randd.commons.net.Message;

import io.netty.buffer.ByteBuf;

/**
 * Generic byte buf message that can contain anything
 * 
 * @author Blank
 * @date 25 déc. 2019
 */
public interface BBMessage extends Message<ByteBuf, BBMessage>, 
	BBSerializer, //Serializer<ByteBuf, ByteBuf>, 
	BBDeserializer//, //Deserializer<ByteBuf, BBMessage>, 
	//Factory<Deserializer<ByteBuf, BBMessage>> // ,
// Deserializer<ByteBuf, BBMessage>,
// Factory<Deserializer<ByteBuf, BBMessage>>
{

	/** Initial capacity */
//	public int getBufferCapacity();
	public default int getBufferCapacity() {
		return 0;
	}

	@Override
	public default ByteBuf serialize(ByteBuf out) {
		return out;
	}
	@Override
	public default BBMessage deserialize(ByteBuf in) {
		return this;
	}
	
/*
	public static void writeString(ByteBuf out, String str) {
		// write string
		out.writeByte(str.getBytes().length);
		out.writeBytes(str.getBytes());
	}
	public static String readString(ByteBuf in) {
		// read string
		var bytes = new byte[in.readByte()];
		in.readBytes(bytes);
		return new String(bytes);
	}
*/
	
}
