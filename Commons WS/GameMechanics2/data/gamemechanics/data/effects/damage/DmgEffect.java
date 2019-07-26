package gamemechanics.data.effects.damage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gamemechanics.models.Effect;
import gamemechanics.models.entities.Creature;
import gamemechanics.models.entities.Entity;
import gamemechanics.stats.StatModifier;
import gamemechanics.stats.StatTable;
import gamemechanics.stats.StatModifier.BasicMod;
import gamemechanics.stats.StatModifier.mo;
import gamemechanics.stats.StatModifier.st;

public class DmgEffect extends Effect<Creature> {
	
	public List<BasicMod> bases;
	// output ele ?
	// converters ?
	
	@Override
	public void apply(Entity source, Creature target) {
		// calculate total dmg in each element from bases
		var s1 = source.getStats();
		var s2 = target.getStats();

		StatTable table = new StatTable();
		
		// add base flat to bonus flat, base scl to bonus scl, base more to bonus more
		bases.forEach(base -> {
			table.add(base.s, base.m, base.v + s1.getDmg(base.s, base.m));
		});

		Map<st, Double> eledmg = new HashMap<>();
		double total = 0;
		for(st ele : StatModifier.ELEMENTS) {
			var sourcedmg = table.get(ele, mo.flat) * (1 + table.get(ele, mo.scl)) * (1 + table.get(ele, mo.more));
			if(sourcedmg != 0) {
				var val = 
						sourcedmg // source-side dmg, can be 0
						* (1 - s2.getDmgRes(ele, mo.more) + s1.getDmgPen(ele, mo.more)) // more pen vs more res
						* (1 - s2.getDmgRes(ele, mo.scl ) + s1.getDmgPen(ele, mo.scl )) // scl  pen vs scl res
						+ (  - s2.getDmgRes(ele, mo.flat) + s1.getDmgPen(ele, mo.flat)) // flat pen vs flat res
						;
				eledmg.put(ele, 
						val
				);
				total += val;
			}
		}
		

		Map<st, Double> resourceDmg = new HashMap<>();
		
		// turn total damage to life and mana damage by converts (ex 30% of damage is taken from mana before life)
		var lifeTotal = 0;
		var manaTotal = 0;
		
		var manaShield = 0;
		var mana = 0;
		var lifeShield = 0;
		var life = 0;

		// apply life and mana damage to [shield and current] [lost] values
		var manaShieldDiff = manaTotal - s2.getCurrentResource(st.manaShield);
		if(s2.getCurrentManaShield() > 0) {
			if(manaShieldDiff > 0) {
				manaShield = s2.getCurrentManaShield();
				mana = manaShieldDiff;
			} else {
				manaShield = manaTotal;
			}
		} 
		var manaDiff = mana - s2.getCurrentMana();
		if(s2.getCurrentMana() > 0) {
			if(manaDiff > 0) {
				mana = s2.getCurrentMana();
				lifeTotal += manaDiff;
			} 
		}
		
		var lifeShieldDiff = lifeTotal - s2.getCurrentLifeShield();
		if(s2.getCurrentLifeShield() > 0) {
			if(lifeShieldDiff > 0) {
				lifeShield = s2.getCurrentLifeShield();
				life = lifeShieldDiff;
			} else {
				lifeShield = lifeTotal;
			}
		}
		var lifeDiff = life - s2.getCurrentLife();
		if(s2.getCurrentLife() > 0) {
			if(lifeDiff > 0) {
				life = s2.getCurrentLife(); // die
			} 
		}
		
//		s2.add(st.manaShield, mo.lost, manaShield);
//		s2.add(st.mana, 	  mo.lost, mana);
//		s2.add(st.lifeShield, mo.lost, lifeShield);
//		s2.add(st.life, 	  mo.lost, life);
		s2.lose(st.manaShield, manaShield);
		s2.lose(st.mana, mana);
		s2.lose(st.lifeShield, lifeShield);
		s2.lose(st.life, life);
		
		if(s2.getCurrentLife() <= 0) {
			// die
		}
		
	}
}