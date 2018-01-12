package com.souchy.randd.annotationprocessor;

import java.lang.annotation.Annotation;
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
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;

@SupportedAnnotationTypes({ "com.souchy.randd.annotationprocessor.ChildMustAnnotate" })
// @SupportedSourceVersion(SourceVersion.RELEASE_6)
public class ChildMustAnnotateProcessor extends AbstractProcessor {

	private Messager messager;

	private ProcessingEnvironment penv;

	@Override
	public void init(ProcessingEnvironment env) {
		penv = env;
		messager = env.getMessager();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {


		// get all types annotated with ChildMustAnnotate
		Set<? extends Element> parents = env.getElementsAnnotatedWith(ChildMustAnnotate.class);
		Map<Element, Class<? extends Annotation>> childsOfThisMustImplementThat = new HashMap<>();

		// get all values of the ChildMustAnnotate and put the interface + required annotation in the map
		parents.forEach(p -> {
			try {
				ChildMustAnnotate a = p.getAnnotation(ChildMustAnnotate.class);
				Class<? extends Annotation> a2 = a.value();
				childsOfThisMustImplementThat.put(p, a2);
			} catch (MirroredTypeException e) {
				TypeMirror tm = e.getTypeMirror();
				Class<? extends Annotation> c = null;
				try {
					c = (Class<? extends Annotation>) Class.forName(tm.toString());
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				childsOfThisMustImplementThat.put(p, c); 
			}
		});

		// get all root elements, including 1st tier enclosed classes
		Set<Element> elements = new HashSet<>();
		Set<Element> childs = new HashSet<>();
		elements.addAll(env.getRootElements());
		elements.forEach((Element e) -> {
			if (e.getKind() == ElementKind.CLASS) {
				childs.addAll(e.getEnclosedElements());

			}
		});
		elements.addAll(childs);

		// check if elements implement x interface and if they do, check if they are correctly annotated
		elements.forEach(e -> {
			if (e.getKind() == ElementKind.CLASS) {
				childsOfThisMustImplementThat.forEach((p, a) -> {
					// check is the class implements any of the parents
					boolean isChild = penv.getTypeUtils().isAssignable(e.asType(), p.asType());
					// if it does ..
					if (isChild) {
						// check if the class is correctly annotated
						if (a != null && e.getAnnotation(a) == null) { // if (e.getClass().isAnnotationPresent(a) == false) {
							messager.printMessage(Kind.ERROR, "must annotate childs of " + p.getSimpleName() + " with " + a, e);
						} else {
							// ok la classe est bien annotée
						}
					}
				});
			}
		});
		return true;
	}

}
