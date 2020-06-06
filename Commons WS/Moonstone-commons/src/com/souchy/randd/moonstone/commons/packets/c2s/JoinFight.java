package com.souchy.randd.moonstone.commons.packets.c2s;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

@ID(id = 10001)
public class JoinFight implements BBMessage {

//	public String userID;
	public String fightID;
	
	public JoinFight() {
		
	}
	
	public JoinFight(String fightID) {
//		this.userID = userID;
		this.fightID = fightID;
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
//		writeString(out, userID);
		writeString(out, fightID);
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
//		userID = readString(in);
		fightID = readString(in);
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new JoinFight();
	}

	@Override
	public int getBufferCapacity() {
		return 10;
	}

}
