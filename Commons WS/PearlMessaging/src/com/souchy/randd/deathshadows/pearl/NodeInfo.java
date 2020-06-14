package com.souchy.randd.deathshadows.pearl;

import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadow.core.DeathShadowCore;

import io.netty.buffer.ByteBuf;
import io.netty.util.AttributeKey;

/**
 * Information about a node application on the server
 * 
 * @author Blank
 * @date 13 juin 2020
 */
public class NodeInfo implements BBSerializer, BBDeserializer {
	
	public static final AttributeKey<NodeInfo> attrKey = AttributeKey.newInstance("pearl.nodeinfo");
	
	/*
	 * Node id
	 */
	public int id;
	/**
	 * Core class / type
	 */
	public Class<? extends DeathShadowCore> type;
	/**
	 * Node server ip
	 */
	public String ip;
	/**
	 * Node server port 
	 */
	public int port;
	/**
	 * Number of clients connected to this server
	 */
	public int clientCount;
	/**
	 * Moment when the core was started
	 */
	public long launchTime;
	/**
	 * Moment when the last heartbeat was received
	 */
	public long lastHeartbeatTime;
	/**
	 * Physical application process so we're able to kill it
	 */
	public Process process;
	
	public String url() {
		return ip + ":" + port;
	}
	
	@Override
	public String toString() {
		if(type == null) return "null" + "#" + id;
		return type.getSimpleName() + "#" + id;
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(id);
		Log.info("nodeinfo serialize type name " + type.getName());
		writeString(out, type.getName());
		writeString(out, ip);
		out.writeInt(port);
		out.writeInt(clientCount);
		out.writeLong(launchTime);
		out.writeLong(lastHeartbeatTime);
		//out.writeLong(process.pid());
		return out;
	}
	@Override
	public BBMessage deserialize(ByteBuf in) {
		id = in.readInt();
		var className = readString(in);
		try {
			type = (Class<? extends DeathShadowCore>) Class.forName(className);
		} catch (ClassNotFoundException e) {
			Log.error("NodeInfo deserialize core type not found for name [" + className + "]");
		}
		ip = readString(in);
		port = in.readInt();
		clientCount = in.readInt();
		launchTime = in.readLong();
		lastHeartbeatTime = in.readLong();
		// processid = in.readLong();
		return null;
	}
	
}
