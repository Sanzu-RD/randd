package com.souchy.randd.data.s1.status;

import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;

import io.netty.buffer.ByteBuf;

public class SpellStatus extends Status {

	/**
	 * modelid of the spell that applied this buff
	 */
	public int spellmodelid;
	
	public SpellStatus(Fight f, int sourceEntityId, int targetEntityId) {
		super(f, sourceEntityId, targetEntityId);
	}
	public SpellStatus(Fight f, int sourceEntityId, int targetEntityId, int spellmodelid) {
		super(f, sourceEntityId, targetEntityId);
		this.spellmodelid = spellmodelid;
	}

	@Override
	public HandlerType type() {
		return HandlerType.Reactor;
	}

	@Override
	public int modelid() {
		return spellmodelid;
	}
	
	@Override
	public boolean fuse(Status s) {
		return super.fuse(s);
	}

	@Override
	public SpellStatus create(Fight fight, int source, int target) {
		return new SpellStatus(fight, source, target);
	}

	@Override
	public SpellStatus copy0(Fight f) {
		var s = new SpellStatus(f, sourceEntityId, targetEntityId);
		s.spellmodelid = spellmodelid;
		return s;
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		return super.serialize(out);
	}
	
	@Override
	public BBMessage deserialize(ByteBuf in) {
		return super.deserialize(in);
	}
	
	
	
}
