package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.layout.HorizontalFlowGroup;
import com.kotcrab.vis.ui.widget.ScrollableTextArea;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.util.LapisUtil;
import com.souchy.randd.ebishoal.sapphire.controls.DragAndResizeListener;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireHud;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;

import gamemechanics.models.entities.Creature;
import gamemechanics.statics.stats.properties.Resource;

public class CreatureSheet extends SapphireWidget {

	public Creature creature;
	
	@LmlActor("closeBtn")
	public Button closeBtn;
	
	@LmlActor("scrolldesc")
	public VisScrollPane scrolldesc;
	@LmlActor("areadesc")
	public ScrollableTextArea areadesc;
	
	@LmlActor("scrollstatus")
	public VisScrollPane scrollstatus;
	@LmlActor("flowstatus")
	public HorizontalFlowGroup flowstatus;

	
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
	
	
	@Override
	protected void init() {
		creature = SapphireGame.fight.teamA.get(0);

		this.addListener(new DragAndResizeListener(this));
		
		LapisUtil.onClick(closeBtn, this::close);
		
		scrolldesc.setOverscroll(false, false);
		scrolldesc.setFlickScroll(false);
		scrolldesc.setFadeScrollBars(false);
		scrolldesc.setScrollbarsOnTop(false);
		scrolldesc.setScrollingDisabled(true, false);
		scrolldesc.setScrollBarPositions(true, false);

		scrollstatus.setOverscroll(false, false);
		scrollstatus.setFlickScroll(false);
		scrollstatus.setFadeScrollBars(false);
		scrollstatus.setScrollbarsOnTop(false);
		scrollstatus.setScrollingDisabled(true, false);
		scrollstatus.setScrollBarPositions(true, false);

		areadesc.clearListeners();
		//flowstatus.clearListeners();
		//scrollstatus.clearListeners();
		
		Lambda focusStatus = () -> getStage().setScrollFocus(scrollstatus);
		Lambda unfocusDesc = () -> {
			if(!areadesc.hasKeyboardFocus()) getStage().setScrollFocus(null);
		};
		Lambda focusDesc = () -> getStage().setScrollFocus(scrolldesc);
		Lambda unfocusStatus = () -> getStage().setScrollFocus(null);
		LapisUtil.onHover(scrolldesc, focusDesc, unfocusDesc);
		LapisUtil.onHover(scrollstatus, focusStatus, unfocusStatus);
		LapisUtil.onClick(areadesc, focusDesc);
		areadesc.addListener(areadesc.new ScrollTextAreaListener() {
			@Override
			public boolean keyTyped(InputEvent event, char character) {
				return true;
			}
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				return true;
			}
		});
		

		
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
			//this.setVisible(false);
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
		return creature.getStats().resources.get(Resource.life).value(); //.getResourceCurrent(Resource.life, false);
	}
	@LmlAction("getLifeShield")
	public int getLifeShield() {
		return creature.getStats().shield.get(Resource.life).value(); //getResourceCurrent(Resource.life, true);
	}
	@LmlAction("getLifeMax")
	public int getLifeMax() {
		return creature.getStats().resources.get(Resource.life).max(); //getResourceMax(Resource.life);
	}

	@LmlAction("getMana")
	public int getManaCurrent() {
		return creature.getStats().resources.get(Resource.mana).value(); //getResourceCurrent(Resource.mana, false);
	}
	@LmlAction("getManaShield")
	public int getManaShield() {
		return creature.getStats().shield.get(Resource.mana).value(); //getResourceCurrent(Resource.mana, true);
	}
	@LmlAction("getManaMax")
	public int getManaMax() {
		return creature.getStats().resources.get(Resource.mana).max(); //getResourceMax(Resource.mana);
	}

	@LmlAction("getMove")
	public int getMoveCurrent() {
		return creature.getStats().resources.get(Resource.move).value(); //getResourceCurrent(Resource.move, false);
	}
	@LmlAction("getMoveShield")
	public int getMoveShield() {
		return creature.getStats().shield.get(Resource.move).value(); //getResourceCurrent(Resource.move, true);
	}
	@LmlAction("getMoveMax")
	public int getMoveMax() {
		return creature.getStats().resources.get(Resource.move).max(); //getResourceMax(Resource.move);
	}
	
}
