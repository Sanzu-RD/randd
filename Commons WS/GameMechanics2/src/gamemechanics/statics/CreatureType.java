package gamemechanics.statics;

import java.util.ArrayList;
import java.util.List;

import data.new1.Tag;
import data.new1.spellstats.CreatureStats;
import data.new1.timed.Status.Passive;

public interface CreatureType extends Tag {
	
	public static List<CreatureType> types = new ArrayList<>();
	
	
	public CreatureStats getStats();
	public Passive getPassive();
	
}
