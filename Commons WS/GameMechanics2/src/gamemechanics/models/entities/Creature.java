package gamemechanics.models.entities;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.jade.meta.JadeCreature;

import data.new1.spellstats.CreatureStats;
import gamemechanics.common.generic.Vector2;
import gamemechanics.components.Position;
import gamemechanics.main.DiamondModels;
import gamemechanics.models.CreatureModel;
import gamemechanics.models.Fight;
import gamemechanics.models.SpellModel;
import gamemechanics.statics.Element;
import io.netty.buffer.ByteBuf;

public class Creature extends Entity {

	/**
	 * Creature basic data : spell data it can learn, creature types, creature colors, base stats, base properties<br>
	 * 
	 * The Ebi version also contains : textures, i18n, default model file
	 */
	public CreatureModel model;
	
	/**
	 * compiled stats (includes model, jade and statuses stats)
	 */
	private CreatureStats stats; 
	/**
	 * spell book containing spells //~~spell groups containing spell~~
	 */
	public List<SpellModel> spellbook;
	
	/**
	 * for deserializing
	 */
	public Creature(Fight f) {
		super(f);
	}

	/**
	 * for initialisation
	 */
	public Creature(Fight f, CreatureModel model, JadeCreature jade, Position pos) { // AzurCache dep, Vector2 pos) {
		super(f);
		this.model = model;
		this.spellbook = new ArrayList<>();
		this.pos = pos;

		// copy model stats into instance stats
		this.stats = model.baseStats.copy(); 
		// then add jade stats
		for(int i = 0; i < Element.count(); i++) {
			this.stats.affinity.get(Element.values.get(i)).inc += jade.affinities[i];
		}
		
		// chosen spells
		for(int i : jade.spellIDs) {
			var s = DiamondModels.spells.get(i);
			if(s != null) spellbook.add(s);
//			var s = dep.spells.get(i);
//			if(s != null) spellbook.add(s);
		}

	}
	
	/**
	 * Same as getTempStats() except this one compiles them before returning
	 */
	@Override
	public CreatureStats getStats() {
		return stats;
	}

	@Override
	public ByteBuf serialize(ByteBuf out) {
		super.serialize(out);
		out.writeInt(model.id());
		stats.serialize(out);
		return null;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		super.deserialize(in);
		int modelid = in.readInt();
		this.model = DiamondModels.creatures.get(modelid);
		stats.deserialize(in);
		return null;
	}
	
}