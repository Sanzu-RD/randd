package data.new1.timed;

import gamemechanics.common.Disposable;
import gamemechanics.models.entities.Creature;
import gamemechanics.statics.stats.Stats;

/**
 * 
 * Statuses are different from Buffs.
 * Buffs give stats for the time of their duration.
 * Statuses react to events.
 * Both can be used to check a condition.
 * 
 * @author Blank
 * @date 27 oct. 2019
 */
public abstract class Buff extends TimedEffect implements Disposable {
	
	public Stats stats;

	@Override
	public void dispose() {
		super.dispose();
		stats = null;
	}
	
}
