package gamemechanics.models;

import data.new1.spellstats.CreatureStats;

public abstract class CreatureModel extends data.new1.ecs.Entity {

	/** 
	 * return creature id in thousands (0000 #### 0000) 
	 */
	public abstract int id();
	/** 
	 * string ID, ex: "sungjin", "grimm".
	 * this is not localized. 
	 * it is the name used primarely for identifying the resource folder of the creature.
	 */
	//public abstract String getStrID();
	
//	public final ImmutableList<Element> elements;
//	public final ImmutableList<CreatureType> types;
//	public final ImmutableList<SpellModel> spells;
//	public final ImmutableList<Status> statuses;
	public final CreatureStats /*Stats*/ baseStats;
	
	public CreatureModel() {
		super();
//		elements = initAffinities();
//		types = initTypes();
		//initTags();
//		spells = initSpells();
//		statuses = initStatuses();
		baseStats = initBaseStats();
	}
	
//	protected abstract ImmutableList<Element> initAffinities();
//	protected abstract ImmutableList<CreatureType> initTypes();
	/**
	 * 
	 */
	//protected abstract List<Tag> initTags();
//	protected abstract ImmutableList<SpellModel> initSpells();
//	protected abstract ImmutableList<Status> initStatuses();
	protected abstract CreatureStats initBaseStats();
	
	// i18n : name, description : in res/creatures/creatureID/i18n/bundle_*.properties
	// 
	
	public String getIconName() {
		return Integer.toString(id());
		//return "avatar"; //"res/creatures/" + getStrID() + "/gfx/avatar.png";
	}
	
}
