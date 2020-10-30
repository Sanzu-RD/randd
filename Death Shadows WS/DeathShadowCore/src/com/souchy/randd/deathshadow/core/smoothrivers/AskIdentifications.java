package com.souchy.randd.deathshadow.core.smoothrivers;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.deathshadow.core.DeathShadowCore;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

@ID(id = 1009)
public class AskIdentifications implements BBMessage {

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new AskIdentifications();
	}
	
	
	public static class AskIdentificationHandler implements BBMessageHandler<AskIdentifications> {
		@Override
		public Class<AskIdentifications> getMessageClass() {
			return AskIdentifications.class;
		}

		@Override
		public void handle(ChannelHandlerContext client, AskIdentifications message) {
//			client.writeAndFlush(new SelfIdentify(DeathShadowCore.deathShadowCore));
			SmoothRivers.sendPearl(new SelfIdentify(DeathShadowCore.deathShadowCore));
		}
	}
	
	
}
