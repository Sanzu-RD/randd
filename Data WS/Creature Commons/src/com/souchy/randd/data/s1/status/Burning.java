package com.souchy.randd.data.s1.status;

import java.util.HashMap;

import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.damage.Damage;
import com.souchy.randd.commons.diamond.effects.status.ModifyStatusEffect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent.OnTurnStartHandler;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;

import io.netty.buffer.ByteBuf;

public class Burning extends Status implements OnTurnStartHandler {
	
	public Damage dmg;

	public Burning(Fight f, int source, int target) {
		super(f, source, target);
		var formula = new HashMap<Element, IntStat>();
		formula.put(Elements.fire, new IntStat(10, 0, 0, 0));
		dmg = new Damage(AoeBuilders.single.get(), TargetType.full.asStat(), formula);
		this.effects.add(dmg);
	}

	
	@Override
	public int modelid() {
		return 2;
	}

	@Override
	public void onTurnStart(TurnStartEvent event) {
		super.onTurnStart(event); // décrémente la duration
//		if(duration <= 0) return; // n'applique pas les effets si le status est terminé 
		// scrath that, vu que c'est un effet qui arrive en début de tour, faut qu'il s'applique sur le dernier tick
		
		var fight = event.fight;
		// check que ça soit le tour de la cible
		int turnStartCreatureID = fight.timeline.get(event.index);
		if(targetEntityId == turnStartCreatureID) {
			var sourceCrea = fight.creatures.get(sourceEntityId);
			var targetCrea = fight.creatures.get(targetEntityId);
			dmg.height.set(targetCrea.stats.height); // rafraichi la height de l'effet au cas ou la cible aurait changé de hauteur
			dmg.apply(sourceCrea, targetCrea.getCell());
		}
	}
	
	@Override
	public Status create(Fight fight, int source, int target) {
		return new Burning(fight, source, target);
	}

	@Override
	public boolean fuse(Status s) {
		genericFuseStrategy(s, false, true);
		return true;
	}

	@Override
	public void onAdd() {
		
	}

	@Override
	public void onLose() {
		
	}
	@Override
	public Status copy(Fight f) {
//		FUCK
//		the fight component qu'on mettrait sur le model pour copier est pas bon car le model est static dans DiamondModels
		var s = new Burning(f, sourceEntityId, targetEntityId);
		s.canDebuff = canDebuff;
		s.canRemove = canRemove;
		s.stacks = stacks;
		s.duration = duration;
		s.dmg = dmg.copy();
		s.dmg.reset();
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
	public ByteBuf serialize(ByteBuf out) {
		return null;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		return null;
	}

	
}
