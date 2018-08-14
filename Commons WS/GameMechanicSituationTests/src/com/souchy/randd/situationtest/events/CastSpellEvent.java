package com.souchy.randd.situationtest.events;

import com.souchy.randd.situationtest.interfaces.IEntity;
import com.souchy.randd.situationtest.interfaces.Spell;
import com.souchy.randd.situationtest.models.org.FightContext;
import com.souchy.randd.situationtest.models.stage.Cell;
import com.souchy.randd.situationtest.scripts.ScriptedSkill;

public class CastSpellEvent extends Event {

	public final FightContext context;
	//public Entity source;
	public final Cell target;
	public final ScriptedSkill spell;
	
	public CastSpellEvent(FightContext context, IEntity source, Cell target, ScriptedSkill spell) {
		super(source);
		this.context = context;
		//this.source = source;
		this.target = target;
		this.spell = spell;
	}
	
	
}
