package gamemechanics.models;

/**
 * Every object needs a fight reference 
 * 
 * @author Blank
 * @date 23 mai 2020
 */
public abstract class FightObject extends data.new1.ecs.Entity {
	public Fight fight;
	public FightObject(Fight f) {
		super();
		this.fight = f;
	}
}
