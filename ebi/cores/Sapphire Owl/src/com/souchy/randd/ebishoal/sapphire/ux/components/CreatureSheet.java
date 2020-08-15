package com.souchy.randd.ebishoal.sapphire.ux.components;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.layout.HorizontalFlowGroup;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.commons.lapis.util.DragAndResizeListener;
import com.souchy.randd.ebishoal.commons.lapis.util.LapisUtil;
import com.souchy.randd.ebishoal.sapphire.gfx.SapphireAssets;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireComponent;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireHud;

import gamemechanics.ext.AssetData;
import gamemechanics.models.Creature;
import gamemechanics.statics.stats.properties.Resource;

public class CreatureSheet extends SapphireComponent {

	public Creature creature;
	
	@LmlActor("closeBtn")
	public Button closeBtn;
	
//	@LmlActor("scrolldesc")
//	public VisScrollPane scrolldesc;
//	@LmlActor("areadesc")
//	public ScrollableTextArea areadesc;
	
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
	
	private static final HashMap<Creature, CreatureSheet> openedSheets = new HashMap<>();
	
	/**
	 * Je pense que toutes les sheets devraient être créées d'avance et juste refreshed on toggle
	 * @param c
	 */
	public static void toggle(Creature c) {
		// si sheet déjà ouverte, delete de la map et du stage
		if(openedSheets.containsKey(c)) {
			openedSheets.get(c).remove();
//			SapphireHud.single.getStage().getActors().removeValue(openedSheets.get(c), true);
			openedSheets.remove(c);
			return;
		}
		// sinon créé la sheet et ajoute à la map et au stage
//		CreatureSheet sheet = LmlWidgets.createGroup(new CreatureSheet().getTemplateFile());
		var sheet = new CreatureSheet();
		sheet.refresh(c);
		sheet.setPosition(Gdx.input.getX(), Gdx.input.getY());
		openedSheets.put(c, sheet);
		SapphireGame.gfx.hud.getStage().addActor(sheet);
	}
	
	
	@Override
	protected void onInit() {
//		creature = SapphireGame.fight.teamA.get(0);

		this.addListener(new DragAndResizeListener(this));
		
		LapisUtil.onClick(closeBtn, this::close);
		
//		scrolldesc.setOverscroll(false, false);
//		scrolldesc.setFlickScroll(false);
//		scrolldesc.setFadeScrollBars(false);
//		scrolldesc.setScrollbarsOnTop(false);
//		scrolldesc.setScrollingDisabled(true, false);
//		scrolldesc.setScrollBarPositions(true, false);

		scrollstatus.setOverscroll(false, false);
		scrollstatus.setFlickScroll(false);
		scrollstatus.setFadeScrollBars(false);
		scrollstatus.setScrollbarsOnTop(false);
		scrollstatus.setScrollingDisabled(true, false);
		scrollstatus.setScrollBarPositions(true, false);

//		areadesc.clearListeners();
		//flowstatus.clearListeners();
		//scrollstatus.clearListeners();
		
		Lambda focusStatus = () -> getStage().setScrollFocus(scrollstatus);
//		Lambda unfocusDesc = () -> {
//			if(!areadesc.hasKeyboardFocus()) getStage().setScrollFocus(null);
//		};
//		Lambda focusDesc = () -> getStage().setScrollFocus(scrolldesc);
		Lambda unfocusStatus = () -> getStage().setScrollFocus(null);
//		LapisUtil.onHover(scrolldesc, focusDesc, unfocusDesc);
		LapisUtil.onHover(scrollstatus, focusStatus, unfocusStatus);
//		LapisUtil.onClick(areadesc, focusDesc);
//		areadesc.addListener(areadesc.new ScrollTextAreaListener() {
//			@Override
//			public boolean keyTyped(InputEvent event, char character) {
//				return true;
//			}
//			@Override
//			public boolean keyDown(InputEvent event, int keycode) {
//				return true;
//			}
//		});

		
		refresh();
	}
	public void refresh(Creature creature) {
		this.creature = creature;
		refresh();
	}
	public void refresh() {
		name.setText(getCreatureName());
//		areadesc.setText(getDescription());
		icon.setDrawable(LapisUtil.getImage(getCreatureModelIcon()));
		
		lifeShield.setText(getLifeShield());
		lifeCurrent.setText(getLifeCurrent());
		lifeMax.setText(getLifeMax());
		
		manaShield.setText(getManaShield());
		manaCurrent.setText(getManaCurrent());
		manaMax.setText(getManaMax());
		
		moveShield.setText(getMoveShield());
		moveCurrent.setText(getMoveCurrent());
		moveMax.setText(getMoveMax());
		
		this.flowstatus.clearChildren();
		if(creature != null)
		creature.statuses.forEach(s -> {
//			var icon = (StatusIcon) LmlWidgets.createGroup("res/ux/sapphire/components/statusicon.lml");
			var icon = new StatusIcon();
			icon.refresh(s);
			this.flowstatus.addActor(icon);
		});
	}

	@Override
	public String getTemplateId() {
		return "creaturesheet";
	}

	@LmlAction("close")
	public void close() {
		try {
			Log.info("stage " + SapphireGame.gfx.hud.getStage());
			//Log.info("actors " + SapphireHud.single.getStage().getActors());
			SapphireGame.gfx.hud.getStage().getActors().removeValue(this, false);
			//this.setVisible(false);
		} catch (Exception e) {
			Log.info("", e);
		}
	}

	@LmlAction("getStatusCount")
	public int getStatusCount() {
		if(creature == null) return 0;
		return creature.statuses.size();
	}

//	@LmlAction("getCreatureModelId")
//	public int getCreatureModelId() {
//		if(creature == null) return 0;
//		return creature.modelid;
//	}

//	@LmlAction("getCreatureModelIcon")
	public String getCreatureModelIcon() { //Object actor) {
		if(creature == null) return "";
		var icon = AssetData.creatures.get(creature.modelid).icon;
		icon = SapphireAssets.getCreatureIconPath(icon);
//		icon = SapphireAssets.getSkinPath(icon);
		Log.info("UI CreatureSheet getCreatureModelIcon " + creature.modelid + " " + icon);
		return icon;
	}

	@LmlAction("getCreatureId")
	public int getCreatureId() {
		if(creature == null) return 0;
		return creature.id;
	}
	
	@LmlAction("getCreatureName")
	public String getCreatureName() {
		if(creature == null) return "null";
		I18NBundle i18n = LapisAssets.assets.get("res/i18n/creatures/bundle", I18NBundle.class);
		var name = i18n.get("creature." + creature.modelid + ".name");
		Log.info("getCreatureName " + creature.modelid + " = " + name);
		return name; 
	}
	@LmlAction("getDescription")
	public String getDescription() {
		if(creature == null) return "null";
		I18NBundle i18n = LapisAssets.assets.get("res/i18n/creatures/bundle", I18NBundle.class);
		return i18n.get("creature."+creature.modelid+".description");
	}

	@LmlAction("getLife")
	public int getLifeCurrent() {
		if(creature == null) return 0;
		return creature.stats.resources.get(Resource.life).value(); //.getResourceCurrent(Resource.life, false);
	}
	@LmlAction("getLifeShield")
	public int getLifeShield() {
		if(creature == null) return 0;
		return creature.stats.shield.get(Resource.life).value(); //getResourceCurrent(Resource.life, true);
	}
	@LmlAction("getLifeMax")
	public int getLifeMax() {
		if(creature == null) return 0;
		return creature.stats.resources.get(Resource.life).max(); //getResourceMax(Resource.life);
	}

	@LmlAction("getMana")
	public int getManaCurrent() {
		if(creature == null) return 0;
		return creature.stats.resources.get(Resource.mana).value(); //getResourceCurrent(Resource.mana, false);
	}
	@LmlAction("getManaShield")
	public int getManaShield() {
		if(creature == null) return 0;
		return creature.stats.shield.get(Resource.mana).value(); //getResourceCurrent(Resource.mana, true);
	}
	@LmlAction("getManaMax")
	public int getManaMax() {
		if(creature == null) return 0;
		return creature.stats.resources.get(Resource.mana).max(); //getResourceMax(Resource.mana);
	}

	@LmlAction("getMove")
	public int getMoveCurrent() {
		if(creature == null) return 0;
		return creature.stats.resources.get(Resource.move).value(); //getResourceCurrent(Resource.move, false);
	}
	@LmlAction("getMoveShield")
	public int getMoveShield() {
		if(creature == null) return 0;
		return creature.stats.shield.get(Resource.move).value(); //getResourceCurrent(Resource.move, true);
	}
	@LmlAction("getMoveMax")
	public int getMoveMax() {
		if(creature == null) return 0;
		return creature.stats.resources.get(Resource.move).max(); //getResourceMax(Resource.move);
	}


	@Override
	public void resizeScreen(int w, int h, boolean centerCam) {
		// TODO Auto-generated method stub
		
	}


	
}
