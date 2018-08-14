package com.souchy.randd.situationtest.properties;

import com.souchy.randd.situationtest.properties.types.Elements;

public class ElementValue {

	/*List<Supplier<Integer>> values;
	private int[] boundaries;
	private int override;*/
	
	public final Elements element;
	/**
	 * prob be private
	 */
	public int value;

	/*public ElementValue(ElementValue val) {
		this.element = val.element;
		supply = val.supply;
	}*/
	
	public ElementValue(Elements element, int value) {
		this.element = element;
		this.value  = value;
		
		/*supply = () -> value;
		supply = () -> supply.get() + value;
		
		
		Supplier<Integer> s2;
		
		//supply.
		*/
	}
	
	public int get() {
		return value;
	}
	/*public int get() {
		return supply.get();
	}
	
	private Supplier<Integer> supply = () -> {
		return value;
	};*/
	
	
	public ElementValue addSet(ElementValue val) {
		/*if(element != val.element) {
			throw new Exception();
		}*/
		this.value += val.value;
		return this;
	}
	public ElementValue addSet(int val) {
		this.value += val;
		return this;
	}
	public ElementValue subSet(int val) {
		this.value -= val;
		return this;
	}
	public ElementValue multSet(int val) {
		this.value *= val;
		return this;
	}
	public ElementValue divSet(int val) {
		this.value /= val;
		return this;
	}
	

	public int add(int val) {
		return this.value = val;
	}
	public int sub(int val) {
		return this.value = val;
	}
	public int mult(int val) {
		return this.value = val;
	}
	public int div(int val) {
		return this.value = val;
	}

	public ElementValue copy() {
		return new ElementValue(element, value);
	}
	
	
}
