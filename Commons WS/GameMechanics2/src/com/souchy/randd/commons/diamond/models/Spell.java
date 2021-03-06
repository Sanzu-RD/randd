package com.souchy.randd.commons.diamond.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.resources.ResourceGainLoss;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.ecs.Entity;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.jade.Constants;

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
	
	public int modelidWithoutSeason() {
		return modelid() % (int) Math.pow(10, (int) Math.log10(modelid())) % (int) Math.pow(10, (int) Math.log10(modelid()));
	}
	
	public final ImmutableList<CreatureType> taggedCreatureTypes;
	public final ImmutableList<Class<CreatureModel>> taggedCreatures;
	public final ImmutableList<Element> taggedElements;
	
	public SpellStats stats;
	public List<Effect> effects = new ArrayList<>();
	
	public Spell(Fight f) {
		super(f);
		id = idCounter++;
		initBaseStats(stats = new SpellStats());
		taggedElements = initElements();
		taggedCreatureTypes = initCreatureTypes();
		taggedCreatures = null; // initCreatures();
		// effects = initEffects();
	}
	
	/**
	 * Initialize spell's costs, range, targetting conditions / los, cooldown, aoes, etc <p>
	 * <b>Important : <br>
	 * stats.costs.put(Resource.mana, new IntStat(3)); <br>
	 * stats.maxRangeRadius.baseflat = 10;</b>
	 */
	protected abstract void initBaseStats(SpellStats stats);
	/**
	 * @return ImmutableList.of(Elements.water) associated elements
	 */
	protected abstract ImmutableList<Element> initElements();
	/**
	 * @return ImmutableList.of() associated creature types
	 */
	protected abstract ImmutableList<CreatureType> initCreatureTypes();
	
	
	public Spell getModel() {                       
		return DiamondModels.spells.get(modelid());   
	}                                               

	/**
	 * Actual casting action. <p>
	 * - <b>Copies the whole spell</b> <br>
	 * - Sends CastSpellEvent to <b>interceptors and modifiers</b> <br>
	 * - Casts the spell with <b>event.spell.cast0</b> <br>
	 * - Sends CastSpellEvent to <b>reactors</b> <br>
	 */
	public void cast(Creature caster, Cell target) {
		// Applique les couts en premier?
		var costs = new HashMap<Resource, Integer>();
		this.stats.costs.forEach((r, i) -> costs.put(r, i.value()));
		ResourceGainLoss.use(caster, costs);
		
		// Cr???? une copie du spell pour pouvoir le modifier dans l'eventpipeline
		var spellcopy = this.copy();
		// puis cast le spell 
		var event = new CastSpellEvent(caster, target, spellcopy);
		Log.format("Spell (%s) cast interceptors", spellcopy.hash());
		get(Fight.class).statusbus.interceptors.post(event); // permet l'interruption
		Log.format("Spell (%s) cast modificators", spellcopy.hash());
		get(Fight.class).statusbus.modifiers.post(event); // permet la modification 
		if(!event.intercepted) {
			Log.format("Spell (%s) cast cast0", spellcopy.hash());
			spellcopy.cast0(caster, target);
		}
		Log.format("Spell (%s) cast reactors", spellcopy.hash());
		get(Fight.class).statusbus.reactors.post(event); // permet la reaction 
		Log.format("Spell (%s) cast dispose", spellcopy.hash());
		spellcopy.dispose(); // dispose de la copie
	}
	
	/**
	 * Individual spell implementation of cast. <br>
	 * The spell is a <b>copy</b> at this point.
	 */
	protected abstract void cast0(Creature caster, Cell target);
	
	
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
		if(target == null) return false;
		
		var board = this.get(Fight.class).board;
		
		// si la cellule est targetable (ex: pas un mur ou un trou)
		if(!board.checkCellViewOn(caster, target)) return false;

		// si on a besoin d'une ligne de vue et qu'il n'y en a pas
		if(stats.target.accepts(TargetType.needsLineOfSight) && !board.checkView(caster, target.pos)) return false;	

		// si la cellule est dans la port??e
//		if(!board.checkRange(getRange(), caster.pos, target.pos)) return false;
		
		// si la cellule est vide
		if(!target.hasCreature()) {
			// si on accepte une cellule vide
			return stats.target.accepts(TargetType.empty);
		}
		
		// si la cellule a une ou plusieurs creatures
		{
			// si on accepte une cellule pleine
			if(!stats.target.accepts(TargetType.full)) return false;
			
			var c = target.getCreatures().get(0);
			
			// si le target est le caster et qu'on accepte le self-target
			if(caster == c && !stats.target.accepts(TargetType.self)) return false;
			
			// si on accepte les alli??s
			if(caster.team == c.team && !stats.target.accepts(TargetType.allies)) return false;
			// si on accepte les ennemis
			if(caster.team != c.team && !stats.target.accepts(TargetType.enemies)) return false;
			
			// si on accepte les summoners
			if(c.summonerID == 0 && !stats.target.accepts(TargetType.summoners)) return false;
			// si on accepte les summons
			if(c.summonerID != 0 && !stats.target.accepts(TargetType.summons)) return false;
		}

		// si tout passe, return true
		return true;
	}
	
	public Aoe getRange() {
		// aoe port??e max
		Aoe range = stats.maxRangePattern.value().build(stats.maxRangeRadius.value());
		var rangeCenter = range.table.center();
		
		// soustrais l'aoe port??e min s'il existe
		if(stats.minRangePattern.value() != null) {
			Aoe minAoe = stats.minRangePattern.value().build(stats.minRangeRadius.value());
			var minCenter = minAoe.table.center();
			minAoe.move((int) (rangeCenter.x - minCenter.x), (int) (rangeCenter.y - minCenter.y));
			range.sub(minAoe);
		}
		return range;
	}

	/**
	 * Copy spell with this : <br>
	 * var s = new XSpell(get(Fight.class)); <br>
	 * s.baseStats = stats.copy(); <br>
	 * return s;
	 * @return
	 */
	public abstract Spell copy(Fight fight);
	
	/**
	 * Be careful not to use this on a spell model because they dont have a fight reference. <br>
	 * Use spell.copy(Fight fight) instead in those cases.
	 */
	public Spell copy() {
		return this.copy(get(Fight.class));
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(modelid()); // written in here but deserialize outside (in update packets) because we create the spell by copying a model so we need the modelid before deserializing the spell instance
		
		out.writeInt(id);
		this.stats.serialize(out);
		// serialize effects
		this.effects.forEach(e -> e.serialize(out));
		return out;
	}
	
	@Override
	public BBMessage deserialize(ByteBuf in) {
		this.id = in.readInt();
		this.stats.deserialize(in);
		// deserialize effects
		this.effects.forEach(e -> e.deserialize(in));
		return null;
	}
	
	
}
