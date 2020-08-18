package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.impl.tag.actor.TableLmlTag;
import com.github.czyzby.lml.parser.impl.tag.actor.provider.TableLmlTagProvider;
import com.github.czyzby.lml.parser.tag.LmlActorBuilder;
import com.github.czyzby.lml.parser.tag.LmlTag;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.util.LapisUtil;
import com.souchy.randd.ebishoal.sapphire.confs.SapphireDevConfig;

public class RootTagProvider extends TableLmlTagProvider {
	
	@Override
	public LmlTag create(LmlParser parser, LmlTag parentTag, StringBuilder rawTagData) {
		var tag = new RootTag(parser, parentTag, rawTagData);
		return tag;
	}
	
	public class RootTag extends TableLmlTag {
		public RootTag(LmlParser parser, LmlTag parentTag, StringBuilder rawTagData) {
			super(parser, parentTag, rawTagData);
			if(SapphireDevConfig.conf.logSapphireWidgets)
				Log.info("attributes : " + String.join(", ", getAttributes()));
			// Set position from tags here since the lmlattribute is bugged
			if(this.getAttribute("x") != null) 
				getActor().setX(Integer.parseInt(this.getAttribute("x")));
			if(this.getAttribute("y") != null) 
				getActor().setY(Integer.parseInt(this.getAttribute("y")));
			// Align the actor on the stage
			LapisUtil.align((SapphireComponent) getActor());
		}
		
		@Override
		protected Actor getNewInstanceOfActor(LmlActorBuilder builder) {
			return SapphireLmlParser.currentView;
		}
	}
	
}
