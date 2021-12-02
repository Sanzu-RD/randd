package com.souchy.randd.commons.diamond.effects.damage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.EffectType;
import com.souchy.randd.commons.diamond.effects.ValueMultiplier;
import com.souchy.randd.commons.diamond.effects.resources.ResourceGainLoss;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.models.stats.maps.ElementMap;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statusevents.damage.DamageEvent;
import com.souchy.randd.commons.diamond.statusevents.resource.ResourceGainLossEvent;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.logging.Log;

import io.netty.buffer.ByteBuf;

/**
 * Damage always applies to the primary resource of the targets (ex: life)
 * Then it can be modified by the target's modifier & reactor handlers to mitigate damage with another resource
 * 
 * 
 * dmg = ((baseflat + casterbaseflat) * (1 + baseinc * casterinc) + (baseincflat + castincflat)) * (1 + basemore * castermore)
 * 
 * en général un sort a juste baseflat, puis on peut avoir des états pour augmenter la base ou autre et les stats du perso pour inc/incflat/more en général
 * 
 * 
 * 
 * Json representation of Damage effect : { _t: damage, aoe:{}, targetconditions:{}, formula:["dark":{base:60, inc:20}] }
 * Json representation of Displacement effect : { _t: pushfromcaster, aoe:{}, targetconditions:{}, value:4 } //  (vector=target-caster)
 * Json representation of Displacement effect : { _t: pushfromaoecenter, aoe:{}, targetconditions:{}, value:4 } // (vector=target-aoe) résulte en une direction différente
 * 
 * 
 *	// exemple string : { Damage : {80 (water)}, {80 (fire)}, {per 3 ap removed} }
 * 
 * @author Blank
 * @date 19 janv. 2020
 */
public class Damage extends Effect {
	
	{
		this.modelid = EffectType.Damage.id;
	}
	
	
	/**
	 * Base ratios
	 */
	public ElementMap formula = new ElementMap();
	/**
	 * Value multipliers, usually only 1, maybe 2
	 */
	public List<ValueMultiplier> multipliers = new ArrayList<>();
	
	
	// 
	/**
	 * Step 1 Pre-calculated source dmg (pre-mitigation)
	 */
	public ElementMap sourceDmg = new ElementMap();

	/**
	 * Step 2 Pre-calculated target dmg (post-mitigation)
	 */
	public ElementMap targetDmg = new ElementMap();
	

	/**
	 * Step 3 total dmg 
	 */
	public double totalDmg = 0;
	/**
	 * Step 4 life damage
	 */
	public Map<Resource, Integer> resources = new HashMap<>();
	/**
	 * Step 4 shield damage
	 */
	public Map<Resource, Integer> shields = new HashMap<>();
	

	/**
	 * Caster source creature
	 */
	private Creature caster;
	/**
	 * Target creature, if there is one on the cell/height specified
	 */
	private Creature creatureTarget;
	
	
	/**
	 * @param areaOfEffect - aoe
	 * @param targetConditions - what kind of targets should be affected
	 * @param formula - base ratios for elemental dmg
	 */
	public Damage(Aoe areaOfEffect, TargetTypeStat targetConditions, ElementMap formula) {
		super(areaOfEffect, targetConditions);
		this.formula = formula;
	}
	
	/** propagate */
	public void pub() {
		/*
		caster.interceptors.pub(this);
		if(this.intercepted) return;
		caster.modifiers.pub(this); 
		foreach(target2) {  
			var copy = this.copy(target2); 
			target.interceptors.pub(copy);
			if(copy.intercepted) break;
			target.modifiers.pub(copy);
				copy.apply(target); //target.appliers.pub(copy);
			caster.reactors.pub(copy); 
			target.reactors.pub(copy);
		} 
		*/
	}
	
	/**
	 * Calcule les dégâts pré-mitigation du caster 
	 */
	public void prepareCaster(Creature caster, Cell aoeOrigin) {
		reset();
		this.caster = caster;
		calculateCasterDamage();
	}
	
	/**
	 * Calcule les dégâts post-mitigation du target
	 */
	public void prepareTarget(Creature caster, Cell cell) {
		creatureTarget = cell.getCreature(height); 
		if(creatureTarget == null) return;

		calculateOnRes();
		calculateTotal();
		calculateResourceLoss();
	}
	
	
	/**
	 * Mitigate then apply damage to a target
	 */
	@Override
	public void apply0(Creature caster, Cell cell) {
		creatureTarget = cell.getCreature(height); 
		if(creatureTarget == null) return;
		
		// applique les dégâts
		var gainloss = new ResourceGainLoss(AoeBuilders.single.get(), targetConditions.copy(), false, shields, resources);
		gainloss.height.set(this.height);
		gainloss.apply(caster, cell);
		
//		var gainlosse = gainloss.createAssociatedEvent(caster, cell);
//		Effect.secondaryEffect(gainlosse);
//		caster.get(Fight.class).statusbus.post(new ResourceGainLossEvent(caster, cell, this); //, composite, Resource.life, -(int) (shieldDmg + lifeDmg)));
	}

	@Override
	public DamageEvent createAssociatedEvent(Creature caster, Cell target) {
		return new DamageEvent(caster, target, this); 
	}

	@Override
	public Damage copy() {
		var effect = new Damage(aoe, targetConditions, formula);
		this.sourceDmg.forEach((ele, stat) -> effect.sourceDmg.put(ele, stat.copy()));
		this.targetDmg.forEach((ele, stat) -> effect.targetDmg.put(ele, stat.copy()));
		effect.totalDmg = this.totalDmg;
		this.resources.forEach((res, val) -> effect.resources.put(res, val));
		this.shields.forEach((res, val) -> effect.shields.put(res, val));
		return effect;
	}

	public void reset() {
		this.sourceDmg.clear();
		this.targetDmg.clear();
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		super.serialize(out);
		out.writeByte(formula.size());
		
		return out;
	}
	
	@Override
	public BBMessage deserialize(ByteBuf in) {
		super.deserialize(in);
		byte size = in.readByte();
		
		return null;
	}
	
	
	
	/**
	 * Calculate damage for each element based on the caster's affinities
	 */
	private void calculateCasterDamage() {
		var casterAffinity = caster.stats.affinity;
		var globalAffinity = caster.stats.affinity.get(Element.global);
		
		// ajoute les stats du caster aux lignes de dégâts, puis calcule la value pour l'ajouter au totaldmg
		for(var ele : formula.keySet()) {
			var affinity = casterAffinity.get(ele);
			// copy the base dmg formula which acts as ratios 
			var f1 = formula.get(ele).copy();
			// add caster stats to the base dmg ratios
			f1.baseflat += globalAffinity.baseflat + affinity.baseflat;
			f1.inc 	 	+= globalAffinity.inc + affinity.inc; // *=
			f1.incflat  += globalAffinity.incflat + affinity.incflat;
			f1.more 	+= globalAffinity.more + affinity.more; // *=
			
			// TODO ValueMultiplier will be hard to implement. Need to save values inside the spell cast instance ? 
			//for(var m : multipliers) {
			//	f1.more *= m.value;
			//}
			// save the source dmg for later
			this.sourceDmg.put(ele, f1);
			
//			Log.info("Effect Damage prepareCaster : " + ele + " = " + f1.value());
		}
	}
	
	/**
	 * Calculate damage for each element based on the target's resistances/mitigation
	 */
	private void calculateOnRes() {
		var casterPen = caster.stats.penetration;
		var targetRes = creatureTarget.stats.resistance;
		var globalPen = caster.stats.penetration.get(Element.global);
		var globalResistance = creatureTarget.stats.resistance.get(Element.global);
		
		// dmg = (baseflat + casterflat) * (1 + baseinc * casterinc) * (1 + basemore * castermore)
		
		// ajoute les stats du caster aux lignes de dégâts, puis calcule la value pour l'ajouter au totaldmg
		for(var ele : sourceDmg.keySet()) {
			// damage coming out of the caster (pre-mitigation)
			double sourceDmg = this.sourceDmg.get(ele).value(); //f1.value();
			// compile pen vs res
			var mitigation = new IntStat(sourceDmg);
			mitigation.more = - targetRes.get(ele).more - globalResistance.more + casterPen.get(ele).more + globalPen.more;
			mitigation.inc = - targetRes.get(ele).inc - globalResistance.inc + casterPen.get(ele).inc + globalPen.inc;
			mitigation.baseflat = - targetRes.get(ele).baseflat - globalResistance.baseflat + casterPen.get(ele).baseflat + globalPen.baseflat;
			// final damage taken by the target (post-mitigation)
			var finalDmg = sourceDmg * (1d + mitigation.more / 100d) * (1d + mitigation.inc / 100d) + mitigation.baseflat;
			// save the target dmg for later
			targetDmg.put(ele, new IntStat(finalDmg));

//			Log.info("Effect Damage prepareTarget : " + ele + " = " + finalDmg);
		}
	}
	
	/**
	 * Sums the elemental damage to a total
	 */
	private void calculateTotal() {
		// Calcule le dommage total résultant du target dmg pré-calculé
		for(var ele : targetDmg.keySet()) {
			// add to total dmg
			totalDmg += targetDmg.get(ele).value();
		}
//		Log.info("Effect Damage apply0 totalDmg: " + totalDmg + ", on " + creature.id + " " + creature );
	}
	
	/**
	 * Calculate the damage for each resource (life, shield..) on the target creature
	 */
	private void calculateResourceLoss() {
		// calcule les dégâts shield et life
		var shield = creatureTarget.stats.shield.get(Resource.life);
		
		double shieldDmg = 0;
		double lifeDmg = 0;
		
		if(shield.fight >= totalDmg) {
			shieldDmg = totalDmg;
		} else { 
			shieldDmg = shield.fight; 
			lifeDmg = totalDmg - shieldDmg;
		} 
		
		if(shieldDmg > 0) shields.put(Resource.life, (int) -shieldDmg);
		if(lifeDmg > 0) resources.put(Resource.life, (int) -lifeDmg);
	}
}
