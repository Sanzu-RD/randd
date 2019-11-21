package gamemechanics.statics;

import java.util.ArrayList;
import java.util.List;

import data.new1.Tag;

import data.new1.timed.Status.Passive;
import gamemechanics.statics.stats.Stats;

public interface CreatureType extends Tag {
	
	public static List<CreatureType> types = new ArrayList<>();
	
	
	public Stats getStats();
	public Passive getPassive();
	
}
