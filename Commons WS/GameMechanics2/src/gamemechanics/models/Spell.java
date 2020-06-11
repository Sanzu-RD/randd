package gamemechanics.models;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;

import data.new1.Effect;
import data.new1.ecs.Entity;
import data.new1.spellstats.SpellStats;
import gamemechanics.main.DiamondModels;
import gamemechanics.statics.CreatureType;
import gamemechanics.statics.Element;
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
	
	public int id;
	public abstract int modelid();
	
	public final ImmutableList<CreatureType> taggedCreatureTypes;
	public final ImmutableList<Class<CreatureModel>> taggedCreatures;
	public final ImmutableList<Element> taggedElements;
	
	public SpellStats stats;
	public List<Effect> effects = new ArrayList<>();
	
	public Spell(Fight f) {
		super(f);
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
	 * Check if the spell can be cast at all.
	 */
	public abstract boolean canCast(Creature caster);
	
	/**
	 * Check if the spell can have a specific target. Notably used to create the
	 * highlight preview to know whats cells are targetable
	 */
	public abstract boolean canTarget(Creature caster, Cell target);

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
