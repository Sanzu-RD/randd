package com.souchy.randd.commons.diamond.statusevents.resource;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class ResourceGainLossEvent extends Event {
	
//	public static enum ResourceComposite {
//		onlyShield,
//		composite,
//		noShield
//	}
	
	/**
	 * if the resource was a use cost rather than a dmg or buff
	 */
//	public final boolean isUsed;
	/**
	 * if the resource gain/loss affects the shields
	 */
//	public final ResourceComposite composite;
	/**
	 * what resource was affected
	 */
//	public final Resource res;
	/**
	 * how much was gained / lost
	 */
//	public final int delta;

	public static interface OnResourceGainLossHandler extends Handler {
		@Subscribe
		public default void handle0(ResourceGainLossEvent event) {
			if(check(event)) onResourceGainLoss(event);
		}
		public void onResourceGainLoss(ResourceGainLossEvent event);
	}

	public ResourceGainLossEvent(Creature source, Cell target, Effect parentEffect) {
		super(source, target, parentEffect);
	}
//	public ResourceGainLossEvent(Creature source, Cell target, Effect parentEffect, ResourceComposite shield, Resource res, int delta) {
//		super(source, target, parentEffect);
//		this.composite = shield;
//		this.res = res;
//		this.delta = delta;
//		this.isUsed = false;
//	}
//
//	public ResourceGainLossEvent(Creature source, Cell target, Effect parentEffect, ResourceComposite shield, Resource res, int delta, boolean isUsed) {
//		super(source, target, parentEffect);
//		this.composite = shield;
//		this.res = res;
//		this.delta = delta;
//		this.isUsed = isUsed;
//	}

	@Override
	public Event copy0() {
		return new ResourceGainLossEvent(source, target, effect); //, composite, res, delta, isUsed);
	}
	
}
