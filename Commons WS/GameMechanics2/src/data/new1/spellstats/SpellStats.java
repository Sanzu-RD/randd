package data.new1.spellstats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;

import data.new1.spellstats.base.BoolStat;
import data.new1.spellstats.base.IntStat;
import data.new1.spellstats.base.ObjectStat;
import gamemechanics.common.Aoe;
import gamemechanics.common.AoeBuilders;
import gamemechanics.statics.stats.properties.Resource;
import io.netty.buffer.ByteBuf;

public class SpellStats implements BBSerializer, BBDeserializer { //extends Entyty {
	
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

	@Override
	public ByteBuf serialize(ByteBuf out) {
		
		minRangeRadius.serialize(out);
		maxRangeRadius.serialize(out);
		
		cooldown.serialize(out);
		castPerTurn.serialize(out);
		castPerTarget.serialize(out);
		
		lineOfSight.serialize(out);

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
		
		lineOfSight.deserialize(in);

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
		if(hasbase) {
			minRangePattern.base = new Aoe(0, 0);
			minRangePattern.base.deserialize(in);
		}
		if(hassetter) {
			minRangePattern.setter = new Aoe(0, 0);
			minRangePattern.setter.deserialize(in);
		}
		
		// max range pattern
		hasbase = in.readBoolean();
		hassetter = in.readBoolean();
		if(hasbase) {
			maxRangePattern.base = new Aoe(0, 0);
			maxRangePattern.base.deserialize(in);
		}
		if(hassetter) {
			maxRangePattern.setter = new Aoe(0, 0);
			maxRangePattern.setter.deserialize(in);
		}
		
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
