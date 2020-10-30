package com.souchy.randd.deathshadows.nodes.pearl.messaging;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.deathshadows.pearl.NodeInfo;

import io.netty.buffer.ByteBuf;

/**
 * Message goes both ways
 * 
 * Node -> pearl
 * 
 * Pearl -> node returns all nodeinfos
 * 
 * 
 * @author Blank
 * @date 13 juin 2020
 */
@ID(id = 1002)
public class AskNodes implements BBMessage {

	public List<NodeInfo> nodes = new ArrayList<>();
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(nodes.size());
		for(var node : nodes) {
			node.serialize(out);
		}
		return out;
	}

	@Override
	public AskNodes deserialize(ByteBuf in) {
		int nodecount = in.readInt();
		for(int i = 0; i < nodecount; i++) {
			var node = new NodeInfo();
			nodes.add(node);
			node.deserialize(in);
		}
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new AskNodes();
	}


}