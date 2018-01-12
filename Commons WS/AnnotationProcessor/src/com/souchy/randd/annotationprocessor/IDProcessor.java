package com.souchy.randd.annotationprocessor;

import java.util.HashMap;
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

	@Override
	public void init(ProcessingEnvironment env) {
		// penv = env;
		messager = env.getMessager();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
		Map<Integer, Element> ids = new HashMap<>();
		Set<? extends Element> elements = env.getElementsAnnotatedWith(ID.class);
		elements.forEach(e -> {
			if (e.getKind() != ElementKind.CLASS) {
				messager.printMessage(Kind.ERROR, "@ID using for class only ", e);
			} else {
				try {
					ID id = e.getAnnotation(ID.class);
					if (ids.keySet().contains(id.id())) {
						Element old = ids.get(id.id());
						messager.printMessage(Kind.ERROR, "Duplicate ID [" + id.id() + "] with class [" + e.getSimpleName() + "]", old);
						messager.printMessage(Kind.ERROR, "Duplicate ID [" + id.id() + "] with class [" + old.getSimpleName() + "]", e);
					} else {
						ids.put(id.id(), e);
						// ok id accepté
					}
				} catch (Exception e1) {
					messager.printMessage(Kind.ERROR, "shit sucks", e);
				}
			}
		});
		return true;
	}

}
