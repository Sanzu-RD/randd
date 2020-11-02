package com.souchy.randd.deathshadows.nodes.pearl.messaging;


import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.deathshadows.pearl.NodeInfo;

import io.netty.buffer.ByteBuf;

/**
 * Ask to create a node of a specified core type
 * 
 * Pearl returns the node info of the newly created node
 * 
 * @author Blank
 * @date 13 juin 2020
 */
@ID(id = 1003)
public class AskCreate implements BBMessage {
	
	/**
	 * name of core type asked (DeathShadowCore.class.getSimpleName())
	 */
	public String askedCoreType;
	public String args = "";
	
	public NodeInfo node;
	
	public AskCreate() {}
	public AskCreate(String corename) {
		this.askedCoreType = corename;
	}
	public AskCreate(String corename, String args) {
		this.askedCoreType = corename;
		this.args = args;
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeBoolean(node != null);
		if(node != null) {
			node.serialize(out);
		} else {
			writeString(out, askedCoreType);
			writeString(out, args);
		}
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		boolean response = in.readBoolean();
		if(response) {
			node = new NodeInfo();
			node.deserialize(in);
		} else {
			askedCoreType = readString(in);
			args = readString(in);
		}
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new AskCreate();
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}

}
