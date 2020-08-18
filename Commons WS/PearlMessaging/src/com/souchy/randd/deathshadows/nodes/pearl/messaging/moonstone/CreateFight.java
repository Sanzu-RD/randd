package com.souchy.randd.deathshadows.nodes.pearl.messaging.moonstone;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

/**
 * This message goes two-ways
 * 
 * Pearl -> moonstone 
 * 		asks to create a fight. 
 * 		can have a fight object already in the packet or not.
 * 		if the packet already contains a fight object, then moonstone deserializes it and adds it to its fights.
 * 
 * Moonstone -> pearl 
 * 		sends the fight info from the newly created fight if the original request didnt have one already
 * 
 * Rainbow -> pearl
 * 		pearl just relays the message to a moonstone
 * 
 * Coral -> moonstone
 * 		same thing as 
 * Moonstone -> coral
 * 
 * 
 * @author Blank
 * @date 13 juin 2020
 */
@ID(id = 1101)
public class CreateFight implements BBMessage {

	public Fight fight;
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeBoolean(fight != null);
		if(fight != null) {
			out.writeInt(fight.id);
		}
		return out;
	}

	@Override
	public CreateFight deserialize(ByteBuf in) {
		var has = in.readBoolean();
		if(has) {
			fight = new Fight();
			fight.id = in.readInt();
		}
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new CreateFight();
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
}
