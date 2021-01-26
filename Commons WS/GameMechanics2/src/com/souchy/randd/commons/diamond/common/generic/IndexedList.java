package com.souchy.randd.commons.diamond.common.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

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
	
	public IndexedList() {
		
	}
	public IndexedList(List<T> list, int index, int turn) {
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
		return list.get(index);
	}

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
	
}
