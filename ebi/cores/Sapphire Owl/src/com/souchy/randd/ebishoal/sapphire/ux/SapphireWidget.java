package com.souchy.randd.ebishoal.sapphire.ux;

import java.lang.reflect.ParameterizedType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.github.czyzby.lml.parser.action.ActorConsumer;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.github.czyzby.lml.parser.impl.tag.AbstractGroupLmlTag;
import com.github.czyzby.lml.parser.tag.LmlActorBuilder;
import com.github.czyzby.lml.parser.tag.LmlTag;
import com.github.czyzby.lml.parser.tag.LmlTagProvider;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireHud;

/**
 * @author Blank
 * @date 22 nov. 2019
 */
public abstract class SapphireWidget extends Group implements ActionContainer {

	public abstract String getTemplateId();
	
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
		public static <T extends Group> T createGroup(String path) {
			var group = (T) SapphireHud.parser.parseTemplate(Gdx.files.internal(path)).first();
			LmlInjector.inject(group);
			return group;
		}
	}
	/**
	 * Injects LML fields 
	 */
	public static class LmlInjector {
		public static <T extends Group> void inject(T group) {
			for(var field : group.getClass().getFields()) {
				try {
					LmlActor ann = field.getAnnotation(LmlActor.class);
					var actorId = ann.value()[0];
					field.set(group, group.findActor(actorId));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static class SapphireWidgetTagProvider<T extends SapphireWidget> implements LmlTagProvider {
		private Class<T> c;
		public SapphireWidgetTagProvider(Class<T> c) {
			this.c = c;
		}
		@Override
		public LmlTag create(LmlParser parser, LmlTag parentTag, StringBuilder rawTagData) {
			return new SapphireWidgetTag<T>(parser, parentTag, rawTagData, c);
		}
	}
	public static class SapphireWidgetTag<T extends SapphireWidget> extends AbstractGroupLmlTag {
		private Class<T> c;
		public SapphireWidgetTag(LmlParser parser, LmlTag parentTag, StringBuilder rawTagData, Class<T> c) {
			super(parser, parentTag, rawTagData);
			this.c = c;
		}
		@Override
		protected T getNewInstanceOfGroup(LmlActorBuilder builder) {
			try {
				return c.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
}
