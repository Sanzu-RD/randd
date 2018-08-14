package com.souchy.randd.situationtest.models.entities;

import com.souchy.randd.situationtest.math.Point3D;
import com.souchy.randd.situationtest.models.org.FightContext;

public class Summon extends Character {

	private final Character summoner;
	
	public Summon(FightContext context, int id, Point3D pos,  Character summoner) {
		super(context, id, pos);
		this.summoner = summoner;
	}

	
	public Character getSummoner() {
		return summoner;
	}

	/**
	 * False by default (Character = Player)<p>
	 * Implemented to true in Summon (Character = Summon)
	 */
	@Override
	public boolean isSummon() {
		return false;
	}
	
}
