package com.souchy.randd.situationtest.scripts.actions;

import com.souchy.randd.jade.api.AEntity;
import com.souchy.randd.situationtest.properties.ElementValue;
import com.souchy.randd.situationtest.properties.types.Damages;
import com.souchy.randd.situationtest.properties.types.Elements;
import com.souchy.randd.situationtest.scripts.ScriptedAction;

/**
 * TODO delete les actions ????
 * 
 * @author Souchy
 *
 */
public class Damage1Action extends ScriptedAction {
	
	//public final Entity source;
	public final AEntity target;
	
	public final Damages dmgType;
	public final Elements dmgEle;
	public final ElementValue scl;
	public final ElementValue flat;
	
	
	public Damage1Action(AEntity source, AEntity target, Damages dmgType, Elements dmgEle, ElementValue scl, ElementValue flat) {
		super(source);
		//this.source = source;
		this.target = target;
		
		this.dmgType = dmgType;
		this.dmgEle = dmgEle;
		this.scl = scl;
		this.flat = flat;
	}
	
}
