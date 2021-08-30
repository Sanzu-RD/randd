package com.souchy.randd.commons.diamond.models;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;
import com.souchy.randd.commons.tealwaters.ecs.Entity;
import com.souchy.randd.commons.tealwaters.logging.Log;

import io.netty.buffer.ByteBuf;


public class StatusList extends Entity implements BBSerializer, BBDeserializer {
	
	//private Map<Class<? extends Status>, Status> statuses;
	private List<Status> statuses;
	
	public StatusList(Fight f) {
		super(f);
		//statuses = new HashMap<>();
		statuses = new ArrayList<>();
	}
	
	public boolean hasStatus(Class<? extends Status> c) {
		//return statuses.containsKey(c);
		for(var s : statuses)
			if(s.getClass() == c)
				return true;
		return false;
	}
	
	public Status getFirst(Class<? extends Status> c) {
		for(var s : statuses)
			if(s.getClass() == c)
				return s;
		return null;
		//return statuses.get(c);
	}
	
	public boolean addStatus(Status s) {
		//Log.format("StatusList %s", s);
		boolean fused = false;
		if(hasStatus(s.getClass())) {
			fused = getFirst(s.getClass()).fuse(s); //.stackAdd(s.stacks);
		} 
		//Log.format("StatusList %s, %s", s, fused);
		if(fused) {
			s.dispose();
		} else {
			//Log.format("StatusList add1 %s %s", s.id, s);
			//statuses.put(s.getClass(), s);
			statuses.add(s);
			s.onAdd();
			//s.target.fight.bus.post(new OnStatusAdd(s));
			
			Log.format("StatusList add2 %s %s", s.id, s);
			
//			s.register(get(Fight.class));
//			get(Fight.class).statusbus.register(s);
			return true;
		}
		return false;
	}
	

	/** remove one status */
	public boolean removeStatus(Status s) {
		//remove(s.getClass()); 
		var removed = statuses.remove(s);
		if(removed) {
            removeProcess(s);
		}
		return removed;
	}
	/** remove all */
	public void removeIf(Predicate<Status> filter) {
        var it = statuses.iterator();
        while (it.hasNext()) {
        	var s = it.next();
            if (filter.test(s)) {
                it.remove();
                removeProcess(s);
            }
        }
		//statuses.removeIf(filter);
	}
	/** remove all instances of the status class */
	public void removeStatus(Class<? extends Status> c) {
		removeIf(s -> s.getClass() == c);
	}
	
	private void removeProcess(Status s) {
    	s.onLose();
		//s.target.fight.bus.post(new OnStatusLose(s));
    	//s.dispose();
	}
	
	public void forEach(Consumer<Status> c) {
		statuses.forEach(s -> c.accept(s));
	}
	public double sum(Function<Status, Double> f) {
		double d = 0;
		for(var s : statuses) {
			d += f.apply(s);
		}
		return d;
	}
	
	public int size() {
		return statuses.size();
	}
	
	/**
	 * Returns a list of ids of all statuses
	 */
	public String toString() {
		//Integer.toString(s.id)
		return statuses.stream().map(s -> "").collect(Collectors.joining(","));
	}
	

	@Override
	public ByteBuf serialize(ByteBuf out) {
//		out.writeInt(statuses.size());
////		Log.info("write status size " + statuses.size());
//		statuses.forEach(status -> {
//			//out.writeInt(status.modelid());
//			status.serialize(out);
//		});
		writeList(out, statuses);
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		int statusesSize = in.readInt();
//		Log.info("read status size " + statusesSize);
		// deserialize tous les status
		for(int i = 0; i < statusesSize; i++) {
			int modelid = in.readInt();
			var statusModel = DiamondModels.statuses.get(modelid);
			var status = statusModel.create(this.get(Fight.class), 0, 0);
			status.deserialize(in);
			
			/*
			int modelid = in.readInt();
			int sourceid = in.readInt();
			int targetid = in.readInt();
			
			var statusModel = DiamondModels.statuses.get(modelid);
			var status = statusModel.create(null, sourceid, targetid); //new EntityRef(this.fight, sourceid), new EntityRef(this.fight, targetid)); //this.fight, sourceid, targetid);
			status.deserialize(in);
			*/
			
			this.statuses.add(status);
		}
		return null;
	}
	
}