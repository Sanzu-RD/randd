package com.souchy.randd.commons.tealwaters.commons;

import com.souchy.randd.annotationprocessor.ChildMustAnnotate;
import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.tealwaters.logging.Log;

@ChildMustAnnotate(ID.class)
public interface AnnotatedIdentifiable extends Identifiable<Integer> {
	@Override
	default Integer getID() {
		return getID(this.getClass());
	}

	public static int getID(Class<?> c) {
		var annotation = c.getAnnotation(ID.class);
		if(annotation == null) {
			Log.error("message has no ID annotation " + c);
			System.exit(0);
		}
		return annotation.id();
	}
}
