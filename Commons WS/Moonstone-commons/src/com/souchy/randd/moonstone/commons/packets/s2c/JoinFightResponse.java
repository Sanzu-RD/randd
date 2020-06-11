package com.souchy.randd.moonstone.commons.packets.s2c;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.commons.tealwaters.logging.Log;

import data.new1.timed.Status;
import gamemechanics.main.DiamondModels;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;
import gamemechanics.models.Fight;
import gamemechanics.models.Spell;
import io.netty.buffer.ByteBuf;

@ID(id = 11001)
public class JoinFightResponse implements BBMessage {

	public boolean accepted;
	
	
	public JoinFightResponse() { }
	public JoinFightResponse(boolean accepted) {
		this.accepted = accepted;
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeBoolean(accepted);
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		this.accepted = in.readBoolean();
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new JoinFightResponse(false);
	}

	@Override
	public int getBufferCapacity() {
		return 1;
	}

}