package com.souchy.randd.deathshadows.iolite.ruby;

import com.souchy.randd.commons.tealwaters.commons.Namespace.RedisNamespace;

import redis.clients.jedis.Jedis;

/**
 * Redis access
 * 
 * @author Blank
 *
 */
public class Ruby {

	private static final String ip = "localhost";
	private static final int port = 27017;
	private static final String user = "";
	private static final String pass = "";

	private static final Jedis jedis; 
	
	private static final String root = "hidden_piranha";
	private static final RedisNamespace queue_simple_unranked = new RedisNamespace(root, "queue_simple_unranked");
	private static final RedisNamespace queue_simple_ranked = new RedisNamespace(root, "queue_simple_ranked");
	//public static final RedisNamespace fights = new RedisNamespace("hidden_piranha", "fights");
	
	static {
		jedis = new Jedis(ip, port);
	}
	
	public static Object queuePlay() {
		return jedis.get(queue_simple_unranked.toString());
	}
	
	public static Object queueRanked() {
		return jedis.get(queue_simple_ranked.toString());
	}
	
	
}
