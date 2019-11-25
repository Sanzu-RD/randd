package com.souchy.randd.deathshadows.iolite.emerald;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.souchy.randd.commons.tealwaters.commons.Namespace.MongoNamespace;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.jade.meta.*;
import com.souchy.randd.jade.meta.Match;
import com.souchy.randd.jade.meta.New;
import com.souchy.randd.jade.meta.User;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;

/**
 * MongoDB access
 * 
 * @author Blank
 *
 */
public final class Emerald {
	
	private static final String ip = "localhost";
	private static final int port = 27017;
	private static final String user = "";
	private static final String pass = "";
	
	private static final MongoClient client; 
	
	private static final String root = "hidden_piranha";
	private static final MongoNamespace logs = new MongoNamespace(root, "logs");
	private static final MongoNamespace users = new MongoNamespace(root, "users");
	private static final MongoNamespace decks = new MongoNamespace(root, "decks");
	private static final MongoNamespace matches = new MongoNamespace(root, "matches");
	private static final MongoNamespace news = new MongoNamespace(root, "news");
	private static final MongoNamespace queue_simple_unranked = new MongoNamespace(root, "queue_simple_unranked");
	private static final MongoNamespace queue_simple_ranked = new MongoNamespace(root, "queue_simple_ranked");
	
	static {
		var credentials = user + ":" + pass + "@";
		credentials = "";
		var registry = CodecRegistries.fromRegistries(
				MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromCodecs(new ZonedDateTimeCodec()),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
		);
		;

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
	
	public static MongoCollection<User> users() {
		return get(users, User.class);
	}
	
	public static MongoCollection<Deck> decks() {
		return get(decks, Deck.class);
	}
	
	public static MongoCollection<Match> matchs() {
		return get(matches, Match.class);
	}
	public static MongoCollection<New> news() {
		return get(news, New.class);
	}
	public static MongoCollection<QueuedUser> queue_simple_unranked() {
		return get(queue_simple_unranked, QueuedUser.class);
	}
	public static MongoCollection<QueuedUser> queue_simple_ranked() {
		return get(queue_simple_ranked, QueuedUser.class);
	}
	public static MongoCollection<Log> logs() {
		return get(logs, Log.class);
	}
	
}
