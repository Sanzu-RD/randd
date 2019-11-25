package com.souchy.randd.moonstone.commons.packets.s2c;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import gamemechanics.models.Fight;
import io.netty.buffer.ByteBuf;

@SuppressWarnings("exports")
@ID(id = 3)
public class Update implements BBMessage {

	public Fight fight;
	
	public Update() {
	}
	
	public Update(Fight fight) {
		this.fight = fight;
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new Update();
	}

	@Override
	public int getBufferCapacity() {
		return 10;
	}
}
