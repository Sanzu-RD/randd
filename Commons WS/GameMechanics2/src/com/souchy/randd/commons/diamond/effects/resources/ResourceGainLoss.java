package com.souchy.randd.commons.diamond.effects.resources;

import java.util.HashMap;
import java.util.Map;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.resource.ResourceGainLossEvent;

public class ResourceGainLoss extends Effect {
	
	public final boolean isUse;
	public final Map<Resource, Integer> shields, resources;
	
	public ResourceGainLoss(Aoe aoe, TargetTypeStat targetConditions, boolean isUse, Map<Resource, Integer> shields, Map<Resource, Integer> resources) {
		super(aoe, targetConditions);
		this.isUse = isUse;
		this.shields = shields;
		this.resources = resources;
	}
	
	public static void use(Creature caster, Map<Resource, Integer> costs) { //Resource r, double amount) {
		// draîne les shields avant les ressources
		var shields = ResourceGainLoss.bufferShields(caster, costs);
		// inverse en négatif
		costs.replaceAll((r, i) -> -i);
		shields.replaceAll((r, i) -> -i);
		var e = new ResourceGainLoss(AoeBuilders.single.get(), TargetType.full.asStat(), true, shields, costs);
		e.height.set(caster.stats.height);
		e.apply(caster, caster.getCell());
	}
	
	public static Map<Resource, Integer> bufferShields(Creature target, Map<Resource, Integer> resources) {
		var shields = new HashMap<Resource, Integer>();
		for(var k : resources.keySet()) {
			var res = resources.get(k);
			var s = target.stats.shield.get(k).value();
			if(s > res) {
				shields.put(k, res);
			} else {
				shields.put(k, s);
				res -= s;
				resources.put(k, res);
			}
		}
		return shields;
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
		if(shields != null)
			for(Resource res : shields.keySet()) 
				c.stats.shield.get(res).fight += shields.get(res);
		
		// apply modifications to resources
		if(resources != null)
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
		return new ResourceGainLoss(aoe, targetConditions, isUse, new HashMap<>(shields), new HashMap<>(resources));
	}
	
}
