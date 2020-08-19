package com.souchy.randd.deathshadows.nodes.pearl.messaging.moonstone;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

/**
 * This message is used two-ways.
 * Pearl -> moonstone asks for info
 * Moonstone -> pearl fills & replies with info
 * 
 * @author Blank
 * @date 13 juin 2020
 */
@ID(id = 1102)
public class FightInfo implements BBMessage {
	
	public List<Fight> fights = new ArrayList<>();
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(fights.size());
		for(var fight : fights){
			// serialize whatever we want from each fight
			out.writeInt(fight.id);
		}
		return out;
	}

	@Override
	public FightInfo deserialize(ByteBuf in) {
		int fightcount = in.readInt();
		for(int i = 0; i < fightcount; i++) {
			var f = new Fight();
			fights.add(f);
			f.id = in.readInt();
			// deserialize whatever we want into fight objects
		}
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new FightInfo();
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
}
