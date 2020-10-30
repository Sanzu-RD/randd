package com.souchy.randd.deathshadows.pearl.rabbithandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadow.core.smoothrivers.SelfIdentify;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.AskNodes;
import com.souchy.randd.deathshadows.pearl.NodeInfo;
import com.souchy.randd.deathshadows.pearl.main.Pearl;

import io.netty.channel.ChannelHandlerContext;

public class SelfIdentifyHandler implements BBMessageHandler<SelfIdentify> {

	@Override
	public void handle(ChannelHandlerContext ctx, SelfIdentify message) {
//		Log.info("SelfIdentifyHandler handling message [" + message + "]");
		
		var node = new NodeInfo();
		node.id = message.pid; // Pearl.core.tempIdmaker++;
		node.port = message.port;
		node.type = message.clazz;
		node.launchTime = System.currentTimeMillis();
		node.lastHeartbeatTime = System.currentTimeMillis();
		// node.process = proc;
		// nodes.add(node);
		node.cpu = message.cpu;
		node.ram = message.ram;
		
		var op = ProcessHandle.of(node.id);
		if(op.isPresent()) {
			node.process = op.get();
		}

		
		// check si le node était déjà connu dans la liste
		var nodes = Pearl.core.nodes.get(node.type);
		boolean replaced = false;
		for(int i = 0; i < nodes.size(); i++) {
			if(nodes.get(i).id == node.id) {
				Log.info("Pearl SelfIdentifyHandler replace node " + node.id + " @ " + node.port);
				nodes.set(i, node); // remplace le node
				replaced = true;
				break;
			}
		}
		if(!replaced) {
			Log.info("Pearl SelfIdentifyHandler add node " + node.id + " @ " + node.port);
			 nodes.add(node); // add nouveau node
		}
		
		Log.info("Pearl SelfIdentifyHandler "
				+ String.join(", ", Pearl.core.nodes.values().stream().flatMap(s -> s.stream()).map(n -> n.toString()).collect(Collectors.toList())));
		
		// envoie un refresh à rainbow
		 var askNodes = new AskNodes();
		 for(var k : Pearl.core.nodes.values())
			askNodes.nodes.addAll(k);
		Pearl.core.server.broadcast(askNodes);
	}

	@Override
	public Class<SelfIdentify> getMessageClass() {
		return SelfIdentify.class;
	}

}
