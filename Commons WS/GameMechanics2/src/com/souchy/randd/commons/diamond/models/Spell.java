package com.souchy.randd.commons.diamond.models;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetingProperty;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;
import com.souchy.randd.commons.tealwaters.ecs.Entity;

import io.netty.buffer.ByteBuf;

/**
 * model and instance in one, like statuses and probably effects too.
 * 
 * FIXME might separate model-instance another time? hard choice for custom properties inside a spellmodel
 * FIXME might need string keys for entity components
 * 
 * @author Blank
 * @date 10 juin 2020
 */
public abstract class Spell extends Entity implements BBSerializer, BBDeserializer {
	
	/**
	 * Need to compile creature stats before compiling them into the spell stats
	 * (spell stats = baseSpellStats + creatureStats)
	 * 
	 * edit try to use instance to separate from model @date 10 juin 2020
	 * 
	 * @author Blank
	 * /
	/*
	public static class SpellInstance extends Entity {
//		public final SpellModel model;
//		public List<Element> elements; // can change elements
		
		public final int modelid;
		public SpellStats stats;
		
		public SpellInstance(Fight f, Spell model) {
			super(f);
//			this.model = model;
			this.modelid = model.id();
			stats = model.baseStats.copy();
		}
		public void cast(Creature caster, Cell target) {
			getModel().onCast(this, caster, target);
		}
		public void canCast(Creature caster) {
			getModel().canCast(this, caster);
		}
		public void canTarget(Creature caster, Cell target) {
			getModel().canTarget(this, caster, target);
		}
		public Spell getModel() {
			return DiamondModels.spells.get(modelid);
		}
	}
	*/
	private static int idCounter = 0;
	
	
	public int id;
	public abstract int modelid();
	
	public final ImmutableList<CreatureType> taggedCreatureTypes;
	public final ImmutableList<Class<CreatureModel>> taggedCreatures;
	public final ImmutableList<Element> taggedElements;
	
	public SpellStats stats;
	public List<Effect> effects = new ArrayList<>();
	
	public Spell(Fight f) {
		super(f);
		id = idCounter++;
		stats = initBaseStats();
		taggedElements = initElements();
		taggedCreatureTypes = initCreatureTypes();
		taggedCreatures = null; // initCreatures();
		// effects = initEffects();
	}
	
	protected abstract SpellStats initBaseStats();
	protected abstract ImmutableList<Element> initElements();
	protected abstract ImmutableList<CreatureType> initCreatureTypes();
	
	public Spell getModel() {                       
		return DiamondModels.spells.get(modelid());   
	}                                               
	
	/**
	 * Actual casting action. Applies all effects.
	 */
	public abstract void onCast(Creature caster, Cell target);
	
	/**
	 * Check if the spell can be cast at all : checks costs, conditions
	 */
	public boolean canCast(Creature caster) {
		for (var e : this.stats.costs.entrySet()) {
			if(caster.stats.resources.get(e.getKey()).value() < e.getValue().value()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Check if the spell can have a specific target. Notably used to create the
	 * highlight preview to know whats cells are targetable
	 */
	public boolean canTarget(Creature caster, Cell target) {
		// si la cellule est vide
		if(!target.hasCreature()) {
			// si on accepte une cellule vide
			return stats.target.accepts(TargetingProperty.empty);
		}
		
		// si la cellule a une ou plusieurs creatures
		
		// si on accepte une cellule pleine
		if(!stats.target.accepts(TargetingProperty.full)) return false;
		
		var c = target.getCreatures().get(0);
		
		// si le target est le caster et qu'on accepte le self-target
		if(caster == c && !stats.target.accepts(TargetingProperty.self)) return false;
		
		// si on accepte les alliés
		if(caster.team == c.team && !stats.target.accepts(TargetingProperty.allies)) return false;
		// si on accepte les ennemis
		if(caster.team != c.team && !stats.target.accepts(TargetingProperty.enemies)) return false;
		
		// si on accepte les summoners
		if(c.summonerID == 0 && !stats.target.accepts(TargetingProperty.summoners)) return false;
		// si on accepte les summons
		if(c.summonerID != 0 && !stats.target.accepts(TargetingProperty.summons)) return false;
		
		// si on a besoin d'une ligne de vue et qu'il y en a une
		if(!this.get(Fight.class).board.checkView(caster, target.pos) && stats.target.accepts(TargetingProperty.needsLineOfSight)) return false;	
		
		// si la cellule est dans la portée
		if(!this.get(Fight.class).board.checkRange(stats, caster.pos, target.pos)) return false;
		
		// si tout passe,  return true
		return true;
	}

	/**
	 * Copy spell with this : <br>
	 * var s = new XSpell(get(Fight.class)); <br>
	 * s.baseStats = stats.copy(); <br>
	 * return s;
	 * @return
	 */
	public abstract Spell copy(Fight fight);
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(modelid()); // written in here but deserialize outside (in update packets) because we creaate the spell by copying a model so we need the modelid before deserializing the spell instance
		
		out.writeInt(id);
		this.stats.serialize(out);
		// serialize effects
		return out;
	}
	
	@Override
	public BBMessage deserialize(ByteBuf in) {
		this.id = in.readInt();
		this.stats.deserialize(in);
		// deserialize effects
		return null;
	}
	
	
}
