package com.souchy.randd.jade.mm;

import org.bson.types.ObjectId;

public class QueuedUser {

	public ObjectId _id;
	public ObjectId userid;
	public int mmr;
	/***/
	public String queueName;
	public int timeQueued;
	

	public static final String name__id = "_id";
	public static final String name_userid = "userid";
	public static final String name_mmr = "mmr";
	
}
