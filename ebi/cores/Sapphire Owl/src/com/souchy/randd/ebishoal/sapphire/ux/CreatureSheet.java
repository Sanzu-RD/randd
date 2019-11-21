package com.souchy.randd.ebishoal.sapphire.ux;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.github.czyzby.lml.parser.impl.tag.AbstractActorLmlTag;
import com.github.czyzby.lml.parser.impl.tag.AbstractGroupLmlTag;
import com.github.czyzby.lml.parser.impl.tag.AbstractNonParentalActorLmlTag;
import com.github.czyzby.lml.parser.tag.LmlActorBuilder;
import com.github.czyzby.lml.parser.tag.LmlTag;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireHud;

import gamemechanics.models.entities.Creature;
import gamemechanics.statics.stats.properties.Resource;

public class CreatureSheet extends SapphireWidget {

	public final Creature creature;

	@LmlActor("")
	public List<StatusIcon> icons;
	
	public CreatureSheet(Creature c) {
		creature = c;
		
		icons = new ArrayList<>();
		creature.getStatus().forEach(s -> icons.add(new StatusIcon(s)));
		
		// inject
		SapphireHud.parser.createView(this, getTemplateFile());
	}

	@Override
	public String getTemplateId() {
		return "creaturesheet";
	}
	
	public int getStatusCount() {
		return creature.getStatus().size();
	}
	
	public int getCreatureId() {
		return 0; //creature.id();
	}
	
	public int getHp() {
		return creature.getStats().getResourceCurrent(Resource.life, false);
	}
	public int getHpShield() {
		return creature.getStats().getResourceCurrent(Resource.life, true);
	}
	public int getHpMax() {
		return creature.getStats().getResourceMax(Resource.life);
	}

	public int getMana() {
		return creature.getStats().getResourceCurrent(Resource.mana, false);
	}
	public int getManaShield() {
		return creature.getStats().getResourceCurrent(Resource.mana, true);
	}
	public int getManaMax() {
		return creature.getStats().getResourceMax(Resource.mana);
	}

	public int getMove() {
		return creature.getStats().getResourceCurrent(Resource.move, false);
	}
	public int getMoveShield() {
		return creature.getStats().getResourceCurrent(Resource.move, true);
	}
	public int getMoveMax() {
		return creature.getStats().getResourceMax(Resource.move);
	}
	
}
