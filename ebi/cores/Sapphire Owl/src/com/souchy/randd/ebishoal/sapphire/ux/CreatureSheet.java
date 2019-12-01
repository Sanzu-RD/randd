package com.souchy.randd.ebishoal.sapphire.ux;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.github.czyzby.lml.parser.impl.tag.AbstractActorLmlTag;
import com.github.czyzby.lml.parser.impl.tag.AbstractGroupLmlTag;
import com.github.czyzby.lml.parser.impl.tag.AbstractNonParentalActorLmlTag;
import com.github.czyzby.lml.parser.tag.LmlActorBuilder;
import com.github.czyzby.lml.parser.tag.LmlTag;
import com.kotcrab.vis.ui.VisUI;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.util.LapisUtil;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireHud;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireHud.TableDrag;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;

import gamemechanics.models.entities.Creature;
import gamemechanics.statics.stats.properties.Resource;

public class CreatureSheet extends SapphireWidget {

	public Creature creature;

	@LmlActor("name")
	public Label name;
	@LmlActor("icon")
	public Image icon;
	
	@LmlActor("lifeShield")
	public Label lifeShield;
	@LmlActor("lifeCurrent")
	public Label lifeCurrent;
	@LmlActor("lifeMax")
	public Label lifeMax;

	@LmlActor("manaShield")
	public Label manaShield;
	@LmlActor("manaCurrent")
	public Label manaCurrent;
	@LmlActor("manaMax")
	public Label manaMax;

	@LmlActor("moveShield")
	public Label moveShield;
	@LmlActor("moveCurrent")
	public Label moveCurrent;
	@LmlActor("moveMax")
	public Label moveMax;

	//@LmlActor("")
	public Array<StatusIcon> icons;
	
	
	public CreatureSheet() {
		//super(SapphireHud.skin);
		//Log.info("new creature sheet with no skin " + SapphireHud.skin);
	}
	//public CreatureSheet(Skin skin) {
	//	super(skin);
	//	Log.info("new creature sheet with skin " + skin);
		//icons = new ArrayList<>();
	//	creature.getStatus().forEach(s -> icons.add(new StatusIcon().refresh(s)));
		
		
		// inject
	//	SapphireHud.parser.createView(this, getTemplateFile());
	//}
	

	@Override
	protected void init() {
		creature = SapphireGame.fight.teamA.get(0);
		
		var drag = new DragAndDrop();
		drag.addSource(new Source(this) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("Some payload!");

				payload.setDragActor(new Label("Some payload!", SapphireHud.skin));

				Label validLabel = new Label("Some payload!", SapphireHud.skin);
				validLabel.setColor(0, 1, 0, 1);
				payload.setValidDragActor(validLabel);

				Label invalidLabel = new Label("Some payload!", SapphireHud.skin);
				invalidLabel.setColor(1, 0, 0, 1);
				payload.setInvalidDragActor(invalidLabel);

				return payload;
			}
		});
		//drag.addTarget(new TableDrag(SapphireHud.single.getStage()));
		
		refresh();
	}
	
	public void refresh() {
		name.setText(getCreatureName());
		
		lifeShield.setText(getLifeShield());
		lifeCurrent.setText(getLifeCurrent());
		lifeMax.setText(getLifeMax());
		
		manaShield.setText(getManaShield());
		manaCurrent.setText(getManaCurrent());
		manaMax.setText(getManaMax());
		
		moveShield.setText(getMoveShield());
		moveCurrent.setText(getMoveCurrent());
		moveMax.setText(getMoveMax());
	}

	@Override
	public String getTemplateId() {
		return "creaturesheet";
	}

	@LmlAction("close")
	public void close() {
		try {
			Log.info("stage " + SapphireHud.single.getStage());
			//Log.info("actors " + SapphireHud.single.getStage().getActors());
			SapphireHud.single.getStage().getActors().removeValue(this, false);
			this.setVisible(false);
		} catch (Exception e) {
			Log.info("", e);
		}
	}

	@LmlAction("getStatusCount")
	public int getStatusCount() {
		return creature.getStatus().size();
	}

	@LmlAction("getCreatureModelId")
	public int getCreatureModelId() {
		return creature.model.id();
	}

	@LmlAction("getCreatureId")
	public int getCreatureId() {
		return 0; // TODO creature.id();
	}
	
	@LmlAction("getCreatureName")
	public String getCreatureName() {
		return "Sungjin";
	}

	@LmlAction("getLife")
	public int getLifeCurrent() {
		return creature.getStats().getResourceCurrent(Resource.life, false);
	}
	@LmlAction("getLifeShield")
	public int getLifeShield() {
		return creature.getStats().getResourceCurrent(Resource.life, true);
	}
	@LmlAction("getLifeMax")
	public int getLifeMax() {
		return creature.getStats().getResourceMax(Resource.life);
	}

	@LmlAction("getMana")
	public int getManaCurrent() {
		return creature.getStats().getResourceCurrent(Resource.mana, false);
	}
	@LmlAction("getManaShield")
	public int getManaShield() {
		return creature.getStats().getResourceCurrent(Resource.mana, true);
	}
	@LmlAction("getManaMax")
	public int getManaMax() {
		return creature.getStats().getResourceMax(Resource.mana);
	}

	@LmlAction("getMove")
	public int getMoveCurrent() {
		return creature.getStats().getResourceCurrent(Resource.move, false);
	}
	@LmlAction("getMoveShield")
	public int getMoveShield() {
		return creature.getStats().getResourceCurrent(Resource.move, true);
	}
	@LmlAction("getMoveMax")
	public int getMoveMax() {
		return creature.getStats().getResourceMax(Resource.move);
	}
	
}
