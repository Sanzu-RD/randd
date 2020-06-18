package com.souchy.randd.tools.uieditor.ui;

import java.lang.reflect.Field;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class PropertiesTab extends Table {
	
	public static PropertiesTab tab;
	
	public PropertiesTab() {
		tab = this;
	}
	
	public static void set(Actor a) {
		tab.clear();
		var fields = a.getClass().getFields();
		for(var f : fields) {
			try {
				Log.info("field " + f.getName() + " = " + f.get(a));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			// colonne pour le nom et la valeur du champs, puis termine la row
			var setter = setter(a, f);
			tab.add(new VisLabel(f.getName()));
			if(setter != null) tab.add(setter);
			tab.row();
		}
		
	}
	
	private static Actor setter(Actor a, Field field) {
		try {
			if(String.class == field.getType()) return new VisTextField(String.valueOf(field.get(a)));
			if(Integer.class == field.getType()) return new VisTextField(String.valueOf(field.get(a)));

			return new VisTextField(String.valueOf(field.get(a)));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
