package com.souchy.randd.commons.diamond.models.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.common.AoeBuilders.AoeBuilder;
import com.souchy.randd.commons.diamond.models.stats.base.BoolStat;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.models.stats.base.ObjectStat;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;

import io.netty.buffer.ByteBuf;

public class SpellStats implements BBSerializer, BBDeserializer { //extends Entyty {
	
	// cast costs ======================================================
	public Map<Resource, IntStat> costs = new HashMap<>();

	// cell targetting conditions, inclu lineofsight ===================
	public TargetTypeStat target = new TargetTypeStat();
	
	// cast ranges and patterns  =======================================
	/** Range minimale. 0 par défaut */
	public IntStat minRangeRadius = new IntStat(0);
	/** Range maximale. 1 par défaut */
	public IntStat maxRangeRadius = new IntStat(1);
	/** Pattern de range maximale. null par défaut. */
	public ObjectStat<AoeBuilder> minRangePattern = new ObjectStat<AoeBuilder>(null);
	/** Pattern de range maximale. Cercle par défaut. <br>
	 * Example : stats.maxRangePattern.base = (t) -> AoeBuilders.cross.apply(t); */
	public ObjectStat<AoeBuilder> maxRangePattern = new ObjectStat<AoeBuilder>(r -> AoeBuilders.circle.apply(r));
	
	// cast cooldowns ==================================================
	public IntStat cooldown = new IntStat(0);
	public IntStat castPerTurn = new IntStat(0);
	public IntStat castPerTarget = new IntStat(0);
	
	
	// if the user can rotate the AOE manually or if it is based off the character's orientation
	public BoolStat canRotate;
	// publicly modifiable aoes (spells create their aoes and place them here so that other classes can modify them without knowing the spell .class)
	public List<ObjectStat<Aoe>> aoes = new ArrayList<>();
	
	
	public void addAoe(Aoe aoe) {
		aoes.add(new ObjectStat<Aoe>(aoe));
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
		s.minRangePattern = new ObjectStat<AoeBuilder>(minRangePattern.base);
		if(minRangePattern.setter != null) s.minRangePattern.setter = minRangePattern.setter; 
		s.maxRangePattern = new ObjectStat<AoeBuilder>(maxRangePattern.base); 
		if(maxRangePattern.setter != null) s.maxRangePattern.setter = maxRangePattern.setter; 
		
		s.cooldown = cooldown.copy();
		s.castPerTurn = castPerTurn.copy();
		s.castPerTarget = castPerTarget.copy();
		
//		s.lineOfSight = lineOfSight.copy(); // maintenant inclu dans target v
		s.target = target.copy();
		
		return s;
	}

	@Override
	public ByteBuf serialize(ByteBuf out) {
		
		minRangeRadius.serialize(out);
		maxRangeRadius.serialize(out);
		
		cooldown.serialize(out);
		castPerTurn.serialize(out);
		castPerTarget.serialize(out);
		
//		lineOfSight.serialize(out);
		target.serialize(out);

		// costs
		out.writeInt(costs.size());
		costs.forEach((r, i) -> {
			out.writeInt(r.ordinal());
			i.serialize(out);
		});
		
		// aoes
		out.writeInt(aoes.size());
		aoes.forEach((a) -> a.serialize(out));
		
		// range patterns
		minRangePattern.serialize(out);
		maxRangePattern.serialize(out);
		
		return out;
	}
	
	

	@Override
	public BBMessage deserialize(ByteBuf in) {
		
		minRangeRadius.deserialize(in);
		maxRangeRadius.deserialize(in);

		cooldown.deserialize(in);
		castPerTurn.deserialize(in);
		castPerTarget.deserialize(in);
		
//		lineOfSight.deserialize(in);
		target.deserialize(in);

		// costs
		int costcount = in.readInt();
		for(int i = 0; i < costcount; i++) {
			int ordinal = in.readInt();
			var val = new IntStat(0);
			val.deserialize(in);
			this.costs.put(Resource.values()[ordinal], val);
		}
		
		// aoes
		int aoecount = in.readInt();
		for(int i = 0; i < aoecount; i++) {
			boolean hasbase = in.readBoolean();
			boolean hassetter = in.readBoolean();
			var val = new ObjectStat<Aoe>(null);
			if(hasbase) {
				val.base = new Aoe(0, 0);
				val.base.deserialize(in);
			}
			if(hassetter) {
				val.setter = new Aoe(0, 0);
				val.setter.deserialize(in);
			}
			this.aoes.add(val);
		}
		
		// min range pattern
		boolean hasbase = in.readBoolean();
		boolean hassetter = in.readBoolean();
		if(hasbase) minRangePattern.base = AoeBuilder.deserialize(in);
		if(hassetter) minRangePattern.base = AoeBuilder.deserialize(in);
		
		// max range pattern
		hasbase = in.readBoolean();
		hassetter = in.readBoolean();
		if(hasbase) minRangePattern.base = AoeBuilder.deserialize(in);
		if(hassetter) minRangePattern.base = AoeBuilder.deserialize(in);
		
		return null;
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
