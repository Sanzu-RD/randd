package com.souchy.randd.situationtest.properties.types;

import com.souchy.randd.situationtest.interfaces.IEntity;
import com.souchy.randd.situationtest.properties.ElementValue;

/**
 * 
 * Differentiate types of damage effects
 * 
 * @author Souchy
 *
 */
public enum Damages {
	
	
	/**
	 * A normal hit
	 */
	Hit {
		@Override
		public void some(ElementValue v, IEntity t) {
			// TODO Auto-generated method stub
			
		}
	},
	
	/**
	 * A hit that goes through shields
	 */
	PenetrationHit {
		@Override
		public void some(ElementValue v, IEntity t) {
			// TODO Auto-generated method stub
			
		}
	},
	
	/**
	 * A damage over time
	 */
	Dot {
		@Override
		public void some(ElementValue v, IEntity t) {
			// TODO Auto-generated method stub
			
		}
	},
	
	/**
	 * A counter hit damage, // ex ds disgaea quand on attaque on a une chance de counter, on peut l'identifier ainsi ? pour controler ex ne pas avoir counter infini, etc
	 */
	Counter {
		@Override
		public void some(ElementValue v, IEntity t) {
			// TODO Auto-generated method stub
			
		}
	},
	;
	
	
	public abstract void some(ElementValue v, IEntity t);

}
