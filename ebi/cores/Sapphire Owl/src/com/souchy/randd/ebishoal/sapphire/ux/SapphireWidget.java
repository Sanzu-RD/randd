package com.souchy.randd.ebishoal.sapphire.ux;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.github.czyzby.lml.parser.action.ActorConsumer;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.github.czyzby.lml.parser.impl.tag.AbstractGroupLmlTag;
import com.github.czyzby.lml.parser.impl.tag.actor.ContainerLmlTag;
import com.github.czyzby.lml.parser.impl.tag.actor.TableLmlTag;
import com.github.czyzby.lml.parser.impl.tag.actor.WindowLmlTag;
import com.github.czyzby.lml.parser.impl.tag.actor.provider.ContainerLmlTagProvider;
import com.github.czyzby.lml.parser.impl.tag.actor.provider.TableLmlTagProvider;
import com.github.czyzby.lml.parser.impl.tag.actor.provider.WindowLmlTagProvider;
import com.github.czyzby.lml.parser.impl.tag.builder.TextLmlActorBuilder;
import com.github.czyzby.lml.parser.tag.LmlActorBuilder;
import com.github.czyzby.lml.parser.tag.LmlTag;
import com.github.czyzby.lml.parser.tag.LmlTagProvider;
import com.github.czyzby.lml.util.LmlUtilities;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.util.LapisUtil;
import com.souchy.randd.ebishoal.sapphire.confs.SapphireDevConfig;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireHud;

/**
 * @author Blank
 * @date 22 nov. 2019
 */
public abstract class SapphireWidget extends Table implements ActionContainer {

	public abstract String getTemplateId();
	protected abstract void init();
	
	public SapphireWidget() {
		super(SapphireHud.skin);
	}
	
	public FileHandle getTemplateFile() {
		return Gdx.files.internal("res/ux/sapphire/" + getTemplateId() + ".lml");
	}
	
	public FileHandle getStyleFile() {
		return Gdx.files.internal("res/ux/sapphire/" + getTemplateId() + ".json");
	}
	

	/**
	 * Create widgets
	 */
	public static class LmlWidgets {
		@SuppressWarnings("unchecked")
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
			//Log.info("inject class name : " + group.getClass().getName().toLowerCase());
			//SapphireHud.parser.getData().addActionContainer(group.getClass().getName().toLowerCase(), group);
			for(var field : group.getClass().getDeclaredFields()) {
				try {
					LmlActor ann = field.getAnnotation(LmlActor.class);
					if(ann == null) continue;
					var actorId = ann.value()[0];
					var value = group.findActor(actorId);
					//Log.info("inject field : " + field.getName() + ", value : " + value);
					field.set(group, value);
					
					// inject sub groups
					if(value instanceof SapphireWidget) 
						inject((SapphireWidget) value);
					
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			group.init();
		}
	}
	
	
	public static class SapphireWidgetTagProvider<T extends SapphireWidget> extends TableLmlTagProvider {
		private Class<T> c;
		public SapphireWidgetTagProvider(Class<T> c) {
			this.c = c;
		}
		@Override
		public LmlTag create(LmlParser parser, LmlTag parentTag, StringBuilder rawTagData) {
			var tag = new SapphireWidgetTag(parser, parentTag, rawTagData, c);
			return tag;
		}
		public class SapphireWidgetTag extends TableLmlTag {
			public SapphireWidgetTag(LmlParser parser, LmlTag parentTag, StringBuilder rawTagData, Class<T> c) {
				super(parser, parentTag, rawTagData);
				if(SapphireDevConfig.conf.logSapphireWidgets)
					Log.info("attributes : " + String.join(", ", getAttributes()));
				// Set position from tags here since the lmlattribute is bugged
				if(this.getAttribute("x") != null) 
					getActor().setX(Integer.parseInt(this.getAttribute("x")));
				if(this.getAttribute("y") != null) 
					getActor().setY(Integer.parseInt(this.getAttribute("y")));
				// Align the actor on the stage
				LapisUtil.align((SapphireWidget) getActor());
			}
			@SuppressWarnings("deprecation")
			@Override
			protected T getNewInstanceOfActor(LmlActorBuilder builder) {
				if(SapphireDevConfig.conf.logSapphireWidgets)
					Log.info("create sapphire widget : " + c); // + ", skin : " + SapphireHud.skin);
				T t = null;
				if(c != null) try {
					t = (T) c.newInstance();
					t.setStage(SapphireHud.single.getStage());
					//LmlUtilities.getLmlUserObject(t).initiateStageAttacher(); // Centers the window by default.
				} catch (Exception e) {
					e.printStackTrace();
				}
				return t;
			}
		}
	}

	
	
}
