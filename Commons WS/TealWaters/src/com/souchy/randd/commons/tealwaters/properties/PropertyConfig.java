package com.souchy.randd.commons.tealwaters.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;


/**
 * Implémente cette interface en créant des fields de type Property. ex : Property nameOfAProp; Property nameOfAnotherProp; <p>
 * L'interface se charge d'initialiser les fiedls (initFields()) au moment ou la config est loadée (load())
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
			InputStream in;
			//in = FilesManager.get().getResourceAsStream(getPath());s
			File f = getFile(); //new File(getPath());
			System.out.println("load : " + f.getAbsolutePath());
			
			in = new FileInputStream(f);
			getProperties().load(in); 
			
			initFields();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> propstrings = getProperties().entrySet().stream().map(e -> e.getKey() + " = " + e.getValue()).collect(Collectors.toList());
		System.out.println("load : " + "[" + String.join(", ", propstrings) + "]");
	}
	default void save() {
		try {
			OutputStream out;

			File f = getFile(); //new File(getPath());
			out = new FileOutputStream(f);
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
		for(Field f : this.getClass().getFields()) {
			if(f.getType().isAssignableFrom(Property.class)) {
				try {
					System.out.println("Set property : " + f.getName());
					Supplier<String> get = () -> { 
						if(getProperties().isEmpty()) load();//getProperties(), getPath());
						return getProperties().getProperty(f.getName()); //this.name());
					};
					Consumer<String> set = (setVal) -> { 
						System.out.println("property.set, this.tostring = " + f.getName());
						getProperties().setProperty(f.getName(), setVal);
					};
					f.set(this, new Property(get, set));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
