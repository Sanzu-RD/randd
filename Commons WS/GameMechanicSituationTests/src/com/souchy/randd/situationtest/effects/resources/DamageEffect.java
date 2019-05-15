package com.souchy.randd.situationtest.effects.resources;

import com.souchy.randd.jade.api.ICell;
import com.souchy.randd.situationtest.events.Event;
import com.souchy.randd.situationtest.events.OnHitEvent;
import com.souchy.randd.situationtest.events.statschange.StatChangeEvent;
import com.souchy.randd.situationtest.matrixes.ConditionMatrix;
import com.souchy.randd.situationtest.matrixes.EffectMatrix;
import com.souchy.randd.situationtest.models.Effect;
import com.souchy.randd.situationtest.models.entities.Character;
import com.souchy.randd.situationtest.models.map.Cell;
import com.souchy.randd.situationtest.models.org.FightContext;
import com.souchy.randd.situationtest.properties.ElementValue;
import com.souchy.randd.situationtest.properties.StatProperty;
import com.souchy.randd.situationtest.properties.types.Damages;
import com.souchy.randd.situationtest.properties.types.Elements;
import com.souchy.randd.situationtest.properties.types.StatProperties;

public class DamageEffect extends Effect {

	public final Damages type;
	public final Elements ele;
	public final ElementValue scl;
	public final ElementValue flat;
	
	public DamageEffect(FightContext c, Character source, Damages type, Elements ele, ElementValue scl, ElementValue flat, EffectMatrix em, ConditionMatrix cm) {
		super(c, source, em, cm);
		this.type = type;
		this.ele = ele;
		this.scl = scl;
		this.flat = flat;
	}
	
	public DamageEffect(FightContext c, Character source, Damages type, Elements ele, ElementValue scl, ElementValue flat, EffectMatrix em, ConditionMatrix cm, String descriptionOverride) {
		this(c, source, type, ele, scl, flat, em, cm);
		
	}

	
	/**
	 * 
	 */
	public int[][] previsualisation(Cell hoverCell) {
		int[][] valueMatrix = {{}};
		return valueMatrix;
	}
	public void previsu(Character source, Character target) {
		
		// Calcule les dégâts de base selon les affinités et les res
		DmgInstanceEvent inst = calculate(source, target);
	    		
		// Post l'instance pour appliquer les statuts modificateurs du style invulnérabilité, +dmg finaux, etc
		source.post(inst);
		target.post(inst);
	}

	@Override
	protected void apply(ICell cell) {
		// TODO Auto-generated method stub
		//ICombatEntity e = cell.getCombatEntity(context);
		Character c = cell.getCharacter();
		apply(c);
	}
	public void apply(Character target) {
		
		// Calcule les dégâts de base selon les affinit�s et les res
		DmgInstanceEvent inst = calculate(source, target);
	    		
		// Post l'instance pour appliquer les statuts modificateurs du style invuln�rabilit�, +dmg finaux, etc
		source.post(inst);
		target.post(inst);

		// Proc "onDmg" event pour quand on frappe la vie
		if(inst.shieldL.value > 0) {
			//source.post(new ShieldDmgEventEvent(inst)); // ex peut avoir un statut qui donne du vol de vie quand il fait du dmg
			//target.post(new ShieldLossEvent(inst)); 	// ex peut avoir un statut qui donne des stats selon les hp qu'il perd

			Event e = new StatChangeEvent(source, target, inst.shieldL);
			target.post(e); //new RecvStatChangeEvent(source, inst.shieldL));
			if(source != target) source.post(e); //new InflictStatChangeEvent(source, inst.shieldL));
		}
		// Proc "onDmg" event pour quand on frappe la vie
		if(inst.hpL.value > 0) {
			//source.post(new HpDmgEventEvent(inst)); // ex peut avoir un statut qui donne du vol de vie quand il fait du dmg
			//target.post(new HpLossEvent(inst)); 	// ex peut avoir un statut qui donne des stats selon les hp qu'il perd
			
			Event e = new StatChangeEvent(source, target, inst.hpL);
			target.post(e); //new RecvStatChangeEvent(source, inst.hpL));
			if(source != target) source.post(e); //new InflictStatChangeEvent(source, inst.hpL));
		}

		// applique la dmg instance
		//applyDamage(inst);
		
		//target.post(new StatCheckEvent());
		
		OnHitEvent onhit = new OnHitEvent(source, target);
		// proc les onHit/onDot ..
		// proc les onhit du target en premier vu qu'on a d�j� fait le hit du sort lui-m�me
		target.post(onhit);
		// puis proc les onhit de la source ensuite pour que ceux-cis puissent proc les onhits du target une deuxi�me fois (s'il y a des dmg onhit)
		// post l'event juste une fois si la source == le target
		if(source != target) source.post(onhit);
	    
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
	
	private DmgInstanceEvent calculate(Character source, Character target) {
		
	    
		return new DmgInstanceEvent(this, source, target); //type, shieldLoss, hpLoss);
	}
	
	private void applyDamage(DmgInstanceEvent inst) {
		
	}

	public static class DmgInstanceEvent extends Event {
		//public Character source; // déjà d�lar� dans Event, mais il nous faut en tant que character
		/**
		 * Possible d'avoir un modificateur qui redirige les dégâts vers une autre personne (ex sacrifice du sacrieur)
		 */
		public Character target;
		
		// Peut avoir des status modificateurs sur toutes ces propriétés là
		// ex colère iop augmente les dégâts de base s'il a le statut coco à 1 tour restant
		public final Damages type;
		public final Elements ele;
		public final ElementValue scl;
		public final ElementValue flat;
		/** Normalement on tape la resource1 (HP), mais c'est possible d'avoir un modificateur qui redirige les dégâts vers une autre resource */
		//public StatProperties resourceHit = StatProperties.Resource1;

	    StatProperty hpL = new StatProperty(StatProperties.Resource1, 0);
	    StatProperty shieldL = new StatProperty(StatProperties.Resource1Shield, 0);
		
		/** Résultats de dégâts précalculés sur le shield et l'hp */
		//public ElementValue shieldDmg, hpDmg;
		//public ElementValue dmg;
		
		public DmgInstanceEvent(DamageEffect e, Character source, Character target) { //, Damages type, ElementValue shieldDmg, ElementValue hpDmg) { //ElementValue dmg) {
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
		    /*ElementValue dmg = new ElementValue(ele, 0).
		    // scl dmg
		    addSet(scl.value * source().stats.scl(scl.element).value).
		    // flat dmg
		    addSet(flat.value + source().stats.flat(flat.element).value).
		    // res fix
		    subSet(target.stats.resFlat(ele).value).
		    // res scl
		    multSet(1 - target.stats.resScl(ele).value / 100);*/
		    
		    int dmg = (scl.value * source().stats.scl(scl.element).value
		    		+ flat.value + source().stats.flat(flat.element).value
		    		- target.stats.resFlat(ele).value)
		    		* (1 - target.stats.resScl(ele).value / 100);
		    
		    // pour pas soigner avec des dégâts négatifs
		    if(dmg < 0)
				dmg = 0;

			//StatProperty hp = source().stats.get(StatProperties.Resource1);
		    StatProperty shield = source().stats.get(StatProperties.Resource1Shield);
		    //ElementValue shieldLoss = new ElementValue(ele, 0);
		    //ElementValue hpLoss = new ElementValue(ele, 0);

		    //StatProperty hpL = new StatProperty(StatProperties.Resource1, 0);
		    //StatProperty shieldL = new StatProperty(StatProperties.Resource1Shield, 0);
		    
		    // ces types 
		    if(type == Damages.Hit || type == Damages.Counter) {
		    	shieldL.value = Math.min(shield.value, dmg);
		    	hpL.value = dmg - shieldL.value;
		    	
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
		    	hpL.value = dmg;
		    }
		}
	}


}
