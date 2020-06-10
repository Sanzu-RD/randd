package gamemechanics.models;

/**
 * Every object needs a fight reference 
 * 
 * @author Blank
 * @date 23 mai 2020
 */
public abstract class FightEntity extends data.new1.ecs.Entity {
//	public Fight fight;
	public FightEntity(Fight f) {
		super(f);
//		this.fight = f;
	}
	
	public Fight fight() {
		return get(Fight.class);
	}
}
