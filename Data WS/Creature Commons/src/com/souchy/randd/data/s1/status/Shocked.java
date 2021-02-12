package com.souchy.randd.data.s1.status;

import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;

import io.netty.buffer.ByteBuf;

public class Shocked extends Status {

	public Shocked(Fight f, int source, int target) {
		super(f, source, target);
	}

	@Override
	public int modelid() {
		return 1;
	}

	@Override
	public HandlerType type() {
		return HandlerType.Modifier;
	}
	
	@Override
	public boolean fuse(Status s) {
		return false;
	}

	@Override
	public ByteBuf serialize(ByteBuf out) {
		// out.writeInt(this.anyVal);
		return null;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		// this.anyVal = in.readInt();
		return null;
	}

	@Override
	public Shocked create(Fight fight, int source, int target) {
		return new Shocked(fight, source, target); 
	}

	@Override
	public Status copy0(Fight f) {
		var s = new Shocked(f, sourceEntityId, targetEntityId);
		return s;
	}
	
}
