package gamemechanics.models;

import java.util.List;

import data.new1.SpellModel;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;
import gamemechanics.stats.NewStats;

public abstract class Spell {

	private final SpellModel model;
	
	// techniquement, les effets devraient rester les mêmes
	// potentiellement pourrait y avoir des status ou items qui les modifients (panos de classes dofus)
	// mais encore là, ça serait de compiler les stats/effets du caster dans onCast et canCast
	//private List<Effect> effects; // surtout pour générer la description des effets du sort

	// compile them inside onCast and canCast ?
	//public NewStats currStats;
	
	public Spell(SpellModel model) {
		this.model = model;
		//setCosts(costs);
	}
	
	public void onCast(Creature caster, Cell target) {
		model.onCast(caster, target);
	}
	public boolean canCast(Creature caster, Cell target) {
		return model.canCast(caster, target);
	}
	
	
	
	
	//public abstract void cast(Entity caster, Cell target);
	//public abstract void setCosts(List<BaseSpellCost> costs);
	
	//public boolean canCast(Cell target) {
		// conditions range, cost, cooldown, uses per turn, uses per turn per target, does target need to be a creature?, is target entity untargetable?...
		// then each spell can implement its own solution too..
	//	return false;
	//}
	
	/*
	 * Spell IDs:
	 * [00 00 0]
	 * first 2 : class id
	 * second 2 : spellgroup id
	 * last 2 : spell id (ends in 0 = a group, otherwise it's a spell)
	 * last digit diff = spells of the same group
	 * ex: 
	 * armour 0010
	 * red armour 0011
	 * blue armour 0012
	 * green armour 0013
	 * yellow armour 0014
	 * white armour 0015
	 * black armour 0016
	 * bubble 0021
	 * déluge 0031
	 * swamp 0041
	 * déluge 0031
	 */
	
	
	
}
