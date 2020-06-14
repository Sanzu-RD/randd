package com.souchy.randd.deathshadows.pearl.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.AskKillNode;
import com.souchy.randd.deathshadows.pearl.main.Pearl;

import io.netty.channel.ChannelHandlerContext;

public class AskKillNodeHandler implements BBMessageHandler<AskKillNode> {

	@Override
	public Class<AskKillNode> getMessageClass() {
		return AskKillNode.class;
	}

	@Override
	public void handle(ChannelHandlerContext client, AskKillNode message) {
		Log.info("Pearl handle kill node " + message.id);
		for(var node : Pearl.core.nodes) {
			if(node.id == message.id) {
				try {
					node.process.destroy();
				} catch(Exception e) {
					Log.warning("", e);
				}
			}
		}
		Pearl.core.nodes.removeIf(n -> n.id == message.id);
	}
	
}
