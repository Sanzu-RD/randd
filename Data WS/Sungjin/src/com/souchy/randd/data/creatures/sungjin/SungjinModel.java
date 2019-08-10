package com.souchy.randd.data.creatures.sungjin;

import static gamemechanics.creatures.CreatureType.Black;
import static gamemechanics.creatures.CreatureType.Blue;
import static gamemechanics.creatures.CreatureType.Summoner;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.creatures.sungjin.spells.Comet;

import data.new1.CreatureModel;
import data.new1.SpellModel;
import gamemechanics.creatures.CreatureType;
import gamemechanics.stats.Modifier.mathMod;
import gamemechanics.stats.Stats;
import gamemechanics.stats.StatProperty;
import gamemechanics.stats.StatProperty.resource;
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
	protected Stats initBaseStats() {
		var stats = new Stats();
		stats.add(1500d, resource.life, mathMod.flat);
		stats.add(12, StatProperty.resource.mana, mathMod.flat);
		stats.add(6, StatProperty.resource.move, mathMod.flat);

		stats.add(30, StatProperty.element.blue, mathMod.flat);
		stats.add(30, StatProperty.element.blue, mathMod.scl);
		stats.add(30, StatProperty.element.black, mathMod.flat);
		stats.add(30, StatProperty.element.black, mathMod.scl);

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
		return ImmutableList.of();
	}

	@Override
	protected ImmutableList<Status> initStatuses() {
		return ImmutableList.of();
	}


}
