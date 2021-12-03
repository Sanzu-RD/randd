package com.souchy.randd.tools.mapeditor.imgui.components;

import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;

import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiTableFlags;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImBoolean;
import imgui.type.ImDouble;
import imgui.type.ImFloat;
import imgui.type.ImInt;
import imgui.type.ImString;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class AutoTable implements ImGuiComponent {

	static class Fiel<T> {
		public String name;
		//public T o;
		public Class<T> type;
		public boolean editable;
		public Supplier<T> getter;
		public Consumer<T> setter;
		public List<Fiel> children = new ArrayList<>();
		
		public Object imv;
	}
	
	
	private Object o;
	private Fiel root;
	
	public AutoTable(Object o) {
		this.o = o;
		this.root = new Fiel();
		this.root.type = null;
		this.root.name = "Root";
		cache(o, root);
	}
	
	/**
	 * 
	 * @param o
	 *            - an object container stuff
	 * @param ass
	 *            - the field associated with the object
	 */
	private void cache(Object o, Fiel ass) {
		if(o == null) return;
		// dont peek into primitives and strings
		if(isPrimitive(o.getClass())) return;
		if(o.getClass() == String.class) return;
		
		// Log.info("Settings cache object " + ass.type + " " + ass.name);
		
		try {
			var fields = o.getClass().getFields();
			var methods = o.getClass().getMethods();
			for (var f : fields) {
				boolean edit = false;
				
				if((f.getModifiers() & Modifier.PUBLIC) > 0) {
					edit = true;
				} else {
					for (var m : methods) {
						if(m.getName().equalsIgnoreCase("set" + f.getName())) {
							edit = true;
							break;
						}
					}
				}
				
				var oc = f.get(o);
				var fc = new Fiel();
				fc.name = f.getName();
				fc.type = oc.getClass();
				// fc.o = f;
				fc.editable = edit;
				
				fc.setter = (v) -> {
					try {
						f.set(o, v);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				};
				fc.getter = () -> {
					try {
						return f.get(o);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
					return null;
				};
				
				if(isPrimitive(fc.type)) {
					if(fc.type == Integer.class) {
						fc.imv = new ImInt(fc.getter.get() == null ? 0 : (int) fc.getter.get());
					}
					if(fc.type == Float.class) {
						fc.imv = new ImFloat(fc.getter.get() == null ? 0 : (float) fc.getter.get());
					}
					if(fc.type == Double.class) {
						fc.imv = new ImDouble(fc.getter.get() == null ? 0 : (double) fc.getter.get());
					}
					if(fc.type == String.class) {
						fc.imv = new ImString(fc.getter.get() == null ? "" : (String) fc.getter.get(), 50);
					}
					if(fc.type == Boolean.class) {
						fc.imv = new ImBoolean(fc.getter.get() == null ? false : (Boolean) fc.getter.get());
					}
				}
				
				// add the child to the parent
				ass.children.add(fc);
				
				// if it's a map or collection, ignore for now
				if(Map.class.isAssignableFrom(fc.type) || AbstractMap.class.isAssignableFrom(fc.type)) {
					
				} else if(Collection.class.isAssignableFrom(fc.type)) {
					// recurse the values in the collection
					// Collection col = (Collection) oc;
					// col.forEach(z -> cache(z));
				} else {
					// recurse the child object's fields
					cache(fc.getter.get(), fc);
				}
			}
		} catch (Exception e) {
			Log.info("AutoTable couldn't cache a field " + o);
		}
	}
	
	public void render(float delta) {
		ImGui.text(" -------- " + o.getClass().getSimpleName() + " -------- ");
//		if(ImGui.beginTable(o.getClass().getSimpleName() + "##AutoTable " + o.hashCode(), 3)) { //, ImGuiTableFlags.ScrollY)) {
//			
//			renderField(root);
//			
//			ImGui.endTable();
//		}
		
		renderField(root);
	}
	
	private void renderField(Fiel f) {
		try {

			if (f.type != null && isPrimitive(f.type)) {
				//if (ImGui.beginTable("##" + f.name + f.hashCode(), 2)) {
					//ImGui.tableNextRow();
					//ImGui.tableSetColumnIndex(0);
					ImGui.text(f.name);
					ImGui.sameLine();
					//ImGui.tableSetColumnIndex(1);
					{
						int flags = f.editable ? ImGuiInputTextFlags.None : ImGuiInputTextFlags.ReadOnly;
						if (f.type == String.class) {
							renderString(f, flags);
						} else if (f.type == Boolean.class) {
							renderBoolean(f, flags);
						} else {
							flags |= ImGuiInputTextFlags.CharsDecimal;
							if (f.type == Integer.class) {
								renderInt(f, flags);
							} else if (f.type == Float.class) {
								renderFloat(f, flags);
							} else if (f.type == Double.class) {
								renderDouble(f, flags);
							}
						}
					}
				//	ImGui.endTable();
					return;
				//}
			} else {
				if (ImGui.treeNodeEx(f.name + "##" + f.hashCode())) { // , ImGuiTreeNodeFlags.DefaultOpen)) {
					for (var c : f.children) {
						if (c != null)
							renderField((Fiel) c);
					}
					ImGui.treePop();
				}
				// ImGui.text("container <");
				// Log.info("" + f.name);
			}
		} catch (Exception e) {
			Log.info("AutoTable can't render a field");
		}

	}
	
	private void renderString(Fiel f, int flags) { 
		var imv = (ImString) f.imv;
		if(ImGui.inputText("##" + f.name + f.hashCode(), imv, flags)) {
			f.setter.accept(imv.get());
		}
	}
	
	private void renderBoolean(Fiel f, int flags) {
		var imv = (ImBoolean) f.imv;
		if(ImGui.checkbox("##" + f.name + f.hashCode(), imv)) {
			f.setter.accept(imv.get());
		}
	}
	
	private void renderInt(Fiel f, int flags) {
		var imv = (ImInt) f.imv;
		if(ImGui.inputInt("##" + f.name + f.hashCode(), imv, flags)) {
			f.setter.accept(imv.get());
		}
	}
	
	private void renderFloat(Fiel f, int flags) {
		var imv = (ImFloat) f.imv;
		if(ImGui.inputFloat("##" + f.name + f.hashCode(), imv, flags)) {
			f.setter.accept(imv.get());
		}
	}
	
	private void renderDouble(Fiel f, int flags) {
		var imv = (ImDouble) f.imv;
		if(ImGui.inputDouble("##" + f.name + f.hashCode(), imv, flags)) {
			f.setter.accept(imv.get());
		}
	}
	
	private static Set<Class<?>> primitiveWrappers = getWrapperTypes();
	private static Set<Class<?>> getWrapperTypes() {
		Set<Class<?>> ret = new HashSet<Class<?>>();
		ret.add(Boolean.class);
		ret.add(Character.class);
		ret.add(Byte.class);
		ret.add(Short.class);
		ret.add(Integer.class);
		ret.add(Long.class);
		ret.add(Float.class);
		ret.add(Double.class);
		ret.add(Void.class);
		ret.add(String.class);
		return ret;
	}
	
	private static boolean isPrimitive(Class c) {
		return c.isPrimitive() || primitiveWrappers.contains(c);
	}
	
	
	
}