package com.souchy.randd.situationtest.events;

import com.souchy.randd.jade.api.AEntity;
import com.souchy.randd.situationtest.models.map.Cell;
import com.souchy.randd.situationtest.models.org.FightContext;
import com.souchy.randd.situationtest.scripts.ScriptedSkill;

public class CastSpellEvent extends Event {

	public final FightContext context;
	//public Entity source;
	public final Cell target;
	public final ScriptedSkill spell;
	
	public CastSpellEvent(FightContext context, AEntity source, Cell target, ScriptedSkill spell) {
		super(source);
		this.context = context;
		//this.source = source;
		this.target = target;
		this.spell = spell;
	}
	
	
}
