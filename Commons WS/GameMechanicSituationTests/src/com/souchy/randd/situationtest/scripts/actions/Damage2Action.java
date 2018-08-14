package com.souchy.randd.situationtest.scripts.actions;

import com.souchy.randd.situationtest.interfaces.IEntity;
import com.souchy.randd.situationtest.properties.ElementValue;
import com.souchy.randd.situationtest.properties.types.Damages;
import com.souchy.randd.situationtest.scripts.ScriptedAction;

/**
 * 
 * @author Souchy
 * 
 * 
 * Damage formula for flat damage that doesnt scale at all with the source's stats
 *
 */
public class Damage2Action extends ScriptedAction {
	
	//public final Entity source;
	public final IEntity target;
	
	public final Damages dmgType;

	public final ElementValue dmg;
	
	
	public Damage2Action(IEntity source, IEntity target, Damages dmgType, ElementValue dmg) {
		super(source);
		//this.source = source;
		this.target = target;
		
		this.dmgType = dmgType;
		this.dmg = dmg;
	}

}
