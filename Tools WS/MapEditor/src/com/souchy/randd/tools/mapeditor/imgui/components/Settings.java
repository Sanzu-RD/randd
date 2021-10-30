package com.souchy.randd.tools.mapeditor.imgui.components;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.*;

import com.badlogic.gdx.Gdx;
import com.google.gson.internal.LinkedTreeMap;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent;
import com.souchy.randd.tools.mapeditor.imgui.ImGuiComponent.Container;
import com.souchy.randd.tools.mapeditor.main.MapEditorCore;

import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiTableFlags;
import imgui.type.ImBoolean;
import imgui.type.ImDouble;
import imgui.type.ImFloat;
import imgui.type.ImInt;
import imgui.type.ImString;

public class Settings extends Container {
	
	private AutoTable tableGfx;
	private AutoTable tableHud;
	
	public MaterialProperties props = new MaterialProperties();
	
	public Settings() {
		this.title = "Settings";
		
		tableGfx = new AutoTable(MapEditorCore.conf.gfx);
		tableHud = new AutoTable(MapEditorCore.conf.hud);
	}
	
	@Override
	protected void applyDefaults() {
		size[0] = 800;
		size[1] = 700;
		position[0] = Gdx.graphics.getWidth() / 2 - size[0] / 2;
		position[1] = Gdx.graphics.getHeight() / 2 - size[1] / 2;
	}

	
	@Override
	public void renderContent(float delta) {
		if(ImGui.beginTabBar("settings tabs")) {
			if(ImGui.beginTabItem("Material")) {
				props.render(delta);
				ImGui.endTabItem();
			}
			
			if(ImGui.beginTabItem("Graphics")) {
				tableGfx.render(delta);
				ImGui.endTabItem();
			}
			if(ImGui.beginTabItem("Hud")) {
				tableHud.render(delta);
				ImGui.endTabItem();
			}
			ImGui.endTabBar();
		}
	}
	

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static class AutoTable implements ImGuiComponent {
		private Object o;
		private Fiel root;
		public AutoTable(Object o) {
			this.o = o;
			this.root = new Fiel();
			this.root.type = null;
			this.root.name = "Root";
			cache(o, root);
		}
		private int iter = 0;
		/**
		 * 
		 * @param o - an object container stuff
		 * @param ass - the field associated with the object
		 */
		private void cache(Object o, Fiel ass) {
			iter++;
			//if(iter > 10) return;
			if(o == null) return;
			// dont peek into primitives and strings
			if(isPrimitive(o.getClass())) return;
			if(o.getClass() == String.class) return;
			
			Log.info("Settings cache object " + ass.type + " " + ass.name);
			
			try {
				var fields = o.getClass().getFields();
				var methods = o.getClass().getMethods();
				for(var f : fields) {
					boolean edit = false;
					
					if((f.getModifiers() & Modifier.PUBLIC) > 0) {
						edit = true;
					} else {
						for(var m : methods) {
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
					//fc.o = f;
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
							fc.imv = new ImString(fc.getter.get() == null ? "" : (String) fc.getter.get());
						}
						if(fc.type == Boolean.class) {
							fc.imv = new ImBoolean(fc.getter.get() == null ? false : (Boolean) fc.getter.get());
						}
					} 
					
					// add the child to the parent
					ass.children.add(fc);
					
					// if it's a map or collection, ignore for now
					if(Map.class.isAssignableFrom(fc.type) || AbstractMap.class.isAssignableFrom(fc.type)) {
						
					} else
					if(Collection.class.isAssignableFrom(fc.type)) {
						// recurse the values in the collection
						//Collection col = (Collection) oc;
						//col.forEach(z -> cache(z));
					} else {
						// recurse the child object's fields
						cache(fc.getter.get(), fc);
					}
				}
			} catch(Exception e) {
				Log.info("AutoTable couldn't cache a field " + o);
			}
		}
		

		
		public void render(float delta) {
			if(ImGui.beginTable(o.getClass().getSimpleName(), 3, ImGuiTableFlags.ScrollY)) {
//				ImGui.nextColumn();
//				ImGui.setColumnWidth(0, 80);

				renderField(root);
				
//				ImGui.tableNextRow();
//				for(int i = 0; i < 2; i++) {
//					ImGui.tableSetColumnIndex(i);
//				    ImGui.text("wtf");
//				}
				
				ImGui.endTable();
			}
		}
		
		private void renderField(Fiel f) {
			ImGui.tableNextRow();
			try {
				if(f.type != null && isPrimitive(f.type)) {
//					ImGui.beginGroup();
					
					ImGui.tableSetColumnIndex(0);
//					ImGui.nextColumn();
//					ImGui.nextColumn();
					ImGui.text(f.name);
//					ImGui.sameLine();

//					ImGui.separator();
					ImGui.tableSetColumnIndex(1);
//					ImGui.nextColumn();
					{
						int flags = f.editable ? ImGuiInputTextFlags.None : ImGuiInputTextFlags.ReadOnly;
						if(f.type == String.class) {
							renderString(f, flags);
						}  else
						if(f.type == Boolean.class) {
							renderBoolean(f, flags);
						} else {
							flags |= ImGuiInputTextFlags.CharsDecimal;
							if(f.type == Integer.class) {
								renderInt(f, flags);
							} else
							if(f.type == Float.class) {
								renderFloat(f, flags);
							} else
							if(f.type == Double.class) {
								renderDouble(f, flags);
							}
						}
						//ImGui.text("what is this " + f.name);
					}
//					ImGui.endGroup();
//					ImGui.separator();
					return;
				} else {
					ImGui.text("container <");
				}

				for(var c : f.children) {
					if(c != null)
						renderField((Fiel) c);
				}
			} catch(Exception e) {
				Log.info("AutoTable can't render a field");
			}
		}
		
		private void renderString(Fiel f, int flags) { //Object inst, Field f, boolean edit) {
			//String s = (String) f.getter.get(); //f.get(inst);
			//ImGui.text(s);
			var imv = (ImString) f.imv;
			if(ImGui.inputText("", imv, flags)) {
				f.setter.accept(imv.get());
			}
		}
		private void renderBoolean(Fiel f, int flags) {
			var imv = (ImBoolean) f.imv;
			if(ImGui.checkbox("", imv)) {
				f.setter.accept(imv.get());
			}
		}
		private void renderInt(Fiel f, int flags) {
			var imv = (ImInt) f.imv;
			if(ImGui.inputInt("", imv, flags)) {
				f.setter.accept(imv.get());
			}
		}
		private void renderFloat(Fiel f, int flags) {
			var imv = (ImFloat) f.imv;
			if(ImGui.inputFloat("", imv, flags)) {
				f.setter.accept(imv.get());
			}
		}
		private void renderDouble(Fiel f, int flags) {
			var imv = (ImDouble) f.imv;
			if(ImGui.inputDouble("", imv, flags)) {
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
	
	
	private static class Fiel<T> {
		public String name;
		//public T o;
		public Class<T> type;
		public boolean editable;
		public Supplier<T> getter;
		public Consumer<T> setter;
		public List<Fiel> children = new ArrayList<>();
		
		public Object imv;
	}
	
	
	
}
