package data.new1.spellstats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;

import data.new1.spellstats.base.BoolStat;
import data.new1.spellstats.base.IntStat;
import data.new1.spellstats.base.ObjectStat;
import data.new1.spellstats.imp.TargetConditionStat;
import data.new1.timed.Status;
import gamemechanics.common.Aoe;
import gamemechanics.common.AoeBuilders;
import gamemechanics.data.effects.damage.Damage;
import gamemechanics.models.Fight;
import gamemechanics.models.SpellModel;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;
import gamemechanics.models.entities.Entity;
import gamemechanics.models.entities.Entity.EntityRef;
import gamemechanics.statics.CreatureType;
import gamemechanics.statics.Element;
import gamemechanics.statics.stats.properties.Resource;
import io.netty.buffer.ByteBuf;

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
	
	
	public static class Shockbomb extends SpellModel {
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
		public int id() {
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		protected SpellStats initBaseStats() {
			// TODO Auto-generated method stub
			return null;
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
			
			var aoe = shockPattern.base.get();
			var board = target.fight.board;
			
			// for all cells in the AOE
			aoe.table.foreach((x, y) -> {
				new Damage(shockPattern.base.get(), new TargetConditionStat(), new HashMap<>());
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
	}
	
	public static class Overcharge {
		public void onGain(Creature c) {
			for(var spell : c.spellbook) {
				if(spell.id() == 0) {
					var s = ((Shockbomb)spell);
					s.shockPattern.setter = () -> AoeBuilders.circle.apply(s.shockRadiusMax.value())
													.sub(AoeBuilders.circle.apply(s.shockRadiusMin.value()));
					//new IntStat(AoePattern.Square3.ordinal());
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
	
	
	/*
	 * Snapshop effect : onGain : add  /  onLose : substract
	 * Realtime effect : onUpdate : substract, update, add  /  onLose : substract
	 */
	
	
//	public int getMinRange() {
//		if(this.has(MinRange.class)) return get(MinRange.class).value();
//		return 0;
//	}
	
}
