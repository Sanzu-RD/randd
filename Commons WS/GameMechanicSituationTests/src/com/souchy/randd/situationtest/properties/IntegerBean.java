package com.souchy.randd.situationtest.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 
 * Cette Bean lie plusieurs valeurs Int 
 * 
 * 
 * @author Souchy
 *
 * @param <T> - Type
 */
public class IntegerBean<T> {
	
	private int value;
	private List<IntegerBean<T>> linkedBeans; //private List<Supplier<T>> suppliers;
	
	public IntegerBean(int value) {
		this.value = value;
		linkedBeans = new ArrayList<>();
	}
	
	public Integer getTotal() {
		int result = value;
		for(IntegerBean<T> s : linkedBeans) {
			result += s.getTotal();
		}
		return result;
	}
	
	public int getBase() {
		return value;
	}
	public void setBase(int val) {
		value = val;
	}
	
	/**
	 * Can only add, can't substract, so if you want to substract, just make a negative bean
	 * Otherwise i'd need a hashmap or smth to determine which bean is negative or not
	 * 
	 * En vrai ça serait super rare qu'on call .remove(). 
	 * En réalité quand tu perd des stats c'est à cause d'un buff qui part,
	 * à ce moment on callerait sûrement buff.dispose()
	 * @param bean
	 */
	public boolean add(IntegerBean<T> bean) {
		if(bean == this || bean.linkedBeans.contains(this)) {
			 // dont want loops
			return false;
		}
		return linkedBeans.add(bean); 
	}

	/*public void add(IntegerBean<T> bean) {
		suppliers.add(() -> + bean.get());
	}
	public void sub(IntegerBean<T> bean) {
		suppliers.add(() -> - bean.get());
	}*/
	
	public boolean remove(IntegerBean<T> bean) {
		// ...  list of beans ?
		return linkedBeans.remove(bean);
	}
	
	public void clear() {
		linkedBeans.clear();
	}
	
}
