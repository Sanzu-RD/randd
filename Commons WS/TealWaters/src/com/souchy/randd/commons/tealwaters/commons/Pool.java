package com.souchy.randd.commons.tealwaters.commons;

import java.util.ArrayList;
import java.util.List;

public class Pool<T> extends ArrayList<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8565069931084718278L;
	
	
	public List<T> backingPool = new ArrayList<>();
	
	
//	private int poolSize = 1;
//	public void setUsed(int size) {
//		poolSize = size;
//	}
//	
//	public void purgeExcess() {
//		this.removeRange(poolSize - 1, this.size() - 1);
//	}
	
//	public void addAllTo(Collection<T> c) {
//		c.addAll(this.subList(0, poolSize - 1));
//	}
//	
//	public void removeAllFrom(Collection<T> c) {
//		c.removeAll(this.subList(0, poolSize - 1));
//	}
	
	public T obtain(int i){
		T t = backingPool.get(i);
		this.add(t);
		return t;
	}
	public void extend(T t) {
		backingPool.add(t);
		this.add(t);
	}
	
	public int poolSize() {
		return backingPool.size();
	}
	
	
	
}
