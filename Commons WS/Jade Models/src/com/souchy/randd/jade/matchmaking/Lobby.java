package com.souchy.randd.jade.matchmaking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.jade.meta.JadeCreature;
import com.souchy.randd.jade.meta.User;

import io.netty.buffer.ByteBuf;
import io.netty.util.AttributeKey;

/**
 * Information about a lobby's players and the game server hosting them
 * 
 * @author Blank
 * @date 24 déc. 2019
 */
public class Lobby implements BBSerializer, BBDeserializer {

	public static final AttributeKey<Lobby> attrkey = AttributeKey.newInstance("jade.matchmaking.lobby");
	
	public enum LobbyPhase {
		ban,
		pick,
		customize;
	}
	
	/** draft / blind / mock */
	public GameQueue type; 
	
	/** user IDs in the lobby */
//	public List<ObjectId> users = new ArrayList<>();

	/** team for each players */
	public Map<ObjectId, Team> teams = new HashMap<>(); // instead of using Team enum, use 0-1 for A and B team
	
	/** jadeteam for each players */
	public Map<ObjectId, List<JadeCreature>> jadeteams = new HashMap<>();
	
	/** queue answer for each player */
//	public Map<ObjectId, QueueAnswer> answers = new HashMap<>();
	
	/** game server info */
	public String moonstoneInfo;
	
	/**
	 * ban, pick, customisation
	 */
	public LobbyPhase phase;
	/**
	 * 
	 */
	public ObjectId playerTurn;
	/**
	 * 
	 */
	public long time;
	
	public static final String name_type = "type";
	public static final String name_users = "users";
	public static final String name_teams = "teams";
	public static final String name_jadeteams = "jadeteams";
//	public static final String name_answers = "answers";
	public static final String name_moonstoneInfo = "moonstoneInfo";
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		// type and phase
		try {
			out.writeByte(type.ordinal());
			out.writeInt(phase.ordinal());
			// player count
			out.writeByte(teams.size());
			for (var id : teams.keySet()) {
				// player id
				writeString(out, id.toHexString());
				out.writeInt(teams.get(id).ordinal());
				// creatures
				var team = jadeteams.get(id);
				out.writeByte(team.size());
				for (var creature : team)
					creature.serialize(out);
				// answer
				// out.writeByte(answers.get(id).ordinal());
			}
		} catch (Exception e) {
			Log.info("", e);
		}
		return out;
	}
	
	@Override
	public BBMessage deserialize(ByteBuf in) {
		
		// type and phase
		type = GameQueue.values()[in.readByte()];
		phase = LobbyPhase.values()[in.readByte()];
		
		// player count
		byte playercount = in.readByte();
		for(int i = 0; i < playercount; i++) {
			// player id
			var id = new ObjectId(readString(in));
//			users.add(id);
			// player team
			int teamordinal = in.readInt();
			teams.put(id, Team.values()[teamordinal]);
			// creatures
			byte creaturecount = in.readByte();
			var creatures = new ArrayList<JadeCreature>(); //new JadeCreature[creaturecount];
			for(int j = 0; j < creaturecount; j++)
				creatures.add(new JadeCreature().deserialize(in));
//				creatures[j] = new JadeCreature().deserialize(in);
			jadeteams.put(id, creatures);
			// answer
//			answers.put(id, QueueAnswer.values()[in.readByte()]);
		}
		return null;
	}
	
}