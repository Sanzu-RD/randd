package gamemechanics.data.spells;

import java.util.List;

import gamemechanics.models.Spell;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;
import gamemechanics.stats.BaseSpellCost;
import gamemechanics.stats.StatModifier.st;

public class Walk extends Spell {

	@Override
	public void cast(Entity caster, Cell target) {
		var board = caster.fight.board;
	}

	
	@Override
	public void setCosts(List<BaseSpellCost> costs) {
		costs.add(new BaseSpellCost(st.move, 1));
	}


	@Override
	public String getSpellFX() {
		return "";
	}

	@Override
	public String getCasterAnimation() {
		return "walk0";
	}
	
}

