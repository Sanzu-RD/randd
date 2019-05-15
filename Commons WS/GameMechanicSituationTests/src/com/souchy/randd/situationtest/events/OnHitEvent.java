package com.souchy.randd.situationtest.events;

import com.souchy.randd.jade.api.AEntity;


/**
 * 
 * To differentiate InflictOnHit and RecvOnHit : <br>
 *  just compare event.source == this.character in the handler <br>
 *  if true then it's an "inflict" event <br>
 *  if false, then it's a "recv" event <br>
 * 
 * @author Souchy
 *
 */
public class OnHitEvent extends Event {
	
	/** receiver of the hit */
	public final AEntity target;

	public OnHitEvent(AEntity source, AEntity target) {
		super(source);

		this.target = target;
	}

}
