package com.souchy.randd.deathshadows.commons.cache.cinnabar.dao;

import com.souchy.randd.commons.cache.impl.RedisCache;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;
import com.souchy.randd.jade.combat.Cell;
import com.souchy.randd.jade.combat.Spell;

public class CinnabarSpell implements Spell {
	
	private int id;
	private RedisCache source;
	
	public CinnabarSpell(int id, RedisCache source) {
		this.id = id;
	}

	@Override
	public Integer getID() {
		return id;
	}

	@Override
	public void getName() {
		source.get("");
	}

	@Override
	public void getDesc() {
		source.get("");
	}

	@Override
	public void getCost() {
		source.get("");
	}

	@Override
	public boolean lineOfSight() {
		return source.get("").equals("1");
	}

	@Override
	public boolean targetsEntity() {
		return source.get("").equals("1");
	}

	@Override
	public boolean targetsEmptyCell() {
		return source.get("").equals("1");
	}

	@Override
	public boolean hasAreaOfEffect() {
		return source.get("").equals("1");
	}

	@Override
	public void areaOfEffect() {
		source.get("");
	}

	@Override
	public Cell[][] castableCells() {
		source.get("");
		return null;
	}

	@Override
	public void effects() {
		source.get("");
	}

	@Override
	public void conditions() {
		source.get("");
	}
	
}
