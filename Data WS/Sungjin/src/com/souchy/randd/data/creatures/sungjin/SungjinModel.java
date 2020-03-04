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
import data.new1.spellstats.CreatureStats;
import data.new1.spellstats.base.IntStat;
import data.new1.timed.Status;
import gamemechanics.statics.CreatureType;
import gamemechanics.statics.Element;
//import gamemechanics.statics.creatures.CreatureType;
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
	protected CreatureStats initBaseStats() {
		var stats = new CreatureStats();
//		stats.addResource(1500d, Resource.life); //, mathMod.flat);
//		stats.addResource(12, Resource.mana); //, mathMod.flat);
//		stats.addResource(6, Resource.move); //, mathMod.flat);
//
//		//stats.addAffinity(30, Elements.water, mathMod.flat);
//		//stats.addAffinity(30, Elements.dark, mathMod.flat);
//		stats.addAffinity(30, Elements.water);
//		stats.addAffinity(30, Elements.dark);
		
		stats.resources.put(Resource.life, new IntStat(1500)); //.get(Resource.life).base = 1500;
		stats.resources.put(Resource.mana, new IntStat(12)); 
		stats.resources.put(Resource.move, new IntStat(6)); 

		stats.affinity.get(Elements.ice).inc = 30; //.put(Elements.water, new IntStat(30));
		stats.affinity.get(Elements.dark).inc = 30; //.put(Elements.dark, new IntStat(30));
		
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
