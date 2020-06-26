package com.souchy.randd.deathshadows.iolite.emerald;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.souchy.randd.commons.tealwaters.commons.Namespace.MongoNamespace;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.jade.matchmaking.Lobby;
import com.souchy.randd.jade.matchmaking.QueueeBlind;
import com.souchy.randd.jade.matchmaking.QueueeDraft;
import com.souchy.randd.jade.meta.Deck;
import com.souchy.randd.jade.meta.Match;
import com.souchy.randd.jade.meta.New;
import com.souchy.randd.jade.meta.User;

/**
 * MongoDB access
 *
 * @author Blank
 *
 */
public final class Emerald {

//	private static String ip = "localhost";
//	private static int port = 27017;
//	private static String user = "";
//	private static String pass = "";
	//private
	static MongoClient client;

	private static final String root = "hidden_piranha";
	private static final MongoNamespace logs = new MongoNamespace(root, "logs");
	private static final MongoNamespace users = new MongoNamespace(root, "users");
	private static final MongoNamespace decks = new MongoNamespace(root, "decks");
	private static final MongoNamespace matches = new MongoNamespace(root, "matches");
	private static final MongoNamespace news = new MongoNamespace(root, "news");
	private static final MongoNamespace queue_simple_blind = new MongoNamespace(root, "queue_simple_blind"); // blind is premade teams
	private static final MongoNamespace queue_simple_draft = new MongoNamespace(root, "queue_simple_draft");
	private static final MongoNamespace lobbies = new MongoNamespace(root, "lobbies");

	static {
		init("localhost", 27017, "", "");
	}

	/**
	 * Allows re-initializing Emerald with different parameters
	 * @param ip - Default is "localhost"
	 * @param port - Default is 27017
	 * @param user - Default is ""
	 * @param pass - Default is ""
	 */
	public static void init(String ip, int port, String user, String pass) {
		if(client != null) client.close();
		var credentials = "";
		if(user != "") credentials = user + ":" + pass + "@";
		var registry = CodecRegistries.fromRegistries(
				MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromCodecs(new ZonedDateTimeCodec()),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
		);
		var settings = MongoClientSettings.builder()
			//.credential(MongoCredential.createCredential("robyn", "admin", new char[] { 'z' }))
			.applyConnectionString(new ConnectionString("mongodb://" + credentials + ip + ":" + port))
			.codecRegistry(registry)
		.build();
		client = MongoClients.create(settings);
	}

	public static class ZonedDateTimeCodec implements Codec<ZonedDateTime> {
		@Override
		public void encode(BsonWriter writer, ZonedDateTime value, EncoderContext encoderContext) {
			writer.writeDateTime(value.toInstant().toEpochMilli());
		}
		@Override
		public Class<ZonedDateTime> getEncoderClass() {
			return ZonedDateTime.class;
		}
		@Override
		public ZonedDateTime decode(BsonReader reader, DecoderContext decoderContext) {
			return ZonedDateTime.ofInstant(Instant.ofEpochMilli(reader.readDateTime()), ZoneId.systemDefault());
		}
	}


	private static <T> MongoCollection<T> get(MongoNamespace space, Class<T> clazz) {
		return client.getDatabase(space.db).<T>getCollection(space.collection, clazz);
	}


	// -------------------------------------------------------------------  meta

	public static MongoCollection<Log> logs() {
		return collection(Log.class); // get(logs, Log.class);
	}
	public static MongoCollection<User> users() {
		return collection(User.class); // get(users, User.class);
	}

	public static MongoCollection<Deck> decks() {
		return collection(Deck.class); // get(decks, Deck.class);
	}

	public static MongoCollection<Match> matchs() {
		return collection(Match.class); // get(matches, Match.class);
	}
	public static MongoCollection<New> news() {
		return collection(New.class); // get(news, New.class);
	}
//	public static MongoCollection<QueuedUser> queue_simple_unranked() {
//		return get(queue_simple_unranked, QueuedUser.class);
//	}
//	public static MongoCollection<QueuedUser> queue_simple_ranked() {
//		return get(queue_simple_ranked, QueuedUser.class);
//	}
	public static MongoCollection<QueueeBlind> queue_simple_blind() {
		return collection(QueueeBlind.class); // get(queue_simple_blind, QueueeBlind.class);
	}
	public static MongoCollection<QueueeDraft> queue_simple_draft() {
		return collection(QueueeDraft.class); // get(queue_simple_draft, QueueeDraft.class);
	}
	public static MongoCollection<Lobby> lobbies(){
		return collection(Lobby.class); // get(lobbies, Lobby.class);
	}


	public static <T> MongoCollection<T> collection(Class<T> clazz) {
		var fullpackag = clazz.getPackageName();
		var packag = fullpackag.substring(fullpackag.lastIndexOf('.'));
		var collection = clazz.getSimpleName();
		return client.getDatabase(root + ":" + packag).getCollection(collection, clazz);
	}

}
