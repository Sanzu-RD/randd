package com.souchy.randd.data.creatures.sungjin;

import static gamemechanics.creatures.CreatureType.Black;
import static gamemechanics.creatures.CreatureType.Blue;
import static gamemechanics.creatures.CreatureType.Summoner;

import com.google.common.collect.ImmutableList;

import data.new1.CreatureModel;
import data.new1.SpellModel;
import gamemechanics.creatures.CreatureType;
import gamemechanics.stats.Modifier.mathMod;
import gamemechanics.stats.NewStats;
import gamemechanics.stats.StatProperty;
import gamemechanics.status.Status;

public class SungjinModel extends CreatureModel {
	
	public static final int id = 1000;

	@Override
	public int id() {
		return id;
	}
	
	public String getStrID() {
		return "sungjin";
	}

	@Override
	protected NewStats initBaseStats() {
		var stats = new NewStats();
		stats.add(1500, StatProperty.resource.life, mathMod.flat.val());
		stats.add(12, StatProperty.resource.mana, mathMod.flat.val());
		stats.add(6, StatProperty.resource.move, mathMod.flat.val());

		stats.add(30, StatProperty.element.blue, mathMod.flat.val());
		stats.add(30, StatProperty.element.blue, mathMod.scl.val());
		stats.add(30, StatProperty.element.black, mathMod.flat.val());
		stats.add(30, StatProperty.element.black, mathMod.scl.val());
		return stats;
	}

	@Override
	protected ImmutableList<CreatureType> initColors() {
		return ImmutableList.of(Blue, Black);
	}

	@Override
	protected ImmutableList<CreatureType> initTypes() {
		return ImmutableList.of(Summoner);
	}

	@Override
	protected ImmutableList<SpellModel> initSpells() {
		return null;
	}

	@Override
	protected ImmutableList<Status> initStatuses() {
		return null;
	}


}
