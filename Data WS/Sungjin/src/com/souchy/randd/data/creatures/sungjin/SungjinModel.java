package com.souchy.randd.data.creatures.sungjin;

import java.util.ArrayList;
import java.util.List;

//import static gamemechanics.statics.creatures.CreatureType.Black;
//import static gamemechanics.statics.creatures.CreatureType.Blue;
//import static gamemechanics.statics.creatures.CreatureType.Summoner;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.data.s1.main.Elements;

import data.new1.CreatureModel;
import data.new1.SpellModel;
import data.new1.Tag;
import data.new1.timed.Status;
import gamemechanics.statics.CreatureType;
import gamemechanics.statics.Element;
//import gamemechanics.statics.creatures.CreatureType;
import gamemechanics.statics.stats.Stats;
import gamemechanics.statics.stats.modifiers.mathMod;
import gamemechanics.statics.stats.properties.Resource;

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
		stats.add(1500d, Resource.life, mathMod.flat);
		stats.add(12, Resource.mana, mathMod.flat);
		stats.add(6, Resource.move, mathMod.flat);

		//stats.addAffinity(30, Elements.water, mathMod.flat);
		//stats.addAffinity(30, Elements.dark, mathMod.flat);
		stats.addAffinity(30, Elements.water);
		stats.addAffinity(30, Elements.dark);

		return stats;
	}

	/*
	@Override
	protected ImmutableList<CreatureType> initAffinities() {
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
	*/

//	@Override
//	protected List<Tag> initTags() {
//		return new ArrayList<>();
//	}


}
