package gamemechanics.models;

/**
 * Every object needs a fight reference 
 * 
 * @author Blank
 * @date 23 mai 2020
 */
public abstract class FightObject {
	public Fight fight;
	public FightObject(Fight f) {
		this.fight = f;
	}
}
