package com.souchy.randd.commons.tealwaters.properties;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.souchy.randd.commons.tealwaters.logging.Log;

/**
 * Implémente cette interface en créant des fields de type Property. ex :
 * Property nameOfAProp; Property nameOfAnotherProp;
 * <p>
 * L'interface se charge d'initialiser les fiedls (initFields()) au moment ou la
 * config est loadée (load())
 * 
 * @author Souchy
 *
 */
public interface PropertyConfig {

	public File getFile();

	public Properties getProperties();

	default String getDefaultPath() {
		return "res/config.properties";
	}

	default void load() {
		try {
			// in = FilesManager.get().getResourceAsStream(getPath());s
			File f = getFile(); // new File(getPath());
			Log.verbose("load : " + f.getAbsolutePath());

			InputStream in = new FileInputStream(f);
			getProperties().load(in);

			initFields();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> propstrings = getProperties().entrySet().stream().map(e -> e.getKey() + " = " + e.getValue()).collect(Collectors.toList());
		Log.verbose("PropertyConfig load : " + "[" + String.join(", ", propstrings) + "]");
	}

	default void save() {
		try {

			File f = getFile(); // new File(getPath());
			OutputStream out = new FileOutputStream(f);
			System.out.println("save : " + f.getAbsolutePath());

			getProperties().store(out, "");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> propstrings = getProperties().entrySet().stream().map(e -> e.getKey() + " = " + e.getValue()).collect(Collectors.toList());
		System.out.println("save : " + "[" + String.join(", ", propstrings) + "]");
	}

	default void initFields() {
		for (Field f : this.getClass().getFields()) {
			if (f.getType().isAssignableFrom(Property.class)) {
				try {
					//System.out.println("Set property : " + f.getName());
				/*	Supplier<String> get = () -> {
						//if (getProperties().isEmpty())
						//	load();// getProperties(), getPath());
						return getProperties().getProperty(f.getName()); // this.name());
					};
					Consumer<String> set = (setVal) -> {
						//System.out.println("property.set, this.tostring = " + f.getName());
						getProperties().setProperty(f.getName(), setVal);
					};
					
					String value = getProperties().getProperty(f.getName());
					if(value.equalsIgnoreCase(Boolean.TRUE.toString()) || value.equalsIgnoreCase(Boolean.FALSE.toString())) {
						new Property<Boolean>(() -> {
							return value.equalsIgnoreCase(Boolean.FALSE.toString());
						}, 
						(setVal) -> {
							getProperties().setProperty(f.getName(), setVal.toString());
						});
					} else
					if(true) {
						
					}
				 	*/	
					
					ParameterizedType t = (ParameterizedType) f.getGenericType();
					Type gt = t.getActualTypeArguments()[0];
					Class<?> gc = (Class<?>) gt;

					Supplier<String> getVal = () -> getProperties().getProperty(f.getName());
					Consumer<String> setVal = val -> getProperties().setProperty(f.getName(), val.toString());
					/*System.out.println("type : " + type);
					System.out.println("generic type 1 : " + t);
					System.out.println("generic type 2 : " + gt);
					System.out.println("generic class : " + gc);*/
					
					PropertyEditor ac = PropertyEditorManager.findEditor(gc);
					
					Property property = new Property<>(() -> {
							ac.setAsText(getVal.get());
							return ac.getValue();
						}, 
						setVal
					);
					

					f.set(this, property); //new Property(get, set));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	

}
