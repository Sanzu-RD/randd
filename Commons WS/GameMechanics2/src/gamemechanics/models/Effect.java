package gamemechanics.models;

import gamemechanics.events.OnLifeDmgInstance;
import gamemechanics.events.OnLifeDmgInstance.OnLifeDmgInstanceHandler;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;
import gamemechanics.models.entities.Entity;

/**
 * 
 * Every Effect has :
 * 		Entity source
 * 		Entity target
 * 
 * An Entity might be a Creature or a Cell or....?? 
 * This sort of Entity needs a way to store statuses or stats
 * 
 * @author Blank
 *
 */
public abstract class Effect<T extends Entity> {
	
	public abstract void apply(Entity source, T target);

	// ---------------------------------------------------------------------------------------------------------------------------------
	public static abstract class CellEffect extends Effect<Cell> {
		
	}
	public static abstract class CreatureEffect extends Effect<Cell> {
		
	}
	// ---------------------------------------------------------------------------------------------------------------------------------
	
	/*
	 Effets one-shot : apply works well
	 Effets continus : not so much (better with events for example)
	 */
	public static abstract class OneShotEffect<T extends Entity> extends Effect<T> {
		
	}
	public static abstract class ContinuousEffect<T extends Entity> extends Effect<T> {
		protected Entity source;
		protected T target;
		@Override
		public void apply(Entity source, T target) {
			this.source = source;
			this.target = target;
			source.fight.bus.register(this);
		}
	}
	
	
	// ---------------------------------------------------------------------------------------------------------------------------------
	
	
	public static class StatsEffect extends Effect<Creature> {
		public StatModifier mod;
		public StatsEffect(StatModifier mod) {
			this.mod = mod;
		}
		@Override
		public void apply(Entity source, Creature target) {
			mod.apply(target.getTempStats());
			// need access to temporary stats ?? ..
			// or just insert them into the creature's stats and then : OnAction -> wipe everything but base stats + re-apply every stats effect
		}
		// public void apply(Creature target) -> on apply -> target.stats.add(this)
	}


	
	public static class DamageTakenAsEleEffect extends ContinuousEffect<Creature> implements OnLifeDmgInstanceHandler {
		@Override
		public void onLifeDmgInstance(OnLifeDmgInstance e) {
			if(e.target == this.target) {
				
			}
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	
}
