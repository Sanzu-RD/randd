package com.souchy.randd.data.s1.status;

import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.stats.CreatureStats;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statusevents.damage.DamageEvent;
import com.souchy.randd.commons.diamond.statusevents.damage.DamageEvent.OnDamageHandler;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;

import io.netty.buffer.ByteBuf;

public class Shocked extends Status implements OnDamageHandler {

	public Shocked(Fight f, int source, int target) {
		super(f, source, target);
		this.creatureStats = new CreatureStats();
		creatureStats.resistance.get(Element.global).more -= 50;
	}

	@Override
	public int modelid() {
		return 1;
	}

	@Override
	public HandlerType type() {
		return HandlerType.Modifier;
	}
	
	/**
	 * Remove global resistance when the status is added
	 */
//	@Override
//	public void onAdd() {
//		var creature = this.get(Fight.class).creatures.get(targetEntityId);
//		creature.stats.resistance.get(Element.global).more -= 50;
//	}

//	/**
//	 * Restore global resistance when the status is removed
//	 */
//	@Override
//	public void onLose() {
//		var creature = this.get(Fight.class).creatures.get(targetEntityId);
//		creature.stats.resistance.get(Element.global).more += 50;
//	}
	
	@Override
	public boolean fuse(Status s) {
		genericFuseStrategy(s, false, true, false); // only refresh duration
		return true;
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

	@Override
	public void onDamage(DamageEvent e) {
//		if(e.target.getFirstCreature().id == this.targetEntityId) {
//			Damage d = (Damage) e.effect;
//			
//		}
	}
	
}
