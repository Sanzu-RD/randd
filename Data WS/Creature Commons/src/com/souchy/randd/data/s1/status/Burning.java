package com.souchy.randd.data.s1.status;

import com.souchy.randd.commons.diamond.effects.damage.Damage;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent.OnTurnStartHandler;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;

import io.netty.buffer.ByteBuf;

public class Burning extends Status implements OnTurnStartHandler {
	
	public Damage dmg;
	

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
		return false;
	}

	@Override
	public void onAdd() {
		
	}

	@Override
	public void onLose() {
		
	}

	@Override
	public ByteBuf serialize(ByteBuf out) {
		return null;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
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

	@Override
	public HandlerType type() {
		return HandlerType.Reactor;
	}

	@Override
	public void onTurnStart(TurnStartEvent event) {
		var fight = get(Fight.class);
		var sourceCrea = fight.creatures.get(sourceEntityId);
		var targetCrea = fight.creatures.get(targetEntityId);
		
		int creaID = fight.timeline.get(event.index);
		if(targetEntityId == creaID) {
			dmg.height.set(targetCrea.stats.height.single());
			dmg.apply(sourceCrea, targetCrea.getCell());
		}
	}
	
}
