package com.souchy.randd.situationtest.models.org;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.situationtest.models.entities.Character;
import com.souchy.randd.situationtest.models.entities.Summon;

public class Timeline {
	

	private int _index;
	private List<Character> queue;
	
	
	public Character current() {
		return queue.get(_index);
	}
	public Character next() {
		return next(_index);
	}
	public Character previous() {
		return previous(_index);
	}
	public Character next(Character c) {
		return next(queue.indexOf(c));
	}
	public Character previous(Character c) {
		return previous(queue.indexOf(c));
	}
	public Character next(int i) {
		i = i + 1;
		if(i == queue.size()) {
			i = 0;
		}
		return queue.get(i);
	}
	public Character previous(int i) {
		i = i + 1;
		if(i == -1) {
			i = queue.size() - 1;
		}
		return queue.get(i);
	}
	public Character shiftNext() {
		_index++;
		if(_index == queue.size()) {
			_index = 0;
		}
		return queue.get(_index);
	}
	
	public Character shiftPrevious() {
		_index++;
		if(_index == -1) {
			_index = queue.size() - 1;
		}
		return queue.get(_index);
	}
	
	
	public List<Summon> getSummons(Character summoner){
		List<Summon> summons = new ArrayList<>();
		int i = queue.indexOf(summoner);
		Character c;
		while((c = next(i)) instanceof Summon) {
			Summon s = (Summon) c;
			if(s.getSummoner() == summoner) 
				summons.add(s);
		}
		return summons;
	}
	
	

}
