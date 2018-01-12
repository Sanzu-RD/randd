package com.souchy.randd.annotationprocessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation pour donner un num�ro d'identification � un type / classe
 * @author Souchy
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ID {
	
	/**
	 * id par d�faut signifiant que l'id du type annot� n'a pas �t� impl�ment�
	 */
	public static final int NO_ID = Integer.MIN_VALUE;
	
	/**
	 * Num�ro d'identification du type
	 * @return
	 */
	int id();// default NO_ID;
	
}
