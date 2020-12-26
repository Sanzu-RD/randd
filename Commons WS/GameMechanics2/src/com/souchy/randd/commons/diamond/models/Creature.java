package com.souchy.randd.commons.diamond.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.models.stats.CreatureStats;
import com.souchy.randd.commons.diamond.models.stats.Targetting;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;
import com.souchy.randd.commons.tealwaters.ecs.Entity;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.jade.matchmaking.Team;
import com.souchy.randd.jade.meta.JadeCreature;

import io.netty.buffer.ByteBuf;

public class Creature extends Entity implements BBSerializer, BBDeserializer {

	private static int idCounter = 0;

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
	
	// parent - summoner id if this creature is a summon
	public int summonerID = 0;
	// children - summons ids
	public List<Integer> summonsID = new ArrayList<>();
	
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
		this.id = idCounter++;
		this.modelid = model.id();
		this.pos = pos;
		this.targeting = new Targetting();
		this.targeting.initCreature();
		
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
		Log.info("Creature serialize spells : " + String.join(",", spellbook.stream().map(i -> String.valueOf(i)).collect(Collectors.toList())) );

		// status 
		this.statuses.serialize(out);
		
		// targeting
		this.targeting.serialize(out);
		
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
//		Log.info("Creature deserialize spells : " + String.join(",", spellbook.stream().map(i -> String.valueOf(i)).collect(Collectors.toList())) );
		
		// status
		this.statuses = new StatusList(null);
		this.statuses.deserialize(in);

		// targeting
		this.targeting = new Targetting();
		this.targeting.deserialize(in);
		
		return null;
	}
	
}