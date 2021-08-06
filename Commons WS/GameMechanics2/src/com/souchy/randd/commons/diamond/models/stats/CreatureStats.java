package com.souchy.randd.commons.diamond.models.stats;

import java.util.HashMap;
import java.util.Map;

import com.souchy.randd.commons.diamond.models.stats.base.BoolStat;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.models.stats.special.HeightStat;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.filters.Height;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;
import com.souchy.randd.commons.tealwaters.logging.Log;

import io.netty.buffer.ByteBuf;

public class CreatureStats implements BBSerializer, BBDeserializer {
	
	/** 
	 * resources 
	 */
	public Map<Resource, IntStat> resources;
	public Map<Resource, IntStat> resourcesmax;
	/** 
	 * shields for each resources 
	 */
	public Map<Resource, IntStat> shield;
	/** 
	 * how spells scale 
	 * sourcedmg = (baseflat + casterflat) * (1 + baseinc * casterinc / 100) * (1 + basemore * castermore / 100)
	 */
	public Map<Element, IntStat> affinity;
	/** 
	 * scales against damage 
	 * finaldmg = sourcedmg * (1 + (casterPenMore-targetResMore) / 100) * (1 + (casterPenInc-targetResInc) / 100 ) + (casterPenFlat-targetResFlat)
	 */
	public Map<Element, IntStat> resistance; 
	
	/** 
	 * penetration counters resists 
	 * finaldmg = sourcedmg * (1 + (casterPenMore-targetResMore) / 100) * (1 + (casterPenInc-targetResInc) / 100 ) + (casterPenFlat-targetResFlat)
	 */
	public Map<Element, IntStat> penetration; 
	
	/** 
	 * this adds and multiplies to healing spells casts 
	 * sourceheal =  (baseflat + casterflat) * (1 + baseinc * casterinc / 100) * (1 + basemore * castermore / 100)
	 */
	public IntStat healingAffinity;
	
	/** 
	 * this adds and multiplies to healing spells received 
	 * finalheal = sourceheal * (1 - targetResMore / 100) * (1 - targetResInc / 100 ) - targetResFlat
	 */
	public IntStat healingRes;
	
	/** 
	 * range increase value 
	 */
	public IntStat range;
	
	/** 
	 * max number of summons
	 */
	public IntStat summons;
	
	/**
	 * if creature is visible/invisible
	 */
	public BoolStat invisible;
	
	/**
	 * Z / height of the creature. ex: flying or burrowed under ground. Default is normal 'floor' level
	 */
	public HeightStat height;
	
	
	public CreatureStats() {
		resources = new HashMap<>();
		shield = new HashMap<>();
		affinity = new HashMap<>();
		resistance = new HashMap<>();
		penetration = new HashMap<>();
		
		healingAffinity = new IntStat(0);
		healingRes = new IntStat(0);
		range = new IntStat(0);
		summons = new IntStat(0);
		invisible = new BoolStat(false);
		height = new HeightStat(Height.floor.bit);
		// + peut-être une stat pour aoeRadiusModificator / AoeRange
		
		for(var v : Resource.values()) {
			resources.put(v, new IntStat(0));
			shield.put(v, new IntStat(0));
		}
		for(var ele : Element.values) {
			//Log.info("creature stat element : " + ele);
			affinity.put(ele, new IntStat(0));
			resistance.put(ele, new IntStat(0));
			penetration.put(ele, new IntStat(0));
		}
	}
	
	public CreatureStats copy() {
		final var s = new CreatureStats();
		
		// resources.forEach((r, i) -> s.resources.put(r, i.copy()));
		// shield.forEach((r, i) -> s.shield.put(r, i.copy()));
		// affinity.forEach((r, i) -> s.affinity.put(r, i.copy()));
		// resistance.forEach((r, i) -> s.resistance.put(r, i.copy()));
		// penetration.forEach((r, i) -> s.penetration.put(r, i.copy()));
		
		for (var r : Resource.values()) {
			s.resources.put(r, resources.get(r).copy());
			s.shield.put(r, shield.get(r).copy());
		}
		
		for (var r : Element.values) {
			s.affinity.put(r, affinity.get(r).copy());
			s.resistance.put(r, resistance.get(r).copy());
			s.penetration.put(r, penetration.get(r).copy());
		}
		
		s.healingAffinity = healingAffinity.copy();
		s.healingRes = healingRes.copy();
		s.range = range.copy();
		s.summons = summons.copy();
		s.invisible = invisible.copy();
		s.height = height.copy();
		
		return s;
	}
	
	
	public int getCurrent(Resource res) {
		return resources.get(res).value();
	}
	public int getMax(Resource res) {
		return resources.get(res).max();
	}
	public int getMissing(Resource res) {
		return (int) resources.get(res).fight;
	}
	
	public void add(CreatureStats s) {
		for (var r : Resource.values()) {
			resources.get(r).add(resources.get(r));
			resourcesmax.get(r).add(resourcesmax.get(r));
			shield.get(r).add(shield.get(r));
		}
		
		for (var r : Element.values) {
			affinity.get(r).add(affinity.get(r));
			resistance.get(r).add(resistance.get(r));
			penetration.get(r).add(penetration.get(r));
		}
		
		healingAffinity.add(s.healingAffinity);
		healingRes.add(s.healingRes);
		range.add(s.range);
		summons.add(s.summons);
		
		invisible.replace(s.invisible.base);
		
		//s.height = height.copy(); // ne pas toucher à la height de la creature
	}

	public void remove(CreatureStats s) {
		for (var r : Resource.values()) {
			resources.get(r).remove(resources.get(r));
			shield.get(r).remove(shield.get(r));
		}
		
		for (var r : Element.values) {
			affinity.get(r).remove(affinity.get(r));
			resistance.get(r).remove(resistance.get(r));
			penetration.get(r).remove(penetration.get(r));
		}
		
		healingAffinity.remove(s.healingAffinity);
		healingRes.remove(s.healingRes);
		range.remove(s.range);
		summons.remove(s.summons);
		
		invisible.reset();
		
		//s.height = height.copy(); // ne pas toucher à la height de la creature
	}

	@Override
	public ByteBuf serialize(ByteBuf out) {
		// resources.forEach((r, i) -> i.serialize(out));
		// shield.forEach((r, i) -> i.serialize(out));
		// affinity.forEach((r, i) -> i.serialize(out));
		// resistance.forEach((r, i) -> i.serialize(out));
		// penetration.forEach((r, i) -> i.serialize(out));
		
		for (var r : Resource.values()) {
			resources.get(r).serialize(out);
			shield.get(r).serialize(out);
		}
		for (var r : Element.values) {
			affinity.get(r).serialize(out);
			resistance.get(r).serialize(out);
			penetration.get(r).serialize(out);
		}
		
//		Log.info("Stats serialize mvm " + resources.get(Resource.move).value());
		
		healingAffinity.serialize(out);
		healingRes.serialize(out);
		range.serialize(out);
		summons.serialize(out);
		invisible.serialize(out);
		height.serialize(out);
		return null;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
//		resources.forEach((r, i) -> i.deserialize(in));
//		shield.forEach((r, i) -> i.deserialize(in));
//		affinity.forEach((e, i) -> i.deserialize(in));
//		resistance.forEach((e, i) -> i.deserialize(in));
//		penetration.forEach((e, i) -> i.deserialize(in));
		
		for (var r : Resource.values()) {
			resources.get(r).deserialize(in);
			shield.get(r).deserialize(in);
		}
		
		for (var r : Element.values) {
			affinity.get(r).deserialize(in);
			resistance.get(r).deserialize(in);
			penetration.get(r).deserialize(in);
		}
		
//		Log.info("Stats deserialize mvm " + resources.get(Resource.move).value());
		
		this.healingAffinity.deserialize(in);
		this.healingRes.deserialize(in);
		this.range.deserialize(in);
		this.summons.deserialize(in);
		this.invisible.deserialize(in);
		this.height.deserialize(in);
		return null;
	}

	
}
