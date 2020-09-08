package com.souchy.randd.deathshadows.coral.main;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.jade.matchmaking.Queuee;

public interface CoralDB {

	/**
	 * foreach Queuee until the end or the function returns true; then break the iterator.
	 */
	public void foreach(Function<Queuee, Boolean> action);
	/**
	 * add a Queuee to the queue
	 */
	public void enqueue(Queuee queuee);
	/**
	 * remove a user from the queue
	 */
	public void dequeue(ObjectId userid);
	
	/**
	 * MongoDB queue
	 * 
	 * @author Blank
	 * @date 7 sept. 2020
	 */
	@SuppressWarnings("unchecked")
	public static class CoralEmerald implements CoralDB {
		public void foreach(Function<Queuee, Boolean> action) {
			var docs = ((MongoCollection<Queuee>) Emerald.collection(Coral.coral.queue.getQueueeClass())).find();
			var iter = docs.iterator();
			while(iter.hasNext()) {
				var q = iter.next();
				if(action.apply(q)) break;
			}
			iter.close();
		}
		public void enqueue(Queuee queuee) {
			Emerald.collection(Coral.coral.queue.getQueueeClass()).insertOne(queuee);
		}
		public void dequeue(ObjectId userid) {
			Emerald.collection(Coral.coral.queue.getQueueeClass()).deleteOne(Filters.eq(userid));
		}
	}
	
	/**
	 * In-memory list queue
	 * 
	 * @author Blank
	 * @date 7 sept. 2020
	 */
	public static class CoralList implements CoralDB {
		private List<Queuee> list = new ArrayList<>();
		public void foreach(Function<Queuee, Boolean> action) {
			var iter = list.iterator();
			while(iter.hasNext()) {
				var q = iter.next();
				if(action.apply(q)) break;
			}
		}
		public void enqueue(Queuee queuee) {
			list.add(queuee);
		}
		public void dequeue(ObjectId userid) {
			list.removeIf(q -> q._id == userid);
		}
	}
	
}
