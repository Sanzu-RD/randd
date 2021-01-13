package com.souchy.randd.commons.diamond.effects.resources;

import java.util.HashMap;
import java.util.Map;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.resource.ResourceGainLossEvent;

public class ResourceGainLoss extends Effect {
	
	public final boolean isUse;
	public final Map<Resource, Double> shields, resources;
	
	public ResourceGainLoss(Fight f, Aoe aoe, TargetTypeStat targetConditions, boolean isUse, Map<Resource, Double> shields, Map<Resource, Double> resources) {
		super(f, aoe, targetConditions);
		this.isUse = isUse;
		this.shields = shields;
		this.resources = resources;
	}

	@Override
	public Event createAssociatedEvent(Creature source, Cell target) {
		return new ResourceGainLossEvent(source, target, this);
	}

	@Override
	public void prepareCaster(Creature caster, Cell aoeOrigin) { }

	@Override
	public void prepareTarget(Creature caster, Cell target) { }

	@Override
	public void apply0(Creature caster, Cell target) {
		Creature c = this.getCreatureTarget(target);
		if(c == null) return;
		
		// apply modifications to shields
		for(Resource res : shields.keySet()) 
			c.stats.shield.get(res).fight += shields.get(res);
		
		// apply modifications to resources
		for(Resource res : resources.keySet()) 
			c.stats.resources.get(res).fight += resources.get(res);
		
		// check if should die
		if(c.stats.resources.get(Resource.life).value() <= 0) {
			// TODO die
			// remove target from fight
		}
	}

	@Override
	public Effect copy() {
		return new ResourceGainLoss(get(Fight.class), aoe, targetConditions, isUse, new HashMap<>(shields), new HashMap<>(resources));
	}
	
}
