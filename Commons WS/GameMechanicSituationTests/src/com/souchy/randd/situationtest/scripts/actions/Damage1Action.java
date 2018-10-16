package com.souchy.randd.situationtest.scripts.actions;

import com.souchy.randd.situationtest.properties.types.*;
import com.souchy.randd.situationtest.scripts.ScriptedAction;
import com.souchy.randd.jade.api.IEntity;
import com.souchy.randd.situationtest.properties.*;

/**
 * TODO delete les actions ????
 * 
 * @author Souchy
 *
 */
public class Damage1Action extends ScriptedAction {
	
	//public final Entity source;
	public final IEntity target;
	
	public final Damages dmgType;
	public final Elements dmgEle;
	public final ElementValue scl;
	public final ElementValue flat;
	
	
	public Damage1Action(IEntity source, IEntity target, Damages dmgType, Elements dmgEle, ElementValue scl, ElementValue flat) {
		super(source);
		//this.source = source;
		this.target = target;
		
		this.dmgType = dmgType;
		this.dmgEle = dmgEle;
		this.scl = scl;
		this.flat = flat;
	}
	
}
