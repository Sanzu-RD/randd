package com.souchy.randd.annotationprocessor;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation pour forcer les enfants du type annot� par ceci � �tre annot� par l'annotation sp�cifi�e
 * @author Souchy
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
//@Inherited
public @interface ChildMustAnnotate {

	Class<? extends Annotation> value();
	
}
