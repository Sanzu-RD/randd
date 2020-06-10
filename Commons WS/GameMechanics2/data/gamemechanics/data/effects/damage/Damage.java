package gamemechanics.data.effects.damage;

import java.util.HashMap;
import java.util.Map;

import data.new1.Effect;
import data.new1.ecs.Entity;
import data.new1.spellstats.base.IntStat;
import data.new1.spellstats.imp.TargetConditionStat;
import gamemechanics.common.Aoe;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.damage.OnDmgEvent;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;
import gamemechanics.models.Fight;
import gamemechanics.statics.Element;
import gamemechanics.statics.stats.properties.Resource;

/**
 * Damage always applies to the primary resource of the targets (ex: life)
 * Then it can be modified by the target's modifier & reactor handlers to mitigate damage with another resource
 * 
 * 
 * dmg = (baseflat + casterflat) * (1 + baseinc * casterinc) * (1 + basemore * castermore)
 * 
 * 
 * Json representation of Damage effect : { _t: damage, aoe:{}, targetconditions:{}, formula:["dark":{base:60, inc:20}] }
 * Json representation of Displacement effect : { _t: pushfromcaster, aoe:{}, targetconditions:{}, value:4 } //  (vector=target-caster)
 * Json representation of Displacement effect : { _t: pushfromaoecenter, aoe:{}, targetconditions:{}, value:4 } // (vector=target-aoe) résulte en une direction différente
 * 
 * @author Blank
 * @date 19 janv. 2020
 */
public class Damage extends Effect {
	/**
	 * Base ratios
	 */
	public Map<Element, IntStat> formula = new HashMap<>();
	/**
	 * Pre-calculated source dmg (pre-mitigation)
	 */
	public Map<Element, IntStat> sourceDmg = new HashMap<>();

	/**
	 * Pre-calculated target dmg (post-mitigation)
	 */
	public Map<Element, IntStat> targetDmg = new HashMap<>();
	
	/**
	 * @param areaOfEffect - aoe
	 * @param targetConditions - what kind of targets should be affected
	 * @param formula - base ratios for elemental dmg
	 */
	public Damage(Fight fight, Aoe areaOfEffect, TargetConditionStat targetConditions, Map<Element, IntStat> formula) {
		super(fight, areaOfEffect, targetConditions);
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
		var crea = (Creature) caster;
		
		var casterAffinity = crea.stats.affinity;
		var globalAffinity = crea.stats.affinity.get(Element.global);
		
		// ajoute les stats du caster aux lignes de dégâts, puis calcule la value pour l'ajouter au totaldmg
		for(var ele : formula.keySet()) {
			var affinity = casterAffinity.get(ele);
			// copy the base dmg formula which acts as ratios 
			var f1 = formula.get(ele).copy();
			// add caster stats to the base dmg ratios
			f1.baseflat += /* globalAffinity.base + */globalAffinity.baseflat + /* affinity.base + */ affinity.baseflat;
			f1.inc 	 	*= globalAffinity.inc + affinity.inc;
			f1.incflat  += globalAffinity.incflat + affinity.incflat;
			f1.more 	*= globalAffinity.more + affinity.more;
			// save the source dmg for later
			this.sourceDmg.put(ele, f1);
		}
	}
	
	/**
	 * Calcule les dégâts post-mitigation du target
	 */
	public void prepareTarget(Creature caster, Cell cell) {
		var crea = (Creature) caster;
		var target = cell.hasCreature() ? cell.getCreatures().get(0) : null;
		if(target == null) return;
		
		var casterPen = crea.stats.penetration;
		var targetRes = target.stats.resistance;
		var globalPen = crea.stats.penetration.get(Element.global);
		var globalResistance = crea.stats.resistance.get(Element.global);
		
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
		}
		
	}
	
	/**
	 * Mitigate then apply damage to a target
	 */
	@Override
	public void apply0(Creature caster, Cell cell) {
		var target = cell.hasCreature() ? cell.getCreatures().get(0) : null;
		if(target == null) return;
		
		// Calcule le dommage total résultant du target dmg pré-calculé
		double totalDmg = 0;
		for(var ele : targetDmg.keySet()) {
			// add to total dmg
			totalDmg += targetDmg.get(ele).value();
		}
		
		// Applique les dégâts
		var life =  target.stats.resources.get(Resource.life);
		var shield =  target.stats.shield.get(Resource.life);
		
		if(shield.fight >= totalDmg) {
			shield.fight -= totalDmg;
		} else { 
			totalDmg -= shield.fight;
			shield.fight = 0;
			life.fight -= totalDmg;
		} 
		
		if(life.value() <= 0) {
			// die
			// remove target from fight
		}
	}

	@Override
	public OnDmgEvent createAssociatedEvent(Creature caster, Cell target) {
		return new OnDmgEvent(caster, target, this); 
	}

	@Override
	public Damage copy() {
		var effect = new Damage(get(Fight.class), aoe, targetConditions, formula);
		this.sourceDmg.forEach((ele, stat) -> effect.sourceDmg.put(ele, stat.copy()));
		this.targetDmg.forEach((ele, stat) -> effect.targetDmg.put(ele, stat.copy()));
		return effect;
	}

}
