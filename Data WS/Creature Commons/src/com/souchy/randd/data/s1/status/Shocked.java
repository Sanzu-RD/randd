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
	public boolean fuse(Status s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onAdd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLose() {
		// TODO Auto-generated method stub
		
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

//	@Override
//	public Shocked create(Entity source, Entity target) {
//		return new Shocked(source, target); 
//		// need source + target. 
//		// perhaps serialize {status id, sourceEntityId, targetEntityId, [custom status values]} ? 
//		// and have an entityBag map for all entities including cells
//	}

	@Override
	public Shocked create(Fight fight, int source, int target) {
		return new Shocked(fight, source, target); 
	}

	@Override
	public Status copy(Fight f) {
		var s = new Shocked(f, sourceEntityId, targetEntityId);
		return s;
	}

	@Override
	public HandlerType type() {
		return HandlerType.Modifier;
	}
	
}
