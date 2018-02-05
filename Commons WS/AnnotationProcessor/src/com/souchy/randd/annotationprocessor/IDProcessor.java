package com.souchy.randd.annotationprocessor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

@SupportedAnnotationTypes({ "com.souchy.randd.annotationprocessor.ID" })
// @SupportedSourceVersion(SourceVersion.RELEASE_6)
public class IDProcessor extends AbstractProcessor {

	private Messager messager;
	//private ProcessingEnvironment penv;

	@Override
	public void init(ProcessingEnvironment env) {
	//	penv = env;
		messager = env.getMessager();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
		messager.printMessage(Kind.NOTE, "ID process start 4 ============================================");
		Map<Integer, Element> ids = new HashMap<>();
		Set<Element> elements = new HashSet<>();
		//elements.addAll(findAllAnnotatedClasses(penv, ID.class));
		elements.addAll(env.getElementsAnnotatedWith(ID.class));
		//elements.addAll(env.getRootElements());
		elements.forEach(e -> {
			ID id = e.getAnnotation(ID.class);
			if(id == null) {
				// ignore
			} else if(e.getAnnotation(ChildMustAnnotate.class) != null) {
				messager.printMessage(Kind.NOTE, "ID process ChildMustAnnotate [" + e + "]");
			} else if (e.getKind() != ElementKind.CLASS) {
				messager.printMessage(Kind.ERROR, "@ID annotation is for classes only. [" + e + "] ", e);
			} else {
				try {
					if (ids.keySet().contains(id.id())) {
						Element old = ids.get(id.id());
						messager.printMessage(Kind.ERROR, "Duplicate ID [" + id.id() + "] with class [" + e + "]", old);
						messager.printMessage(Kind.ERROR, "Duplicate ID [" + id.id() + "] with class [" + old + "]", e);
					} else {
						ids.put(id.id(), e);
						// ok id accepté
						messager.printMessage(Kind.NOTE, "ID accept [" + id.id() + "] on class [" + e + "]");
					}
				} catch (Exception e1) {
					messager.printMessage(Kind.ERROR, "shit sucks [" + e + "]", e);
				}
			}
		});
		messager.printMessage(Kind.NOTE, "ID process end 4 ==============================================");
		return true;
	}

}
