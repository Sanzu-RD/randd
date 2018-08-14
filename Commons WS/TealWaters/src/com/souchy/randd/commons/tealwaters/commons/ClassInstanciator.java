package com.souchy.randd.commons.tealwaters.commons;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 * Creates an instance of a class or creates a list of instance from a list of classes
 * <br>
 * Old name : InstanceProvider
 * 
 * @author Souchy
 *
 * @param <T> Type of the resulting class instance
 */
public class ClassInstanciator<T> implements Function<Class<T>, T> {
	
	@Override
	public T apply(Class<T> c) {
		return create(c);
	}
	
	public static <T> T create(Class<T> c) {
		try {
			return (T) c.getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static <T> Stream<T> list(Collection<Class<T>> classes){
		return classes.stream().map(c -> ClassInstanciator.create(c)); //new InstanceProvider<T>());
	}

}
