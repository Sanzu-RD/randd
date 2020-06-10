package data.new1.timed;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;

import data.new1.ecs.Entity;
import gamemechanics.main.DiamondModels;
import gamemechanics.models.Fight;
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
	
	public void addStatus(Status s) {
		boolean fused = false;
		if(hasStatus(s.getClass())) {
			fused = getFirst(s.getClass()).fuse(s); //.stackAdd(s.stacks);
		} 
		if(!fused){
			//statuses.put(s.getClass(), s);
			statuses.add(s);
			s.onAdd();
			//s.target.fight.bus.post(new OnStatusAdd(s));
		}
	}
	
//	public void remove(Class<? extends Status> s) {
//		if(has(s)) {
//			get(s).onLose();
//			get(s).dispose();
//			//statuses.remove(s.getClass()); 
//			statuses.remove(s);
//		}
//	}

	/** remove one status */
	public void removeStatus(Status s) {
		//remove(s.getClass()); 
		var removed = statuses.remove(s);
		if(removed) {
            removeProcess(s);
		}
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
    	s.dispose();
	}
	
	public void forEach(Consumer<Status> c) {
		//statuses.values().forEach(s -> c.accept(s));
		statuses.forEach(s -> c.accept(s));
	}

	public int size() {
		return statuses.size();
	}
	
	
	

	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(statuses.size());
		statuses.forEach(status -> {
			out.writeInt(status.modelID());
			status.serialize(out);
		});
		return null;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		int statusesSize = in.readInt();
		// deserialize tous les status
		for(int i = 0; i < statusesSize; i++) {
			int modelid = in.readInt();
			int sourceid = in.readInt();
			int targetid = in.readInt();
			
			var statusModel = DiamondModels.statuses.get(modelid);
			var status = statusModel.create(get(Fight.class), sourceid, targetid); //new EntityRef(this.fight, sourceid), new EntityRef(this.fight, targetid)); //this.fight, sourceid, targetid);
			status.deserialize(in);
			
			this.statuses.add(status);
		}
		return null;
	}
	
}