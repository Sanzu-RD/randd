package gamemechanics.models;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;
import com.souchy.randd.jade.meta.JadeCreature;

import data.new1.ecs.Entity;
import data.new1.spellstats.CreatureStats;
import data.new1.spellstats.Targetting;
import data.new1.timed.StatusList;
import gamemechanics.components.Position;
import gamemechanics.main.DiamondModels;
import gamemechanics.statics.Element;
import io.netty.buffer.ByteBuf;

public class Creature extends Entity implements BBSerializer, BBDeserializer {

	public static enum Team {
		A,
		B,
		/** C is Neutral */
//		C,
	}

	/** entity id for identification and mostly retrival during deserialization */
	public int id;
	
	/** creaturemodel's id */
	public int modelid;
	
	/** board position */
	public Position pos;

	/** Team appartenance */
	public Team team;
	
	/** compiled stats (includes model, jade and statuses stats) */
	public CreatureStats stats; 
	
	/** spells instances ids */
	public List<Integer> spellbook;

	/** Statuses */
	public StatusList statuses;
	
	/** Properties like pathing,  line of sights, ~~visibility~~, orientation */
	public Targetting targeting;
	
	/**
	 * for deserializing
	 */
	public Creature(Fight f) {
		super(f);
	}

	/**
	 * for initialisation
	 */
	public Creature(Fight fight, CreatureModel model, JadeCreature jade, Position pos) { // AzurCache dep, Vector2 pos) {
		super(fight);
		this.modelid = model.id();
		this.pos = pos;
		this.targeting = new Targetting();
		this.spellbook = new ArrayList<>();
		this.statuses = new StatusList(fight);

		// copy model stats into instance stats
		this.stats = model.baseStats.copy(); 
		// then add jade stats
		for(int i = 0; i < Element.count(); i++) {
			this.stats.affinity.get(Element.values.get(i)).inc += jade.affinities[i];
		}
		
		// copy chosen spells
		for(int i : jade.spellIDs) {
			var s = DiamondModels.spells.get(i);
			if(s != null) 
				spellbook.add(s.copy(fight).id); //new SpellInstance(f, s)); // TODO create spell instances
//			var s = dep.spells.get(i);
//			if(s != null) spellbook.add(s);
		}

	}
	
	public CreatureModel getModel() {
		return DiamondModels.creatures.get(modelid);
	}

	@Override
	public <T> T get(Class<T> c) {
		if(c == Position.class)
			return (T) this.pos;
		return super.get(c);
	}

	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(id);
		out.writeInt(modelid); //model.id());
		out.writeDouble(this.pos.x);
		out.writeDouble(this.pos.y);
		out.writeInt(this.team.ordinal());
		
		// stats
		this.stats.serialize(out);
		
		// spells
		out.writeInt(spellbook.size());
		spellbook.forEach(s -> out.writeInt(s));
//		Log.info("write spellbook " + spellbook.size());

		// status 
		this.statuses.serialize(out);
		
		// TODO serialize targeting...
		return null;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		this.id = in.readInt();
		this.modelid = in.readInt();
		this.pos = new Position(in.readDouble(), in.readDouble());
		this.team = Team.values()[in.readInt()];
		
		// stats
		this.stats = new CreatureStats();
		this.stats.deserialize(in);

		// spells
		this.spellbook = new ArrayList<>();
		int spellcount = in.readInt();
		for(int i = 0; i < spellcount; i++) {
			int spellid = in.readInt();
			spellbook.add(spellid);
		}
//		Log.info("read spellbook " + spellbook.size());
		
		// status
		this.statuses = new StatusList(null);
		this.statuses.deserialize(in);
		
		
		// TODO deserialize targeting...
		return null;
	}
	
}