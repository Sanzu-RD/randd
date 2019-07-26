package gamemechanics.data.effects.other;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.eventbus.Subscribe;

import gamemechanics.common.FightEvent;
import gamemechanics.events.OnTurnEnd;
import gamemechanics.models.Effect;
import gamemechanics.models.Spell;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;
import gamemechanics.models.entities.Entity;
import gamemechanics.stats.BaseSpellCost;
import gamemechanics.stats.StatModifier.st;

public class SlowEffect extends Effect<Creature> {

	public Map<st, Integer> slows = new HashMap<>();
	
	@Override
	public void apply(Entity source, Creature target) {

		var inst = new OnSlowInstance(slows);
		source.fight.bus.post(inst);
		
		for(var entry : inst.slows.entrySet()) {
			target.getStats().lose(entry.getKey(), entry.getValue());
			// post event OnStatLoss (dans la method stat.lose)
			// send packet for each resource loss
		}
	}
	
	
	public static class OnSlowInstance implements FightEvent {
		public Map<st, Integer> slows = new HashMap<>();
		public OnSlowInstance(Map<st, Integer> slows) {
			for(var entry : slows.entrySet())
				slows.put(entry.getKey(), entry.getValue());
		}
	}
	public static interface OnSlowInstanceHandler {
		@Subscribe
		public void onTurnEnd(OnTurnEnd e);
	}
	
	
	
	public static class Rall extends Spell {
		public Rall() {
			var eff = new SlowEffect();
			eff.slows.put(st.mana, 2);
			effects.add(eff);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void cast(Entity caster, Cell target) {
			for(var c : target.getCreatures())
				for(var eff : effects)
					eff.apply(caster, c);
		}

		@Override
		public void setCosts(List<BaseSpellCost> costs) {
			costs.add(new BaseSpellCost(st.mana, 2d));
		}

		@Override
		public String getSpellFX() {
			return "";
		}

		@Override
		public String getCasterAnimation() {
			return "";
		}
		
	}
	
	
	
}
