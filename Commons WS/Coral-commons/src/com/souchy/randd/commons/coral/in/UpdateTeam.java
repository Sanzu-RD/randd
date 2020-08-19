package com.souchy.randd.commons.coral.in;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.diamond.statics.Constants;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.jade.meta.JadeCreature;

import io.netty.buffer.ByteBuf;

/**
 * 
 * 
 * @author Blank
 * @date 25 déc. 2019
 */
@ID(id = 7003)
public class UpdateTeam implements BBMessage {
	
	public JadeCreature[] creatures;
//	public boolean valid = false;
	
	private UpdateTeam() {}
	public UpdateTeam(JadeCreature[] creatures) {
		this.creatures = creatures;
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeByte(creatures.length);
		for(JadeCreature creature : creatures) 
			creature.serialize(out);
//		valid = true;
		return out;
	}


	@Override
	public BBMessage deserialize(ByteBuf in) {
		byte count = in.readByte();
		creatures = new JadeCreature[count];
		for(int c = 0; c < count; c++) {
			var creature = new JadeCreature();
			creature.deserialize(in);
			creatures[c] = creature;
		}
//		valid = true;
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new UpdateTeam();
	}

	@Override
	public int getBufferCapacity() {
		return Constants.CreaturesPerTeam * (1 + Constants.numberOfSpells + Element.count());
	}
	
}
