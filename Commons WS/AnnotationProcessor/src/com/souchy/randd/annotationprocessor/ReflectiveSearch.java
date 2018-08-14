package com.souchy.randd.annotationprocessor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ModuleElement;

class ReflectiveSearch {

	public static void getRecursiveInterfaceSearch(Class<?> c, List<Class<?>> parents) {
		Class<?> superclass = c.getSuperclass();
		if(superclass != null) {
			parents.add(superclass);
			getRecursiveInterfaceSearch(superclass, parents);
		}
		
		Class<?>[] interfaces = c.getInterfaces();
		if(interfaces.length != 0) {
			for(Class<?> p : interfaces) {
				parents.add(p);
				getRecursiveInterfaceSearch(p, parents);
			}
		}
	}
	public static void getRecursiveEnclosedElementSearch(Element parent, List<Element> children) {
		getRecursiveEnclosedElementSearch(parent, children, e -> true, e -> true);
	}
	public static void getRecursiveEnclosedElementSearch(Element parent, List<Element> children, Predicate<Element> filteradd, Predicate<Element> filtersearch) {
		List<? extends Element> elements = parent.getEnclosedElements();
		if(elements.size() != 0) {
			//children.addAll(elements);
			elements.forEach(e -> {
				if(filteradd.test(e)) children.add(e);
				if(filtersearch.test(e)) getRecursiveEnclosedElementSearch(e, children, filteradd, filtersearch);
			});
		}
	}
	

	public static Set<Element> findAllClasses(ProcessingEnvironment penv) {
		return findAllElements(penv, e -> e.getKind() == ElementKind.CLASS, e -> true);
	}
	public static Set<Element> findAllAnnotatedClasses(ProcessingEnvironment penv, Class<? extends Annotation> annotation) {
		return findAllElements(penv, e -> e.getKind() == ElementKind.CLASS && e.getAnnotation(annotation) != null, e -> true);
	}

	public static Set<Element> findAllElements(ProcessingEnvironment penv, Predicate<Element> filteradd, Predicate<Element> filtersearch) {
		Set<Element> elements = new HashSet<>();
		
		Set<? extends ModuleElement> moduleElements = penv.getElementUtils().getAllModuleElements();
		moduleElements.forEach(m -> {
			if (m.getSimpleName().toString().toLowerCase().contains("com.souchy") || m.getSimpleName().toString().toLowerCase().contains("com.hiddenpiranha")) {
				
				List<Element> ele = new ArrayList<>();
				getRecursiveEnclosedElementSearch(m, ele, e1 -> {
					return e1.getKind() == ElementKind.CLASS && e1.getAnnotation(ID.class) != null;
				}, e2 -> true);
				
				ele.stream().filter(e -> e.getAnnotation(ChildMustAnnotate.class) != null).forEach(e -> {
					// messager.printMessage(Kind.NOTE, "Found childmustannotate ["+e.getSimpleName()+"] in module ["+m.getSimpleName()+"]");
					elements.add(e);
				});
			}
		});
		return elements;
	}
	
	
}
