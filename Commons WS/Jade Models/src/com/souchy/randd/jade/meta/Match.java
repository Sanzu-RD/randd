package com.souchy.randd.jade.meta;

import java.time.Instant;
import java.util.Map;

import org.bson.types.ObjectId;

import gamemechanics.common.Action;

public class Match {

	public ObjectId _id;
	public ObjectId userIDWin;
	public ObjectId deck1;
	public ObjectId deck2;
	public Instant date;
	public Map<Instant, Action> actions;
	
	
	public static final String name__id = "_id";
	public static final String name_userIDWin = "userIDWin";
	public static final String name_deck1 = "deck1";
	public static final String name_deck2 = "deck2";
	public static final String name_date = "date";
	public static final String name_actions = "actions";
	
}
