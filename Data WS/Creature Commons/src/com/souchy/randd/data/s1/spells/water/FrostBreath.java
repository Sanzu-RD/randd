package com.souchy.randd.data.s1.spells.water;

import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.Status;

import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.damage.Damage;
import com.souchy.randd.commons.diamond.effects.status.AddStatusEffect;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.stats.CreatureStats;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.models.stats.base.ObjectStat;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.data.s1.status.Burning;
import com.souchy.randd.data.s1.status.SpellStatus;

/**
 * Reduces resistances and does damage
 * 
 * 4 small hits
 * 
 * @author Blank
 * @date 6 août 2021
 */
public class FrostBreath extends Spell {

	public static final int modelid = Elements.water.makeid(1, 2);

	public Damage e1;
	public AddStatusEffect e2;
	
	public FrostBreath(Fight f) {
		super(f);
		
		var aoe = AoeBuilders.cone.apply(2);
		this.stats.addAoe(aoe);
		
		var formula = new HashMap<Element, IntStat>();
		formula.put(Elements.water, new IntStat(7, 0, 10, 0));
		e1 = new Damage(aoe, TargetType.full.asStat(), formula);
		
		// status that reduces resistances and doesn't fuse
		e2 = new AddStatusEffect(aoe, TargetType.full.asStat(), (ff) -> {
			var b = new SpellStatus(ff, 0, 0, this.modelid());/* {
				@Override
				public boolean fuse(Status s) {
					return false;
				}
			};*/
			b.duration = 1;
			b.stacks = 1;
			b.canDebuff = true;
			b.canRemove = true;
			b.creatureStats = new CreatureStats();
			b.creatureStats.resistance.get(Elements.water).inc = -20;
			return b;
		});
		
		this.effects.add(e1);
	}
	
	@Override
	public int modelid() {
		return modelid;
	}

	@Override
	protected void initBaseStats(SpellStats stats) {
		stats.costs.put(Resource.mana, new IntStat(3));
		stats.maxRangeRadius.baseflat = 4;
	}

	@Override
	protected ImmutableList<Element> initElements() {
		return ImmutableList.of(Elements.water);
	}

	@Override
	protected ImmutableList<CreatureType> initCreatureTypes() {
		return ImmutableList.of();
	}

	@Override
	public void cast0(Creature caster, Cell target) {
		e2.apply(caster, target);
		
		e1.apply(caster, target);
		e1.apply(caster, target);
		e1.apply(caster, target);
		e1.apply(caster, target);
	}

	
	@Override
	public Spell copy(Fight fight) {
		var s = new FrostBreath(fight);
		s.stats = stats.copy();
		return s;
	}

}
