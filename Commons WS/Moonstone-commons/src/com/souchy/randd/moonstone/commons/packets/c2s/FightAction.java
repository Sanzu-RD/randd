package com.souchy.randd.moonstone.commons.packets.c2s;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

@ID(id = 10002)
public class FightAction implements BBMessage {

	public int sourceID;
	public int turn;
	
	public int actionID;
	public int cellX;
	public int cellY;
	
//	public boolean canApply;
	
	public FightAction() {
		
	}
	
	public FightAction(int sourceID, int turn, int actionID, int cellX, int cellY) {
		this.sourceID = sourceID;
		this.turn = turn;
		this.actionID = actionID;
		this.cellX = cellX;
		this.cellY = cellY;
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(sourceID);
		out.writeInt(turn);
		out.writeInt(actionID);
		out.writeInt(cellX);
		out.writeInt(cellY);
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		sourceID = in.readInt();
		turn = in.readInt();
		actionID = in.readInt();
		cellX = in.readInt();
		cellY = in.readInt();
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new FightAction();
	}

	@Override
	public int getBufferCapacity() {
		return 5 * 4;
	}

}
