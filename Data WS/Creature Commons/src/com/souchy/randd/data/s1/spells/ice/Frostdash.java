package com.souchy.randd.data.s1.spells.ice;

import java.util.HashMap;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.damage.Damage;
import com.souchy.randd.commons.diamond.effects.displacement.DashTo;
import com.souchy.randd.commons.diamond.effects.status.AddStatusEffect;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.data.s1.status.Burning;

public class Frostdash extends Spell {

	public static final int frostdashID = Elements.water.thousand(6);

	public Damage e1;
	public DashTo e2;
	
	public Frostdash(Fight f) {
		super(f);
		
		//this.stats.target.setBase(TargetType.needsLineOfSight, false); // everything already false by default
		this.stats.target.base = 0;
		this.stats.target.fight = 0;
		this.stats.target.setFight(TargetType.empty, true); 
		this.stats.target.setBase(TargetType.empty, true); // only empty cells are accepted

		var formula = new HashMap<Element, IntStat>();
		formula.put(Elements.water, new IntStat(50, 0, 10, 0));
		e1 = new Damage(AoeBuilders.single.get(), TargetType.full.asStat(), formula);
		e2 = new DashTo(AoeBuilders.single.get(), TargetType.empty.asStat());
		
		this.effects.add(e1);
		this.effects.add(e2);
	}

	@Override
	public int modelid() {
		return frostdashID;
	}

	@Override
	protected SpellStats initBaseStats() {
		var stats = new SpellStats();
		stats.maxRangeRadius.baseflat = 5;
		stats.maxRangePattern.base = r -> AoeBuilders.cross.apply(r);
		return stats;
	}

	@Override
	protected ImmutableList<Element> initElements() {
		return null;
	}

	@Override
	protected ImmutableList<CreatureType> initCreatureTypes() {
		return null;
	}

	@Override
	protected void cast0(Creature caster, Cell target) {
		Log.info("Frostdash cast: " + caster + ", " + target);
		e1.apply(caster, target);
		e2.apply(caster, target);
	}

	@Override
	public Spell copy(Fight fight) {
		var s = new Frostdash(fight);
		s.stats = stats.copy();
		return s;
	}
	
}
