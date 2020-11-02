package com.souchy.randd.commons.diamond.models.stats;

import java.util.HashMap;
import java.util.Map;

import com.souchy.randd.commons.diamond.models.stats.base.BoolStat;
import com.souchy.randd.commons.diamond.statics.properties.Targetability;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;
import com.souchy.randd.commons.tealwaters.ecs.Entity;

import io.netty.buffer.ByteBuf;

public class Targetting implements BBSerializer, BBDeserializer {
	
	public Map<Targetability, BoolStat> targetability;
	
	public Targetting() {
		targetability = new HashMap<>();
		reset();
//		setBase(Targetability.CanBeCastedOn, false);
//		setBase(Targetability.CanBeCastedThrough, false);
//		setBase(Targetability.CanBeWalkedOn, false);
//		setBase(Targetability.CanBeWalkedThrough, false);
		
//		setBase(Targetability.CanCastOnBlocks, false);
//		setBase(Targetability.CanCastThroughBlocks, false);
//		setBase(Targetability.CanWalkOnBlocks, false);
//		setBase(Targetability.CanWalkThroughBlocks, false);
	}
	public void reset() {
		for(var t : Targetability.values()) 
			targetability.put(t, new BoolStat(false));
	}
	/** generic creature type, but certain creatures are more special (flyers can walk through holes, ghosts can walk through blocks...) */
	public void initCreature() {
		setBase(Targetability.CanBeCastedOn, true);
	}
	public void initCreatureFlyer() {
		setBase(Targetability.CanBeCastedOn, true);
		// TODO have a setting to be able to walk through holes but not blocks
	}
	public void initCreatureGhost() {
		setBase(Targetability.CanBeCastedOn, true);
		setBase(Targetability.CanBeCastedThrough, true);
		setBase(Targetability.CanBeWalkedThrough, true);
		//setBase(Targetability.CanWalkOnBlocks, true); // actually this is op if you can hide inside a block and cast spells while no one can hit you
		setBase(Targetability.CanWalkThroughBlocks, true);
	}
	public void initCellFloor() {
		setBase(Targetability.CanBeCastedOn, true);
		setBase(Targetability.CanBeCastedThrough, true);
		setBase(Targetability.CanBeWalkedOn, true);
		setBase(Targetability.CanBeWalkedThrough, true);
	}
	public void initCellHole() {
		setBase(Targetability.CanBeCastedThrough, true);
	}
	public void initCellBlock() {
		// keep everything false
	}

	
	/** should be used only at init step. Use setCan otherwise (for statuses and such) */
	public void setBase(Targetability targetting, boolean should) {
		targetability.get(targetting).base = should;
	}
	
	
	public boolean can(Targetability targetting) {
		return targetability.get(targetting).value();
	}
	public void setCan(Targetability targetting, boolean should) {
		targetability.get(targetting).replace(should);
	}
	
	

	/**
	 * If this entity can cast through an entity
	 */
	public boolean canCastThrough(Entity e) {
		var targetTargetting = e.get(Targetting.class);
		return can(Targetability.CanCastThroughBlocks) || targetTargetting.can(Targetability.CanBeCastedThrough);
	}
	/**
	 * If this entity can target cast on an entity
	 */
	public boolean canCastOn(Entity e) {
		var targetTargetting = e.get(Targetting.class);
		return can(Targetability.CanCastOnBlocks) || targetTargetting.can(Targetability.CanBeCastedOn);
	}
	/**
	 * If this entity can walk through an entity (without stopping on it)
	 */
	public boolean canWalkThrough(Entity e) {
		var targetTargetting = e.get(Targetting.class);
		return can(Targetability.CanWalkThroughBlocks) || targetTargetting.can(Targetability.CanBeWalkedThrough);
	}
	/**
	 * If this entity can target walk and stop on an entity
	 */
	public boolean canWalkOn(Entity e) {
		var targetTargetting = e.get(Targetting.class);
		return can(Targetability.CanWalkOnBlocks) || targetTargetting.can(Targetability.CanBeWalkedOn);
	}
	
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		for(var t : Targetability.values()) {
			this.targetability.get(t).serialize(out);
		}
		return out;
	}
	@Override
	public BBMessage deserialize(ByteBuf in) {
		for(var t : Targetability.values()) {
			this.targetability.get(t).deserialize(in);
		}
		return null;
	}
	
	
}
