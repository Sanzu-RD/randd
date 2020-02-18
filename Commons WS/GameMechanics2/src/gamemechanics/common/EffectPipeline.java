package gamemechanics.common;

import java.util.List;

import data.new1.Effect;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public class EffectPipeline {
	public Effect executing;
	public List<Effect> pipe;
	public int index;

	public void reset() {
		if(executing == null) top();
		else index = pipe.indexOf(executing);
	}
	public void top() {
		index = 0;
	}
	public void bottom() {
		index = pipe.size();
	}
	public void up() {
		if(index > 0) index--;
	}
	public void down() {
		if(index < pipe.size()) index++;
	}
	public boolean insert(Effect e) {
		pipe.add(index, e);
		if(executing == null) {
			executing = e;
			return true;
		}
		return false;
	}
	public boolean add(Effect e) {
		pipe.add(e);
		if(pipe.isEmpty()) {
			return true;
		}
		return false;
	}
	public void pop() {
		var i = pipe.indexOf(executing);
		pipe.remove(i);
	}
}
