package com.souchy.randd.commons.net.netty.bytebuf.pipehandlers;

import java.util.List;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.multihandlers.BBMessageFactories;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

// TODO je veux la même chose mais pour mes MessageBrokers sur SmoothRivers (avec rabbit mq et guava eventbus)
public class BBMessageDecoder extends ByteToMessageDecoder  {

	private final BBMessageFactories msgFactories;

	public BBMessageDecoder(BBMessageFactories factory) {
		this.msgFactories = factory;
	}

	@Override
	protected void decode(ChannelHandlerContext arg0, ByteBuf in, List<Object> out) throws Exception {
		int packetid = -1;
		String s = null;
		try {
			packetid = in.readInt();
			ByteBuf data = in.readBytes(in.readableBytes());

			if (msgFactories.has(packetid)) { // res.canHandle(packetid)){
				BBMessage msg = msgFactories.get(packetid).create().deserialize(data);
				out.add(msg);
				// out.add(res.get(packetid).handle(data));
			}

			in.release();
			data.release();
		} catch (Exception e) {
			throw new Exception("packetid = " + packetid + ", data = " + s, e);
		}
	}

}
