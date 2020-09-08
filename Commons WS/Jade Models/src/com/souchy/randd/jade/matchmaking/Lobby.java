package com.souchy.randd.jade.matchmaking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;

import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;
import com.souchy.randd.commons.tealwaters.commons.ActionPipeline;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.jade.meta.JadeCreature;
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
	
//	public enum LobbyPhase {
//		ban,
//		pick,
//		customize;
//	}
	
	/** game server info */
	public String moonstoneInfo;
	
	/** draft / blind / mock */
	public GameQueue type; 
	
	/**
	 * ban, pick, customisation
	 */
//	public LobbyPhase phase;
	
	/** user IDs in the lobby */
	public List<ObjectId> users = new ArrayList<>();
	
	/** team for each players, indexed by users */
	public List<Team> teams = new ArrayList<>();
	
	/** jadeteam for each players, indexed by users */
	public List<List<JadeCreature>> jadeteams = new ArrayList<>();
	
	/**
	 * Current turn starting from 0 (+1 for each ban and pick so 3 bans * 2 + 4 picks * 2 = 14 turns total)
	 */
	public int turn;
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
	
	/**
	 * get the team for a player
	 */
	public Team team(ObjectId player) {
		return teams.get(users.indexOf(player));
	}
	/**
	 * get the creatures for a player
	 */
	public List<JadeCreature> creatures(ObjectId player) {
		return jadeteams.get(users.indexOf(player));
	}
	
	
	private ActionPipeline pipe = new ActionPipeline();
	/**
	 * Action pipeline for managing turn actions
	 */
	public ActionPipeline pipe() {
		return pipe;
	}
	
	/**
	 * Get the current player turn in a synchronized way
	 */
	public synchronized int playerTurn() {
		return turn;
	}
	
	/** get player index (not id) in the user list from turn index */
	public int getPlayerIndex(int turn) {
		switch(users.size()) {
			// 1 player = mock fight
			case 1 : return 0;
			// 1v1 = 2 players
			case 2 : { 
				return turn % 2;
			}
			// 2v2 = 4 players
			case 4 : {
				if(turn < 2) return turn % 2;
				else return (turn - 2) % 4;
			}
			// 4v4 = 8 players
			case 8 : {
				if(turn < 2) return turn % 2;
				else return (turn - 2) % 8;
			}
		}
		return -1;
	}
	
	/** get player id from turn index */
	public ObjectId getPlayer(int turn) {
		int index = getPlayerIndex(turn);
		if(index < 0 || index >= users.size()) return null;
		return users.get(index);
	}
	
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		// type and phase
		try {
			out.writeByte(type.ordinal());
//			out.writeInt(phase.ordinal());
			
			// player count
			out.writeByte(users.size());
			// for each player write : id, team, creatures
			for (int i = 0; i < users.size(); i++) {
				var user = users.get(i);
				// player id
				writeString(out, user.toString());
				// team 
				out.writeInt(teams.get(i).ordinal());
				// creatures
				var team = jadeteams.get(i);
				out.writeByte(team.size());
				for (var creature : team)
					creature.serialize(out);
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
//		phase = LobbyPhase.values()[in.readByte()];
		
		// player count
		byte playercount = in.readByte();
		// for each player read : id, team, creatures
		for(int i = 0; i < playercount; i++) {
			// player id
			var id = new ObjectId(readString(in));
			users.add(id);
			// player team
			int teamordinal = in.readInt();
			teams.add(Team.values()[teamordinal]); //.put(id, Team.values()[teamordinal]);
			// creatures
			byte creaturecount = in.readByte();
			var creatures = new ArrayList<JadeCreature>(); //new JadeCreature[creaturecount];
			for(int j = 0; j < creaturecount; j++)
				creatures.add(new JadeCreature().deserialize(in));
			jadeteams.add(creatures); // .put(id, creatures);
		}
		return null;
	}
	
}