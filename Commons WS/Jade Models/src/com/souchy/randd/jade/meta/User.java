package com.souchy.randd.jade.meta;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;


public class User {

	
	public ObjectId _id;
	
	/**
	 * This is the authorization level of the user
	 */
	public UserLevel level = UserLevel.User;
	/**
	 * This is the username to log in
	 */
	public String username;
	/**
	 * This is the hashed password
	 */
	public String password;
	/**
	 * This is the salt to hash the password
	 */
	public String salt;
	/**
	 * This is the publicly shown Pseudo
	 */
	public String pseudo;
	/**
	 * This is the user's email
	 */
	public String email;
	/**
	 * This is if the email has been verified (user creates account -> server sends email for confirmation -> user click confirmation link)
	 */
	public boolean verifiedEmail;
	/**
	 * This is the user's phone number
	 */
	public String phone;
	/**
	 * This is if the phone number has been verified (user asks for 2fa-> server sends SMS for confirmation -> user enters the code from the SMS into Amethyst)
	 */
	public boolean verifiedPhone;
	
	
	
	/** can automatically unlock free things at x levels */
	public int xp; 
	/** free currency to buy things */
	public int gold; 
	/** paid currency to buy things */
	public int gems; 
	public List<ObjectId> friends = new ArrayList<>();
	public List<ObjectId> decks = new ArrayList<>();
	public List<ObjectId> matchHistory = new ArrayList<>();
	public List<ObjectId> unlockedCreatures = new ArrayList<>();
	public List<ObjectId> unlockedItems = new ArrayList<>();
	public List<ObjectId> unlockedSpells = new ArrayList<>();

	
	
	
	public static final String name__id = "_id";
	public static final String name_username = "username";
	public static final String name_password = "password";
	public static final String name_pseudo = "pseudo";
	public static final String name_email = "email";
	public static final String name_xp = "xp";
	public static final String name_gold = "gold";
	public static final String name_gems = "gems";
	public static final String name_friends = "friends";
	public static final String name_decks = "decks";
	public static final String name_unlockedCreatures = "unlockedCreatures";
	public static final String name_unlockedItems = "unlockedItems";
	public static final String name_unlockedSpells = "unlockedSpells";
	public static final String name_matchHistory = "matchHistory";
	public static final String name_level = "level";
	
	
}
