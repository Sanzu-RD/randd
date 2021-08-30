package com.souchy.randd.commons.diamond.common.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

import com.souchy.randd.commons.tealwaters.logging.Log;

public class IndexedList<T> { // extends ArrayList<T> {

	/**
	 * amount of times the list has been looped
	 */
	private int turn;

	/**
	 * current index in the list
	 */
	private int index;

	public List<T> list = new ArrayList<>();
	
//	private T defaultValue = null;
	
	public IndexedList() {
//		this.defaultValue = defaultValue;
	}
	public IndexedList(List<T> list, int index, int turn) {
//		this.defaultValue = defaultValue;
		this.list = list;
		this.index = index;
		this.turn = turn;
	}

	public synchronized void add(T t) {
		list.add(t);
	}

	public synchronized void add(int i, T t) {
		list.add(i, t);
		if (i <= index) {
			move(1);
		}
	}

	public synchronized void add(T[] ts) {
		for (var t : ts)
			add(t);
	}

	public synchronized void remove(T t) {
		remove(list.indexOf(t));
	}

	public synchronized void remove(int i) {
		list.remove(i);
		if (i < index) {
			move(-1);
		}
	}

	/**
	 * Current value (ex: player id)
	 */
	public synchronized T current() {
		if(index < 0 || list.size() <= index) return null;
		return list.get(index);
	}
	
//	public synchronized T previous() {
//		var i = index - 1;
//		if(i < 0) i = list.size() - 1;
//		return list.get(i);
//	}

	/**
	 * Current round
	 */
	public synchronized int turn() {
		return turn;
	}

	/**
	 * Current index in the line
	 */
	public synchronized int index() {
		return index;
	}
	
	public synchronized int indexOf(T t) {
		return list.indexOf(t);
	}


	public synchronized void setTurn(int i) {
		turn = i;
	}
	public synchronized void setIndex(int i) {
		index = i;
	}

	public synchronized void moveUp() {
		move(1);
	}

	public synchronized void move(int i) {
		index += i;
		while (index >= list.size()) {
			index -= list.size();
			turn++;
		}
	}

	public synchronized T get(int index) {
		if(index > list.size() || index < 0) return null;
		return list.get(index);
	}
	
	public synchronized int size() {
		return list.size();
	}
	
	public synchronized List<T> copy(){
		return new ArrayList<>(list);
	}
	
	/**
	 * Find the next value that matches a predicate. Null if no value matches.
	 * @param valueTester
	 * @return
	 */
	public synchronized T findNext(Predicate<T> valueTester) {
		if(size() == 0) return null;
		int i = index;
		int max = list.size() + index;
		T v = null;
		do {
			v = get(i % list.size());
			i++;
//			Log.format("Timeline.findNext, size=%s, i=%s, v=%s", list.size(), i-1, v);
		} while (!valueTester.test(v) && i < max);
		
		if(i >= max) v = null; // si on n'a pas trouvé de match
		
		return v;
	}
	
}
