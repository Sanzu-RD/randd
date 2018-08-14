package com.souchy.randd.situationtest.effects.resources;

import com.souchy.randd.situationtest.events.Event;
import com.souchy.randd.situationtest.events.OnHitEvent;
import com.souchy.randd.situationtest.interfaces.ICombatEntity;
import com.souchy.randd.situationtest.matrixes.ConditionMatrix;
import com.souchy.randd.situationtest.matrixes.EffectMatrix;
import com.souchy.randd.situationtest.models.Effect;
import com.souchy.randd.situationtest.models.stage.Cell;
import com.souchy.randd.situationtest.properties.ElementValue;
import com.souchy.randd.situationtest.properties.StatProperty;
import com.souchy.randd.situationtest.properties.types.Damages;
import com.souchy.randd.situationtest.properties.types.Elements;
import com.souchy.randd.situationtest.properties.types.StatProperties;
import com.souchy.randd.situationtest.models.entities.Character;

public class DamageEffect extends Effect {

	public final Damages type;
	public final Elements ele;
	public final ElementValue scl;
	public final ElementValue flat;
	
	public DamageEffect(Character source, Damages type, Elements ele, ElementValue scl, ElementValue flat, EffectMatrix em, ConditionMatrix cm) {
		super(source, em, cm);
		this.type = type;
		this.ele = ele;
		this.scl = scl;
		this.flat = flat;
	}
	
	public DamageEffect(Character source, Damages type, Elements ele, ElementValue scl, ElementValue flat, EffectMatrix em, ConditionMatrix cm, String descriptionOverride) {
		this(source, type, ele, scl, flat, em, cm);
		
	}

	
	/**
	 * 
	 */
	public int[][] previsualisation(Cell hoverCell) {
		int[][] valueMatrix = {{}};
		return valueMatrix;
	}
	public void previsu(Character source, Character target) {
		
		// Calcule les d�g�ts de base selon les affinit�s et les res
		DmgInstance inst = calculate(source, target);
	    		
		// Post l'instance pour appliquer les statuts modificateurs du style invuln�rabilit�, +dmg finaux, etc
		source.post(inst);
		target.post(inst);
	}

	@Override
	protected void apply(Cell cell) {
		// TODO Auto-generated method stub
		ICombatEntity e = cell.getCombatEntity(context);
		apply(e);
	}
	public void apply(Character target) {
		
		// Calcule les d�g�ts de base selon les affinit�s et les res
		DmgInstance inst = calculate(source, target);
	    		
		// Post l'instance pour appliquer les statuts modificateurs du style invuln�rabilit�, +dmg finaux, etc
		source.post(inst);
		target.post(inst);

		// Proc "onDmg" event pour quand on frappe la vie
		if(inst.shieldDmg.value > 0) {
			source.post(new ShieldDmgEventEvent(inst)); // ex peut avoir un statut qui donne du vol de vie quand il fait du dmg
			target.post(new ShieldLossEvent(inst)); 	// ex peut avoir un statut qui donne des stats selon les hp qu'il perd
		}
		// Proc "onDmg" event pour quand on frappe la vie
		if(inst.hpDmg.value > 0) {
			source.post(new HpDmgEventEvent(inst)); // ex peut avoir un statut qui donne du vol de vie quand il fait du dmg
			target.post(new HpLossEvent(inst)); 	// ex peut avoir un statut qui donne des stats selon les hp qu'il perd
		}

		// applique la dmg instance
		//applyDamage(inst);
		
		//target.post(new StatCheckEvent());
		
		OnHitEvent onhit = new OnHitEvent(source, target);
		// proc les onHit/onDot ..
		// proc les onhit du target en premier vu qu'on a d�j� fait le hit du sort lui-m�me
		target.post(onHitRcv);
		// puis proc les onhit de la source ensuite pour que ceux-cis puissent proc les onhits du target une deuxi�me fois (s'il y a des dmg onhit)
		source.post(onHitSomeone);
	    
	    // post(OnHit());
	    // post(OnDot());
	    // post(OnCounter());
	    
	    /*
	     * Dans le spell :
	     * 
	     * DamageEffect effect1;
	     *
	     * context.post()
		 * 	  source.receiver -> if(event.source == this) dmgEffect.dmg *= 1,5;
		 * 	  target.receiver -> if(event.target == this) dmgEffect.dmg *= 0,7;
		 *    context.receiver -> target.hp -= dmgEffect.dmg;
	     * post(OnHit/OnDot etc)
	     *     source.receiver -> post(effect)
	     * 
	     * 
	     * effect1.apply();
	     * 
	     * handler pipeline : 
	     * receive -> dmg = 0
	     * receive -> play.hp -= dmg;
	     * 
	     */
	}
	/*private void proc() {
		if(type == Damages.Hit) {
		      target.post(new OnHitEvent(source, target));
		}
		if(type == Damages.Dot) {
			target.post(new OnDotEvent(source, target));
		}
	}*/
	
	private DmgInstance calculate(Character source, Character target) {
		
	    
		return new DmgInstance(this, source, target); //type, shieldLoss, hpLoss);
	}
	
	private void applyDamage(DmgInstance inst) {
		
	}

	public static class DmgInstance extends Event {
		//public Character source; // d�j� d�lar� dans Event, mais il nous faut en tant que character
		/**
		 * Possible d'avoir un modificateur qui redirige les d�g�ts vers une autre personne (ex sacrifice du sacrieur)
		 */
		public Character target;
		
		// Peut avoir des status modificateurs sur toutes ces propri�t�s l�
		// ex col�re iop augmente les d�g�ts de base s'il a le statut coco � 1 tour restant
		public final Damages type;
		public final Elements ele;
		public final ElementValue scl;
		public final ElementValue flat;
		/** Normalement on tape la resource1 (HP), mais c'est possible d'avoir un modificateur qui redirige les d�g�ts vers une autre resource */
		public StatProperties resourceDmg = StatProperties.Resource1;
		
		
		/** R�sultats de d�g�ts pr�calcul�s sur le shield et l'hp */
		public ElementValue shieldDmg, hpDmg;
		//public ElementValue dmg;
		
		public DmgInstance(DamageEffect e, Character source, Character target) { //, Damages type, ElementValue shieldDmg, ElementValue hpDmg) { //ElementValue dmg) {
			super(source);
			//this.source = source;
			this.target = target;
			/*this.type = type;
			//this.dmg = dmg;
			this.shieldDmg = shieldDmg;
			this.hpDmg = hpDmg;*/
			type = e.type;
			ele = e.ele;
			scl = e.scl.copy();
			flat = e.flat.copy();
			update();
		}
		public Character source() {
			return (Character) super.source;
		}
		/** recalculate the resulting damage */
		public void update() {
		    ElementValue dmg = new ElementValue(ele, 0).
		    // scl dmg
		    addSet(scl.value * source().stats.scl(scl.element).value).
		    // flat dmg
		    addSet(flat.value + source().stats.flat(flat.element).value).
		    // res fix
		    subSet(target.stats.resFlat(ele).value).
		    // res scl
		    multSet(1 - target.stats.resScl(ele).value / 100);
		    
		    // pour pas se soigner avec des d�g�ts n�gatifs
		    if(dmg.value < 0)
				dmg.value = 0;

			StatProperty hp = source().stats.get(StatProperties.Resource1);
		    StatProperty shield = source().stats.get(StatProperties.Resource1Shield);
		    ElementValue shieldLoss = new ElementValue(ele, 0);
		    ElementValue hpLoss = new ElementValue(ele, 0);
		    
		    // ces types 
		    if(type == Damages.Hit || type == Damages.Counter) {
		    	shieldLoss.value = Math.min(shield.value, dmg.value);
		    	hpLoss.value = dmg.value - shieldLoss.value;
		    	
		    	if(type == Damages.Counter) {
			    	// less chance to counter a counter
		    	} else {
			    	// calculate chance to counter where ??? dans le receiver de OnHit ??
		    	}
		    	
		    	// source.post(onDmg/onHitSomeone(source, target, shieldLoss, hpLoss)); ?????
		    		// pour que la source puisse proc d'autres onHit
		    		// peut ajouter des effets de splash/multihit � cet endroit
		    	// target.post(onDmg/onHitReceive(source, target, shieldLoss, hpLoss)); ?????
		    		// pour que le target puisse proc ses OnHitReceive
		    }
		    // Ces types se foutent des shields
		    if(type == Damages.Dot || type == Damages.PenetrationHit) {
		    	hpLoss.value = dmg.value;
		    }
		}
	}


}
