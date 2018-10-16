package com.souchy.randd.situationtest.math;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class Buffer<T> {
	
	private List<T> data;
	//private ListIterator<T> itr;
	private int cursor = -1;
	
	public Buffer(){
		data = new LinkedList<T>();
		//itr = data.listIterator();
	}
	public Buffer(int initialCapacity){
		data = new ArrayList<T>(initialCapacity);
		//itr = data.listIterator();
	}
	
	/**
	 * @return Current position of the cursor
	 */
	public int position(){
		return cursor;
	}
	/**
	 * Sets the current position of the cursor
	 * @param i - New cursor position
	 * @return This buffer
	 */
	public Buffer<T> setPosition(int i){
		/*itr = data.listIterator(i);
		cursor = i;
		return this;*/
        if (i < 0 || i >= data.size())
            throw new NoSuchElementException();
		cursor = i;
		return this;
	}
	
	public boolean hasRemaining(){
		//return itr.hasNext();
		return remaining() > 0;
	}
	
	/**
	 * @return Number of remaining elements after the cursor
	 */
	public int remaining(){
		return data.size() - cursor - 1;
	}
	
	/**
	 * 
	 * @return Current element in the buffer or null if there's none or if the cursor is mispositioned 
	 */
	public T current() {
		if(cursor < 0 || cursor >= data.size()) return null;
		return data.get(cursor);
	}
	
	/**
	 * 
	 * @return Current index element after the index is incremented
	 * @throws IndexOutOfBoundsException if there is no remaining element before the index is incremented
	 */
	public T next() {
		//cursor = itr.nextIndex();
		//return itr.next();
        /*checkForComodification();
        int i = cursor;
        if (i >= size)
            throw new NoSuchElementException();
        Object[] elementData = ArrayList.this.elementData;
        if (i >= elementData.length)
            throw new ConcurrentModificationException();
        cursor = i + 1;
        return (E) elementData[lastRet = i];
		*/
		
		if(!hasRemaining()) throw new NoSuchElementException(); //"cursor "+(cursor+1)+" out of bounds");
		cursor++;
		return data.get(cursor);
	}
	
	public Buffer<T> push(T t){
		data.add(t);
		return this;
	}
	
	public Buffer<T> insert(T t){
		if(hasRemaining()) data.add(cursor + 1, t);
		else push(t);
		return this;
	}
	
	public boolean remove(T t) {
		return data.remove(t);
	}
	
	public Buffer<T> flip(){
		
		return this;
	}
	
	
	
	
}
