package com.souchy.randd.deathshadows.iolite.emerald;

import org.bson.types.ObjectId;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;

/**
 * Object that should be on the emerald database should implement this. <p>
 * 
 * Ex: creature, spell, status, cell, etc. <p>
 * 
 * We'll implement this later to keep fights safe in case of a server failure
 * 
 * 
 * ------ I cant make game objects extend this because game objects are also on the client side and we dont want server code there
 * ------ Therefore, saving will be completely separate
 * 
 * 
 * @author Blank
 * @date 24 juin 2020
 */
public interface EmeraldObj extends Identifiable<ObjectId> {
	
	public static ReplaceOptions options = new ReplaceOptions() {
		{
			upsert(true);
		}
	};
	
	/**
	 * Find the first object of the class that has the id
	 */
	public static <T extends EmeraldObj> T get(Class<T> clazz, ObjectId id) {
		return Emerald.collection(clazz).find(Filters.eq(id)).first();
	}
	
	public default void adfs() {
		Emerald.collection(EmeraldObj.class).replaceOne(Filters.eq(this.getID()), this, options);
	}
	
	/**
	 * Save this whole object on mongo. <br>
	 * <code>Emerald.collection(MyClass.class).replaceOne(Filters.eq(this.getID()), this, options);</code>
	 */
	public void save();


}
