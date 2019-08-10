package data.new1;

import com.google.common.collect.ImmutableList;

import gamemechanics.creatures.CreatureType;
import gamemechanics.stats.Stats;
import gamemechanics.status.Status;

public abstract class CreatureModel {

	/** 
	 * return creature id in thousands (0000 #### 0000) 
	 */
	public abstract int id();
	/** 
	 * string ID, ex: "sungjin", "grimm".
	 * this is not localized. 
	 * it is the name used primarely for identifying the resource folder of the creature.
	 */
	public abstract String getStrID();
	
	public final ImmutableList<CreatureType> colors;
	public final ImmutableList<CreatureType> types;
	public final ImmutableList<SpellModel> spells;
	public final ImmutableList<Status> statuses;
	public final Stats baseStats;
	
	public CreatureModel() {
		colors = initColors();
		types = initTypes();
		spells = initSpells();
		statuses = initStatuses();
		baseStats = initBaseStats();
	}
	
	protected abstract ImmutableList<CreatureType> initColors();
	protected abstract ImmutableList<CreatureType> initTypes();
	protected abstract ImmutableList<SpellModel> initSpells();
	protected abstract ImmutableList<Status> initStatuses();
	protected abstract Stats initBaseStats();
	
	// i18n : name, description : in res/creatures/creatureID/i18n/bundle_*.properties
	// 
	
	public String getAvatarName() {
		return "avatar.png"; //"res/creatures/" + getStrID() + "/gfx/avatar.png";
	}
	
}
