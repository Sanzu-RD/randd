package com.souchy.randd.jade.meta;

import org.bson.types.ObjectId;

public class New {
	
	public ObjectId _id;
	/** news url on the website */
	public String url;
	/** image hosted on the azur server */
	public String thumbnailUrl; 
	public String title;
	public String content;
	
	
	public static final String name__id = "_id";
	public static final String name_url = "url";
	public static final String name_thumbnailUrl = "thumbnailUrl";
	public static final String name_title = "title";
	public static final String name_content = "content";
	
}
