package com.souchy.randd.annotationprocessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation pour donner un numéro d'identification à un type / classe
 * @author Souchy
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ID {
	
	/**
	 * id par défaut signifiant que l'id du type annoté n'a pas été implémenté
	 */
	public static final int NO_ID = Integer.MIN_VALUE;
	
	/**
	 * Numéro d'identification du type
	 * @return
	 */
	int id();// default NO_ID;
	
}
