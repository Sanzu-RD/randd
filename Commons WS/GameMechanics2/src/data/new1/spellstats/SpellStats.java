package data.new1.spellstats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import data.new1.spellstats.base.BoolStat;
import data.new1.spellstats.base.IntStat;
import data.new1.spellstats.base.ObjectStat;
import data.new1.spellstats.imp.TargetConditionStat;
import gamemechanics.common.Aoe;
import gamemechanics.common.AoeBuilders;
import gamemechanics.data.effects.damage.Damage;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;
import gamemechanics.models.Fight;
import gamemechanics.models.Spell;
import gamemechanics.statics.CreatureType;
import gamemechanics.statics.Element;
import gamemechanics.statics.stats.properties.Resource;

public class SpellStats { //extends Entyty {
	
	// cast costs
	public Map<Resource, IntStat> costs = new HashMap<>();
	
	// cast ranges and pattern for the cast range
	public IntStat minRangeRadius = new IntStat(0);
	public IntStat maxRangeRadius = new IntStat(0);
	public ObjectStat<Aoe> minRangePattern = new ObjectStat<Aoe>(AoeBuilders.single.get());
	public ObjectStat<Aoe> maxRangePattern = new ObjectStat<Aoe>(AoeBuilders.single.get());
	
	// cast cooldowns
	public IntStat cooldown = new IntStat(0);
	public IntStat castPerTurn = new IntStat(0);
	public IntStat castPerTarget = new IntStat(0);
	
	// cast line of sight
	public BoolStat lineOfSight = new BoolStat(true);
	
	// publicly modifiable aoes (spells create their aoes and place them here so that other classes can modify them without knowing the spell .class)
	public List<ObjectStat<Aoe>> aoes = new ArrayList<>();
	

//	public BiPredicate<Creature, SpellModel> predicate = (c, s) -> true;
//	public SpellStats() {
//		
//	}
//	public SpellStats(BiPredicate<Creature, SpellModel> predicate) {
//		this.predicate = predicate;
//	}
	
	
	public static class Shockbomb extends Spell {
		public Shockbomb(Fight f) {
			super(f);
		}
		// this doesnt apply to the cast, it applies to an effect so it's specific to this spell so it's here
		// aoe pattern for the shock;
		//public IntStat shockPattern;
		public IntStat shockRadiusMin;
		public IntStat shockRadiusMax;
		public ObjectStat<Supplier<Aoe>> shockPattern = new ObjectStat<>(() -> {
			return AoeBuilders.circle.apply(shockRadiusMax.value())
			       .sub(AoeBuilders.circle.apply(shockRadiusMin.value()));
		});
		
		@Override
		public int modelid() {
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		protected SpellStats initBaseStats() {
			var stats = new SpellStats();
			return stats;
		}
		@Override
		protected ImmutableList<Element> initElements() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		protected ImmutableList<CreatureType> initCreatureTypes() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void onCast(Creature caster, Cell target) {
			var fight = caster.get(Fight.class);
			var board = fight.board;
			
			var aoe = shockPattern.base.get();
			
			// for all cells in the AOE
			aoe.table.foreach((x, y) -> {
				new Damage(fight, shockPattern.base.get(), new TargetConditionStat(), new HashMap<>());
			});
		}
		@Override
		public boolean canCast(Creature caster) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean canTarget(Creature caster, Cell target) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public Spell copy(Fight fight) {
			var s = new Shockbomb(fight);
			s.stats = stats.copy();
			return s;
		}
	}
	
	public static class Overcharge {
		public void onGain(Creature c) {
			for(var spell : c.spellbook) {
				if(spell.modelid() == 0) {
					var s = ((Shockbomb) spell.getModel());
					
					spell.stats.aoes.get(0).setter = 
							AoeBuilders.circle.apply(s.shockRadiusMax.value())
							.sub(AoeBuilders.circle.apply(s.shockRadiusMin.value()));
					
//					s.shockPattern.setter = () -> AoeBuilders.circle.apply(s.shockRadiusMax.value())
//													.sub(AoeBuilders.circle.apply(s.shockRadiusMin.value()));
				}
			}
		}
	}
	
	public static class BarrageSupport {
		public void onGain(Creature c) {
			for(var spell : c.spellbook) {
				var aoes = new ArrayList<ObjectStat<Aoe>>(); // spell.aoes;
				aoes.forEach(a -> {
					a.setter = AoeBuilders.single.get(); //new IntStat(AoePattern.Single.ordinal());
				});
			}
		}
	}

	public SpellStats copy() {
		final var s = new SpellStats();
		
		costs.forEach((r, i) -> s.costs.put(r, i.copy()));
		aoes.forEach((a) -> {
			var o = new ObjectStat<Aoe>(a.base.copy());
			if(a.setter != null) o.setter = a.setter.copy();
			s.aoes.add(o);
		});
		
		s.minRangeRadius = minRangeRadius.copy();
		s.maxRangeRadius = maxRangeRadius.copy();
		s.minRangePattern = new ObjectStat<Aoe>(minRangePattern.base.copy());
		if(minRangePattern.setter != null) s.minRangePattern.setter = minRangePattern.setter.copy();
		s.maxRangePattern = new ObjectStat<Aoe>(maxRangePattern.base.copy());
		if(maxRangePattern.setter != null) s.maxRangePattern.setter = maxRangePattern.setter.copy();

		s.cooldown = cooldown.copy();
		s.castPerTurn = castPerTurn.copy();
		s.castPerTarget = castPerTarget.copy();
		
		s.lineOfSight = lineOfSight.copy();
		
		return s;
	}
	
	
	/*
	 * Snapshop effect : onGain : add  /  onLose : substract
	 * Realtime effect : onUpdate : substract, update, add  /  onLose : substract
	 */
	
	
//	public int getMinRange() {
//		if(this.has(MinRange.class)) return get(MinRange.class).value();
//		return 0;
//	}
	
}
