package com.souchy.randd.commons.diamond.models.stats.special;

import java.util.HashMap;
import java.util.Map;

import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.stats.base.BoolStat;
import com.souchy.randd.commons.diamond.statics.properties.Targetability;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;
import com.souchy.randd.commons.tealwaters.ecs.Entity;

import io.netty.buffer.ByteBuf;
import static com.souchy.randd.commons.diamond.statics.properties.Targetability.*;

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
	public Targetting reset() {
		for(var t : Targetability.values()) 
			targetability.put(t, new BoolStat(false));
		
		setBase(CanBeDashedThrough, true);
		return this;
	}
	/** generic creature type, but certain creatures are more special (flyers can walk through holes, ghosts can walk through blocks...) */
	public Targetting initCreature() {
		setBase(Targetability.CanBeCastedOn, true);
		setBase(Targetability.CanCastThroughHole, true);
		setBase(Targetability.CanCastOnCreature, true);
		return this;
	}
	public Targetting initCreatureFlyer() {
		initCreature(); // base creature
		setBase(Targetability.CanWalkOnHole, true);
		setBase(Targetability.CanWalkThroughHole, true);
		setBase(Targetability.CanWalkThroughCreature, true);
		return this;
	}
	public Targetting initCreatureGhost() {
		initCreature(); // base creature
		setBase(Targetability.CanBeCastedThrough, true);
		setBase(Targetability.CanBeWalkedThrough, true);
		
		setBase(Targetability.CanWalkOnHole, true);
		setBase(Targetability.CanWalkThroughHole, true);
		setBase(Targetability.CanWalkThroughWall, true);
		setBase(Targetability.CanWalkThroughCreature, true);
		return this;
	}
	public Targetting initCellFloor() {
		setBase(Targetability.CanBeCastedOn, true);
		setBase(Targetability.CanBeCastedThrough, true);
		setBase(Targetability.CanBeWalkedOn, true);
		setBase(Targetability.CanBeWalkedThrough, true);
		return this;
	}
	public Targetting initCellHole() {
		setBase(Targetability.CanBeCastedThrough, true);
		return this;
	}
	/** renamed Block to Wall */
	public Targetting initCellWall() {
		// keep everything false
		return this;
	}

	
	/** should be used only at init step. Use setCan otherwise (for statuses and such) */
	public Targetting setBase(Targetability targetting, boolean should) {
		targetability.get(targetting).base = should;
		return this;
	}
	
	
	public boolean can(Targetability targetting) {
		return targetability.get(targetting).value();
	}
	public Targetting setCan(Targetability targetting, boolean should) {
		targetability.get(targetting).replace(should);
		return this;
	}
	
	

	/**
	 * If this entity can cast through an entity
	 */
	public boolean canCastThrough(Entity e) {
		var target = e.get(Targetting.class);
		if(target.can(Targetability.CanBeCastedThrough)) return true;
		if(e instanceof Cell) return can(Targetability.CanCastThroughWall);
		else 				  return can(Targetability.CanCastThroughCreature);
	}
	/**
	 * If this entity can target cast on an entity
	 */
	public boolean canCastOn(Entity e) {
		var target = e.get(Targetting.class);
		if(target.can(Targetability.CanBeCastedOn)) return true;
		if(e instanceof Cell) return can(Targetability.CanCastOnWall);
		else 				  return can(Targetability.CanCastOnCreature);
	}
	/**
	 * If this entity can walk through an entity (without stopping on it)
	 */
	public boolean canWalkThrough(Entity e) {
		var target = e.get(Targetting.class);
		if(target.can(Targetability.CanBeWalkedThrough)) return true;
		if(e instanceof Cell) return can(Targetability.CanWalkThroughWall);
		else 				  return can(Targetability.CanWalkThroughCreature);
	}
	/**
	 * If this entity can target walk and stop on an entity
	 */
	public boolean canWalkOn(Entity e) {
		var target = e.get(Targetting.class);
		if(target.can(Targetability.CanBeWalkedOn)) return true;
		if(e instanceof Cell) return can(Targetability.CanWalkOnWall);
		else 				  return can(Targetability.CanWalkOnCreature);
	}
	public boolean canDashThrough(Entity e) {
		var target = e.get(Targetting.class);
		if(target.can(Targetability.CanBeWalkedThrough)) return true;
		if(e instanceof Cell) return can(Targetability.CanWalkThroughWall);
		else 				  return can(Targetability.CanWalkThroughCreature);
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
	
	public Targetting copy() {
		return copyTo(new Targetting());
	}
	public Targetting copyTo(Targetting t) {
		for(var a : Targetability.values()) {
			t.setBase(a, this.can(a));
		}
		return t;
	}
	
	/**
	 * Check if 2 Targetting have the same targetabilities
	 */
	public boolean same(Targetting t) {
		for(Targetability a : Targetability.values()){
			if(t.can(a) != this.can(a)) return false;
		}
		return true;
	}
	
	
}
