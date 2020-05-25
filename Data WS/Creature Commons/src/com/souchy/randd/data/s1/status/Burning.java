package com.souchy.randd.data.s1.status;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;

import data.new1.timed.Status;
import gamemechanics.models.Fight;
import gamemechanics.models.entities.Entity.EntityRef;
import io.netty.buffer.ByteBuf;

public class Burning extends Status {

	public Burning(EntityRef source, EntityRef target) {
		super(source, target);
	}

	@Override
	public int modelID() {
		return 2;
	}

	@Override
	public Status create(EntityRef source, EntityRef target) {
		return new Burning(source, target);
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
	
}
