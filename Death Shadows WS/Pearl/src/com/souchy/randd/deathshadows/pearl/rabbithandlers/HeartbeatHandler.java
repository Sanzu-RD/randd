package com.souchy.randd.deathshadows.pearl.rabbithandlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.Heartbeat;
import com.souchy.randd.deathshadows.pearl.NodeInfo;

import io.netty.channel.ChannelHandlerContext;

public class HeartbeatHandler implements BBMessageHandler<Heartbeat> {

	@Override
	public Class<Heartbeat> getMessageClass() {
		return Heartbeat.class;
	}

	@Override
	public void handle(ChannelHandlerContext client, Heartbeat message) {
		var nodeinfo = client.channel().attr(NodeInfo.attrKey).get();
		if(nodeinfo == null) {
			Log.info("Heartbeat client has no node info");
//			client.channel().close();
			return;
		}
		nodeinfo.lastHeartbeatTime = System.currentTimeMillis();
	}
	
}
