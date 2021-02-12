package com.souchy.randd.data.s1.status;

import java.util.HashMap;

import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.damage.Damage;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.commons.diamond.statusevents.Handler.Reactor;
import com.souchy.randd.commons.diamond.statusevents.other.EnterCellEvent;
import com.souchy.randd.commons.diamond.statusevents.other.EnterCellEvent.OnEnterCellHandler;
import com.souchy.randd.data.s1.main.Elements;

public class HeavyStep extends Status implements Reactor, OnEnterCellHandler {
	
	public Damage e1;
	
	public HeavyStep(Fight f, int sourceEntityId, int targetEntityId) {
		super(f, sourceEntityId, targetEntityId);
		
		var formula = new HashMap<Element, IntStat>();
		formula.put(Elements.earth, new IntStat(15));
		e1 = new Damage(AoeBuilders.circle.apply(1), TargetType.enemies.asStat(), formula);
	}
	
	@Override
	public int modelid() {
		return 3;
	}

	@Override
	public void onEnterCell(EnterCellEvent event) {
		componentBus.post(event);
	}
	
	@Override
	public Status create(Fight fight, int source, int target) {
		return new HeavyStep(fight, source, target);
	}

	@Override
	public Status copy0(Fight f) {
		var s = new HeavyStep(f, sourceEntityId, targetEntityId);
		s.e1 = e1.copy();
		s.effects.add(e1);
		return s;
	}
	
}
