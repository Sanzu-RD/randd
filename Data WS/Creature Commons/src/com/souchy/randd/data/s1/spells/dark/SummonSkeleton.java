package com.souchy.randd.data.s1.spells.dark;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;

/**
 * These spells need to be registered to their creature type to be available to creatures of that type when the creature data is created
 * 
 * @author Blank
 *
 */
public class SummonSkeleton extends Spell { //implements EbiSpellData {

	public SummonSkeleton(Fight f) {
		super(f);
	}

	@Override
	public int modelid() {
		return 7;
	}

	public List<Effect> previsualisation(Creature caster, Cell target) {
		var stats = new SpellStats();
//		stats..compile(caster);
		return null;
	}
	
	@Override
	protected SpellStats initBaseStats() {
		var stats = new SpellStats();
		return stats;
	}

	@Override
	protected ImmutableList<Element> initElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ImmutableList<CreatureType> initCreatureTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cast0(Creature caster, Cell target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canCast(Creature caster) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canTarget(Creature caster, Cell target) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Spell copy(Fight fight) {
		var s = new SummonSkeleton(fight);
		s.stats = stats.copy();
		return s;
	}
	
}
