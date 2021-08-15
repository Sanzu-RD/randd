package com.souchy.randd.data.s1.spells.air;

import java.util.HashMap;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.damage.Damage;
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

/**
 * 
 * 
 * @author Blank
 * @date 6 août 2021
 */
public class SunBall extends Spell {

	public static final int sunballID = Elements.air.makeid(1, 1);

	public Damage e1;
	
	public SunBall(Fight f) {
		super(f);
		var formula = new HashMap<Element, IntStat>();
		formula.put(Elements.fire, new IntStat(50, 0, 10, 0));
		e1 = new Damage(AoeBuilders.single.get(), TargetType.full.asStat(), formula);
		this.effects.add(e1);
	}
	
	@Override
	public int modelid() {
		return sunballID;
	}

	@Override
	protected void initBaseStats(SpellStats stats) {
		stats.costs.put(Resource.mana, new IntStat(3));
		stats.maxRangeRadius.baseflat = 8;
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
		Log.info("SunBall cast: " + caster + ", " + target);
		e1.apply(caster, target);
	}

	
	@Override
	public Spell copy(Fight fight) {
		var s = new SunBall(fight);
		s.stats = stats.copy();
//		Log.critical("SunBall FIGHT COPY = " + fight);
		return s;
	}

}
