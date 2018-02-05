package com.souchy.randd.annotationprocessor;

import static com.souchy.randd.annotationprocessor.ReflectiveSearch.getRecursiveEnclosedElementSearch;

import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

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

		messager.printMessage(Kind.NOTE, "====================================");
		messager.printMessage(Kind.NOTE, "ChildMustAnnotate 5 start build in ["+fetchSourcePath()+"] ======");

		//messager.printMessage(Kind.NOTE, "------------------------------------ Parent Elements");
		messager.printMessage(Kind.NOTE, "------------------------------------ Module Elements");
		
		// get all direct types annotated with ChildMustAnnotate
		Set<Element> parents = new HashSet<>(); // env.getElementsAnnotatedWith(ChildMustAnnotate.class)
		
		Set<? extends ModuleElement> moduleElements = penv.getElementUtils().getAllModuleElements();
		moduleElements.forEach(m -> {
			if(m.getSimpleName().toString().toLowerCase().contains("com.souchy") || m.getSimpleName().toString().toLowerCase().contains("com.hiddenpiranha")) { //!m.getSimpleName().toString().toLowerCase().contains("java") && !m.getSimpleName().toString().toLowerCase().contains("jdk")) {// && !m.toString().startsWith("oracle")) {
				//messager.printMessage(Kind.NOTE, "Module Element ["+m.getSimpleName()+"]");
				//if(m.getSimpleName().toString().toLowerCase().contains("com.souchy") || m.getSimpleName().toString().toLowerCase().contains("com.hiddenpiranha")) {
					List<Element> ele = new ArrayList<>();
					getRecursiveEnclosedElementSearch(m, ele);
					ele.stream().filter(e -> e.getAnnotation(ChildMustAnnotate.class) != null).forEach(e -> {
						messager.printMessage(Kind.NOTE, "Found childmustannotate ["+e.getSimpleName()+"] in module ["+m.getSimpleName()+"]");
						parents.add(e);
					});
				//}
			}
		});

		// get all values of the ChildMustAnnotate and put the interface + required annotation in the map
		Map<Element, Class<? extends Annotation>> childsOfThisMustImplementThat = new HashMap<>();
		parents.forEach(p -> {
			Class<? extends Annotation> c = null;
			try {
				ChildMustAnnotate a = p.getAnnotation(ChildMustAnnotate.class);
				c = a.value();
				childsOfThisMustImplementThat.put(p, c);
			} catch (MirroredTypeException e) {
				TypeMirror tm = e.getTypeMirror();
				try {
					c = (Class<? extends Annotation>) Class.forName(tm.toString());
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				childsOfThisMustImplementThat.put(p, c); 
			}
			messager.printMessage(Kind.NOTE, "ChildMustAnnotate put type ["+p+"] must be annotated with ["+c+"]");
		});

		
		messager.printMessage(Kind.NOTE, "------------------------------------ Root elements");

		
		// get all root elements, including 1st tier enclosed classes
		Set<Element> elements = new HashSet<>();
		Set<Element> childs = new HashSet<>();
		elements.addAll(env.getRootElements());
		elements.forEach((Element e) -> {
			
			List<Element> ele = new ArrayList<>();
			getRecursiveEnclosedElementSearch(e, ele, e1 -> {
				return e1.getKind() == ElementKind.CLASS;// || e1.getKind() == ElementKind.ENUM;
			}, e2 -> true);
			boolean b = ele.removeIf(e1 -> e1.getKind() != ElementKind.CLASS);// && e1.getKind() != ElementKind.ENUM);
			if(b) messager.printMessage(Kind.WARNING, "remove some shit");
			childs.addAll(ele);
			//if (e.getKind() == ElementKind.CLASS) {
			//	childs.addAll(e.getEnclosedElements());
			//}
			
			/*List<Class<?>> parents1 = new ArrayList<>();
			try {
				Class<?> child = Class.forName(e.toString());
				getRecursiveInterfaceSearch(child, parents1);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
				messager.printMessage(Kind.NOTE, "Error cant find class. "+e1.getMessage());
			}*/
			
			//parents1.forEach(p -> messager.printMessage(Kind.NOTE, "\t |- Parent : ["+p.getSimpleName()+"], is annotated ["+(p.getAnnotation(ChildMustAnnotate.class) != null)+"]"));
		});
		elements.addAll(childs);
		elements.forEach(e -> {
			ChildMustAnnotate a = e.getAnnotation(ChildMustAnnotate.class);
			messager.printMessage(Kind.NOTE, "Root element ["+e+"] is annotated? ["+(a != null)+"]");
		});

		messager.printMessage(Kind.NOTE, "------------------------------------ Child Elements");
		
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
							messager.printMessage(Kind.ERROR, "must annotate children of " + p + " with " + a, e);
						} else {
							// ok la classe est bien annotée
						}
					}
				});
			}
		});

		messager.printMessage(Kind.NOTE, "====================================");
		messager.printMessage(Kind.NOTE, "ChildMustAnnotate end build ==================");
		messager.printMessage(Kind.NOTE, "====================================");
		return true;
	}
	
	
	private String fetchSourcePath() {
	    try {
	        JavaFileObject generationForPath = penv.getFiler().createSourceFile("PathFor" + getClass().getSimpleName());
	        Writer writer = generationForPath.openWriter();
	        String sourcePath = generationForPath.toUri().getPath();
	        writer.close();
	        generationForPath.delete();
	        return sourcePath;
	    } catch (Exception e) {
	    	penv.getMessager().printMessage(Kind.WARNING, "Unable to determine source file path!");
	    }
	    return "";
	}

}
