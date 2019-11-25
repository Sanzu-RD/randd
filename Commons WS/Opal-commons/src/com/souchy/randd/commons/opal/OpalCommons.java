package com.souchy.randd.commons.opal;

import com.souchy.randd.commons.tealwaters.commons.Namespace.HttpNamespace;

public class OpalCommons {
	
	// can't use non-constant expressions for path annotations :(
	//public static final HttpNamespace root = new HttpNamespace("/api/");
	//public static final HttpNamespace authenticate = root.in("authenticate/");
	
	
	public static final String root = "/api/";
	
	public static final String authentication = root + "authentication/";
	public static final String news = root + "news/";
	
	/**
	 * a user's collection of creatures, spells, items
	 */
	public static final String collection = root + "collection/";
	/**
	 * a user's decks
	 */
	public static final String decks = root + "decks/";
	public static final String shop = root + "shop/";
	
	//public static final String encyclopedia = "encyclopedia/";
	
	/**
	 * encyclopedia of creatures
	 */
	//public static final String creatures = "creatures/";
	/**
	 * encyclopedia of spells
	 */
	//public static final String spells = "spells/";
	/**
	 * encyclopedia of items
	 */
	//public static final String items = "items/";
	
}
 