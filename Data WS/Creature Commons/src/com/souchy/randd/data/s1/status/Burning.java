package com.souchy.randd.data.s1.status;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;

import data.new1.timed.Status;
import gamemechanics.models.Fight;
import io.netty.buffer.ByteBuf;

public class Burning extends Status {

	public Burning(Fight f, int source, int target) {
		super(f, source, target);
	}

	@Override
	public int modelID() {
		return 2;
	}

	@Override
	public Status create(Fight fight, int source, int target) {
		return new Burning(fight, source, target);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status copy(Fight fight) {
//		FUCK
//		the fight component qu'on mettrait sur le model pour copier est pas bon car le model est static dans DiamondModels
		var s = new Burning(fight, sourceEntityId, targetEntityId);
		s.canDebuff = canDebuff;
		s.canRemove = canRemove;
		s.stacks = stacks;
		s.duration = duration;
		// FIXME status copy effects
//		effects.forEach(eid -> {
//			var em = DiamondModels.effects.get(eid); // copy effects
//			s.effects.add(em.copy()); //fight));
//		});
		return s;
	}
	
}
