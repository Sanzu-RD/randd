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
import com.souchy.randd.commons.diamond.models.stats.CreatureStats;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.data.s1.status.SpellStatus;

/**
 * 
 * 
 * Would be really cool to have effects for global/environment type of buffs
 * 
 * Like the sun shows up and shines hard, the cell blocks change texture to have lava cracks or desert ground, post-process of heat deformation, etc.
 * Same for water/rain or nature with grass/flowers or air with wind effects, etc. 
 * 
 * Would be able to deactivate those effects in settings.
 * 
 * 
 * 
 * @author Blank
 * @date 17 août 2021
 */
public class Heatwave extends Spell {

	public static final int modelid = Elements.fire.makeid(1, 6);

	public Damage e1;
	public AddStatusEffect e2;
	
	public Heatwave(Fight f) {
		super(f);
		var formula = new HashMap<Element, IntStat>();
		formula.put(Elements.fire, new IntStat(5, 0, 10, 0));
		e1 = new Damage(AoeBuilders.single.get(), TargetType.full.asStat(), formula);
		

		// status that reduces resistances and doesn't fuse
		e2 = new AddStatusEffect(AoeBuilders.single.get(), TargetType.full.asStat(), (ff) -> {
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
			b.creatureStats.affinity.get(Elements.fire).inc = 20;
			return b;
		});
		
		this.effects.add(e1);
		this.effects.add(e2);
	}
	
	@Override
	public int modelid() {
		return modelid;
	}

	@Override
	protected void initBaseStats(SpellStats stats) {
		stats.costs.put(Resource.mana, new IntStat(4));
		stats.maxRangeRadius.baseflat = 0;
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
		Log.info("Heatwave cast: " + caster + ", " + target);
		// apply damage and status to the whole map
		this.get(Fight.class).creatures.foreach(c -> {
			e1.apply(caster, c.getCell());
			e2.apply(caster, c.getCell());
		});
	}

	@Override
	public Spell copy(Fight fight) {
		var s = new Heatwave(fight);
		s.stats = stats.copy();
//		Log.critical("Heatwave FIGHT COPY = " + fight);
		return s;
	}

	
	
}
