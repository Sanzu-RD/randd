package com.souchy.randd.deathshadows.pearl.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadow.core.DeathShadowCore;
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
		
		Class<? extends DeathShadowCore> type = null;
		
		outloop:
		for(var key : Pearl.core.nodes.keySet()) {
			for(var node : Pearl.core.nodes.get(key)) {
				if(node.id == message.id) {
					try {
						Log.info("found node with id " + node.id);
						if(node.process != null) {
							Log.info("try to destroy node id " + node.id);
							node.process.destroy();
						}
						type = key;
						break outloop;
					} catch(Exception e) {
						Log.warning("", e);
					}
				}
			}
		}

		Pearl.core.nodes.get(type).removeIf(n -> n.id == message.id);
		
	}
	
}
