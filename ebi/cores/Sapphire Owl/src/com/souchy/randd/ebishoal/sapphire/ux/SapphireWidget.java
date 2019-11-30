package com.souchy.randd.ebishoal.sapphire.ux;

import java.lang.reflect.ParameterizedType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.github.czyzby.lml.parser.action.ActorConsumer;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.github.czyzby.lml.parser.impl.tag.AbstractGroupLmlTag;
import com.github.czyzby.lml.parser.impl.tag.actor.ContainerLmlTag;
import com.github.czyzby.lml.parser.impl.tag.actor.TableLmlTag;
import com.github.czyzby.lml.parser.impl.tag.actor.provider.ContainerLmlTagProvider;
import com.github.czyzby.lml.parser.impl.tag.actor.provider.TableLmlTagProvider;
import com.github.czyzby.lml.parser.tag.LmlActorBuilder;
import com.github.czyzby.lml.parser.tag.LmlTag;
import com.github.czyzby.lml.parser.tag.LmlTagProvider;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireHud;

/**
 * @author Blank
 * @date 22 nov. 2019
 */
public abstract class SapphireWidget extends Table implements ActionContainer {

	public abstract String getTemplateId();
	protected abstract void init();
	
	public FileHandle getTemplateFile() {
		return Gdx.files.internal("res/ux/sapphire/" + getTemplateId() + ".lml");
	}
	
	public FileHandle getStyleFile() {
		return Gdx.files.internal("res/ux/sapphire/" + getTemplateId() + ".json");
	}

	public void setImage(Image img, String imgid) {
		if(img == null) return;
		var drawable = SapphireHud.parser.getData().getDefaultSkin().getDrawable(imgid);
		img.setDrawable(drawable);
	}
	
	public void setText(Label lbl, String text) {
		lbl.setText(text); 
	}
	

	/**
	 * Create widgets
	 */
	public static class LmlWidgets {
		public static <T extends SapphireWidget> T createGroup(String path) {
			var group = (T) SapphireHud.parser.parseTemplate(Gdx.files.internal(path)).first();
			LmlInjector.inject(group);
			return group;
		}
	}
	/**
	 * Injects LML fields 
	 */
	public static class LmlInjector {
		public static <T extends SapphireWidget> void inject(T group) {
			for(var field : group.getClass().getFields()) {
				try {
					Log.info("inject field : " + field.getName());
					LmlActor ann = field.getAnnotation(LmlActor.class);
					if(ann == null) continue;
					var actorId = ann.value()[0];
					var value = group.findActor(actorId);
					field.set(group, value);
					
					// inject sub groups
					if(value instanceof SapphireWidget) 
						inject((SapphireWidget) value);
					
					group.init();
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static class SapphireWidgetTagProvider<T extends SapphireWidget> extends TableLmlTagProvider {
		private Class<T> c;
		public SapphireWidgetTagProvider(Class<T> c) {
			this.c = c;
		}
		@Override
		public LmlTag create(LmlParser parser, LmlTag parentTag, StringBuilder rawTagData) {
			Log.info("SapphireWidgetTagProvider . create " + c);
			return new SapphireWidgetTag(parser, parentTag, rawTagData, c);
		}

		public class SapphireWidgetTag extends TableLmlTag {
			//private Class<T> c;
			public SapphireWidgetTag(LmlParser parser, LmlTag parentTag, StringBuilder rawTagData, Class<T> c) {
				super(parser, parentTag, rawTagData);
				//this.c = c;
				Log.info("SapphireWidgetTag : " + c.descriptorString());
			}
			@Override
			protected T getNewInstanceOfActor(LmlActorBuilder builder) {
				Log.info("create instance of table : " + c);
				try {
					if(c != null)
						return c.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				return null;
			}
		}
	}

	
}
