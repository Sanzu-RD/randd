package com.souchy.randd.commons.net.netty.bytebuf;

import com.souchy.randd.commons.net.Message;

import io.netty.buffer.ByteBuf;

public interface BBMessage extends Message<ByteBuf, BBMessage> // ,
// Deserializer<ByteBuf, BBMessage>,
// Factory<Deserializer<ByteBuf, BBMessage>>
{

	/** Initial capacity */
	public int getBufferCapacity();
	
}
