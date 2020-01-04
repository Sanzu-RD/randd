package com.souchy.randd.jade.mm;

import org.bson.types.ObjectId;

/**
 * Person in a match making queue
 * 
 * @author Blank
 * @date 24 déc. 2019
 */
public class Queuee {
	/**
	 * Client id
	 */
	public ObjectId userid;
	/**
	 * Rating
	 */
	public int mmr;
	/**
	 * currentTimeMillis at which he started queueing
	 */
	public long timeQueued; 
	/**
	 * Queue type
	 */
	public GameQueue queue;
	

	public static final String name_userid = "userid";
	public static final String name_mmr = "mmr";
	public static final String name_timeQueued = "timeQueued";
	public static final String name_queue = "queue";
}