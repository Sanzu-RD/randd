package com.souchy.randd.commons.net.netty.bytebuf.pipehandlers;


import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToByteEncoder;

@Sharable
public class BBMessageEncoder extends MessageToByteEncoder<BBMessage> {
	
	@Override
	/**
	 * Override ceci pour donner une capacit� initiale � l'allocation du buffer utilis� dans {@link #encode(ChannelHandlerContext, I, ByteBuf)}
	 */
	protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, BBMessage msg, boolean preferDirect) throws Exception {
		// Même code que l'implémentation par default, mais avec une capacité initiale
		int cap = msg.getBufferCapacity();
		if (preferDirect) {
			return ctx.alloc().ioBuffer(cap);
		} else {
			return ctx.alloc().heapBuffer(cap);
		}
	}

	@Override
	protected void encode(ChannelHandlerContext arg0, BBMessage msg, ByteBuf out) throws Exception {
		out.writeInt(msg.getID());
		msg.serialize(out);
	}

}
