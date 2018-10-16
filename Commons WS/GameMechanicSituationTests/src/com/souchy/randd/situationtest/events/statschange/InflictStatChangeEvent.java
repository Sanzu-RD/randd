package com.souchy.randd.situationtest.events.statschange;

import com.souchy.randd.situationtest.events.Event;
import com.souchy.randd.situationtest.interfaces.IEntity;
import com.souchy.randd.situationtest.properties.StatProperty;

/**
 * Alerts that a property value has been changed and by how much.
 * <br>
 * Ex. after taking damage
 * 
 * @author Souchy
 * 
 * @param prop <b>.enum</b> : Which property has been changed (ex Resource1)
 * @param prop <b>.value</b> : The difference in how much is has been changed (ex +50 heal)
 *
 */
public class InflictStatChangeEvent extends Event {
	
	//public final IEntity target;
	public final StatProperty changedProp;
	
	/**
	 * 
	 * @param prop <b>.enum</b> : Which property has been changed (ex Resource1)
	 * @param prop <b>.value</b> : The difference in how much is has been changed (ex +50 heal)
	 */
	public InflictStatChangeEvent(IEntity source, StatProperty prop) {
		super(source);
		//this.target = target;
		this.changedProp = prop;
	}

	
}
