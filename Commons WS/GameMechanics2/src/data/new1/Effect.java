package data.new1;


import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/**
 * 
 * Parents all effects like DamageEffect, PushEffect, etc
 * 
 * create a new instance of them for each spell ex : effects.add(new PushEffect(aoe, conds))
 * 
 * @author Blank
 *
 */
public abstract class Effect {

	//public JadeEffect jade;

	//public abstract int id();
	public int[] areaOfEffect;
	public int targetConditions;
	
	public Effect(int[] areaOfEffect, int targetConditions) {
		this.areaOfEffect = areaOfEffect;
		this.targetConditions = targetConditions;
	}
	
	public abstract void apply(Entity caster, Cell target); //, Effect parent);
	
//	public abstract class SingleTargetEffect extends Effect {
//		public SingleTargetEffect() {
//			super(new int[1], 0);
//		}
//	}
	
}
