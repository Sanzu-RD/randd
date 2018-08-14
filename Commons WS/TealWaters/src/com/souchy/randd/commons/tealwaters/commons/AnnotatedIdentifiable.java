package com.souchy.randd.commons.tealwaters.commons;

import com.souchy.randd.annotationprocessor.ChildMustAnnotate;
import com.souchy.randd.annotationprocessor.ID;

@ChildMustAnnotate(ID.class)
public interface AnnotatedIdentifiable extends Identifiable<Integer> {
	@Override
	default Integer getID() {
		return getID(this.getClass());
	}

	public static int getID(Class<?> c) {
		return c.getAnnotation(ID.class).id();
	}
}
