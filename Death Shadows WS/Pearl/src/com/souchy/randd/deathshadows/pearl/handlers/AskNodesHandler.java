package com.souchy.randd.deathshadows.pearl.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadow.core.smoothrivers.AskIdentifications;
import com.souchy.randd.deathshadow.core.smoothrivers.SmoothRivers;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.AskNodes;
import com.souchy.randd.deathshadows.pearl.NodeInfo;
import com.souchy.randd.deathshadows.pearl.main.Pearl;

import io.netty.channel.ChannelHandlerContext;

public class AskNodesHandler implements BBMessageHandler<AskNodes> {

	@Override
	public Class<AskNodes> getMessageClass() {
		return AskNodes.class;
	}

	@Override
	public void handle(ChannelHandlerContext client, AskNodes message) {
//		Log.info("Pearl AskNodesHandler");
		
		SmoothRivers.send("nodes", new AskIdentifications());
		
		// just set the nodes and send the message back
		for(var k : Pearl.core.nodes.values()) 
			message.nodes.addAll(k);
		
		Log.info("Pearl send node infos " + String.join(", ", message.nodes.stream().map(n -> n.toString()).collect(Collectors.toList())));
		
		client.channel().writeAndFlush(message);
	}
	
}
