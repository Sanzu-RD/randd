package com.souchy.randd.data.s1.spells.fire;

import java.util.HashMap;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.damage.Damage;
import com.souchy.randd.commons.diamond.effects.status.AddStatusEffect;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.data.s1.status.Burning;

public class Fireball extends Spell {
	
	public static final int fireballID = 1;

	public Damage e1;
	public AddStatusEffect e2;
	
	public Fireball(Fight f) {
		super(f);
		var formula = new HashMap<Element, IntStat>();
		formula.put(Elements.fire, new IntStat(50, 0, 10, 0));
		e1 = new Damage(f, AoeBuilders.single.get(), TargetType.full.asStat(), formula);
		e2 = new AddStatusEffect(f, AoeBuilders.single.get(), TargetType.full.asStat(), () -> {
			var b = new Burning(f, 0, 0);
			b.duration = 3;
			b.stacks = 1;
			b.canDebuff = true;
			b.canRemove = true;
			return b;
		});
		this.effects.add(e1);
		this.effects.add(e2);
		Log.critical("FIREBALL FIGHT = " + f);
	}
	
	@Override
	public int modelid() {
		return fireballID;
	}

	@Override
	protected SpellStats initBaseStats() {
		var stats = new SpellStats();
		stats.costs.put(Resource.mana, new IntStat(3));
		stats.maxRangeRadius.baseflat = 8;
		return stats;
	}

	@Override
	protected ImmutableList<Element> initElements() {
		return ImmutableList.of(Elements.fire);
	}

	@Override
	protected ImmutableList<CreatureType> initCreatureTypes() {
		return ImmutableList.of();
	}

	@Override
	public void cast0(Creature caster, Cell target) {
		Log.info("Fireball cast: " + caster + ", " + target);
		e1.apply(caster, target);
		e2.apply(caster, target);
	}

	@Override
	public boolean canCast(Creature caster) {
		return super.canCast(caster);
	}

	@Override
	public boolean canTarget(Creature caster, Cell target) {
		return super.canTarget(caster, target);
	}
	
	@Override
	public Spell copy(Fight fight) {
		var s = new Fireball(fight);
		s.stats = stats.copy();
		Log.critical("FIREBALL FIGHT COPY = " + fight);
		return s;
	}
	
}
