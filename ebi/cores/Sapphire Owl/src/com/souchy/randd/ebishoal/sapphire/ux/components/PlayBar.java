package com.souchy.randd.ebishoal.sapphire.ux.components;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.VisUI;
import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statusevents.Handler.Reactor;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent;
import com.souchy.randd.commons.diamond.statusevents.other.TurnEndEvent.OnTurnEndHandler;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent.OnTurnStartHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.sapphire.gfx.ui.roundImage.RoundImage;
import com.souchy.randd.ebishoal.sapphire.main.SapphireAssets;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.main.SapphireWorld;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireComponent;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireHudSkin;
import com.souchy.randd.jade.Constants;
import com.souchy.randd.moonstone.white.Moonstone;

public class PlayBar extends SapphireComponent implements Reactor, OnTurnStartHandler {

//	@LmlActor("life")
//	public Label life;

	@LmlActor("pageUp")
	public Button pageUp;

	@LmlActor("pageDown")
	public Button pageDown;

	@LmlActor("pageUpImage")
	public Image pageUpImage;

	@LmlActor("pageDownImage")
	public Image pageDownImage;
	
	@LmlActor("content")
	public Table content;
	
//	@LmlActor({ "spellStack0", "spellStack1", "spellStack2", "spellStack3", "spellStack4", "spellStack5", "spellStack6", "spellStack7" })
//	public List<Stack> stacks;
	
	@LmlActor("playingCreatureAvatarDebug")
	public Label playingCreatureAvatarDebug;
	
//	@LmlActor({ "playingCreatureSpell0Debug", "playingCreatureSpell1Debug", "playingCreatureSpell2Debug", "playingCreatureSpell3Debug", "playingCreatureSpellD4ebug", "playingCreatureSpell5Debug", "playingCreatureSpell6Debug", "playingCreatureSpell7Debug" })
//	public Array<Label> playingCreatureSpellDebug;
	
//	@LmlActor("playingCreatureSpell")
//	public Array<Image> playingCreatureSpell;
	
	@LmlActor("playingCreatureAvatar")
	public Image avatar;

//	@LmlActor("resource")
//	public Array<Label> resources;

	@LmlActor("life;mana;move;special")
	public Array<Label> resources;
	
	
	private Creature playing;
	
	public PlayBar() {
		Moonstone.fight.statusbus.register(this);
	}

	@Override
	public void dispose() {
		this.clear();
		SapphireGame.fight.statusbus.unregister(this);
	}
	
	/**
	 * Active / désactive le mode debug
	 */
	@Override
	public Table debug() {
		super.setDebug(!this.getDebug());
		playingCreatureAvatarDebug.setVisible(this.getDebug());

		for(int i = Constants.numberOfSpells; i < Constants.numberOfSpells; i++) {
			var stack = (Group) content.getChild(i);
			var lblDebug = (Label) stack.findActor("playingCreatureSpell" + i + "Debug");
			lblDebug.setVisible(this.getDebug());
		}
		return this;
	}
	
	@Override
	protected void onInit() {
		createListeners();
//		if(this.getX() == -1) 
//			this.setX(this.getStage().getWidth() / 2 - this.getWidth() / 2);
//		if(this.getY() == -1)
//			this.setY(15);
//		if(playingCreatureSpell == null) {
//			Log.info("Playbar playingCreatureSpell array null");
//			playingCreatureSpell = new Array<>(Constants.numberOfSpells);
//		}
	}

	@Override
	public String getTemplateId() {
		return "playbar";
	}
	
	private void createListeners() {
		var btnShaderListener = new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//				Log.info("enter");
				var light = 1.3f;
				var actor = event.getTarget();
				actor.setColor(light, light, light, actor.getColor().a);
				super.enter(event, x, y, pointer, fromActor);
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//				Log.info("exit");
				var actor = event.getTarget();
				actor.setColor(1, 1, 1, actor.getColor().a);
				super.exit(event, x, y, pointer, toActor);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Log.info("touch down");
				//view.pageUpImage.setDrawable(parser.getData().getDefaultSkin().getDrawable("down"));
				var shade = 0.7f;
				var actor = event.getTarget();
				actor.setColor(shade, shade, shade, actor.getColor().a);
				return super.touchDown(event, x, y, pointer, button);
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Log.info("touch up");
				var light = 1.3f;
				var actor = event.getTarget();
				actor.setColor(light, light, light, actor.getColor().a);
				//view.pageUpImage.setDrawable(parser.getData().getDefaultSkin().getDrawable("up"));
				super.touchUp(event, x, y, pointer, button);
			}
		};
		
		if(pageUp != null) pageUp.addListener(btnShaderListener);
		if(pageDown != null) pageDown.addListener(btnShaderListener);
		
		for(var c : content.getChildren()) {
			c.addListener(btnShaderListener);
		}
	}

	@Override
	public void resizeScreen(int w, int h, boolean centerCam) {
		// TODO Auto-generated method stub
		
	}


	public void refresh() {
		var c = playing;
		int i = 0;
		
		// set creature avatar
		var iconpath = AssetData.creatures.get(playing.modelid).icon;
		var avatarPath = SapphireAssets.getCreatureIconPath(iconpath) + "_round";
		var avatarDrawable = VisUI.getSkin().getDrawable(avatarPath);
		this.avatar.setDrawable(avatarDrawable);
		
		
		// set spell icons
		for (i = 0; i < Constants.numberOfSpells; i++) {
			int s = i < c.spellbook.size() ? c.spellbook.get(i) : -1;
			
			var spell = SapphireGame.fight.spells.get(s);
			var spellResource = spell == null ? null : AssetData.spells.get(spell.modelid());
			var iconPath = spellResource == null ? "" : SapphireAssets.getSpellIconPath(spellResource.icon) + "_round";
			
//			if(spell != null) 
//				Log.info("**** Playbar refresh spell ("+i+") " + spell.modelid()); // SapphireAssets.getI18N_Spells().get("spell." + spell.modelid() + ".name")
//			else
//				Log.info("**** Playbar refresh spell ("+i+") null");
				
			
			var stack = (Group) content.getChild(i);
			var img = (Image) stack.findActor("playingCreatureSpell" + i);
			var lblDebug = (Label) stack.findActor("playingCreatureSpell" + i + "Debug");
			lblDebug.setText(
					(spellResource == null ? "null" : spellResource.icon)
					+ "\n" + 
					(spell == null ? "null" : Constants.simplifiedSpellId(spell.modelid()))
			);
				
//			Log.info("**** Playbar refresh spell (" + i + ") modelid " + (spell == null ? "null" : spell.modelid()) + ", stack " + stack.getName() + " img " + img);
			
			//var img = i >= playingCreatureSpell.size ? null : playingCreatureSpell.get(i);
			if(img != null) {
				//Log.info("**** Playbar refresh spell ("+i+") lml img");
			} else {
				//Log.info("**** Playbar refresh spell ("+i+") lml img null");
				continue;
			}
			
			if(spell != null) 
				img.setDrawable(VisUI.getSkin().getDrawable(iconPath));
			else 
				img.setDrawable(null);
		}
		i = 0;
		
		// set all resources (current, max and shields)
		for (var r : Resource.values()) {
			var txt = c.stats.resources.get(r).value() + " / " + c.stats.resources.get(r).max();
			resources.get(i).setText(txt);
			i++;
			
//			val = c.stats.resources.get(r).value();
//			lml.addArgument(prefix + camel(r.name(), "Current"), val);
//
//			val = c.stats.shield.get(r).value();
//			lml.addArgument(prefix + camel(r.name(), "Current", "Shield"), val);
//			
//			val = c.stats.resources.get(r).max(); 
//			lml.addArgument(prefix + camel(r.name(), "Max"), val);
		}
		i = 0;
		
	}

	

	@Override
	public void onTurnStart(TurnStartEvent e) {
//		Log.format("SapphireGame event fight %s turn %s start %s", e.fight.id, e.turn, e.index);
		playing = SapphireGame.getPlayingCreature();
		if(playing == null) return;
		//if(SapphireGame.gfx.hud != null) {
//			Log.info("PlayBar turn start " + playing.id + " " + SapphireAssets.getI18N_Creatures().get("creature."+playing.modelid+".name")); //.format("creature.{0}.name", playing.modelid));
			//SapphireHudSkin.play(playing);
			//SapphireGame.gfx.hud.playbar.clear();
			//SapphireGame.gfx.hud.playbar = new PlayBar();
			
			//SapphireGame.gfx.hud.playbar.refresh();

			Gdx.app.postRunnable(this::refresh);
		//}
	}
	
	public int getSpellCount() {
		return Constants.numberOfSpells;
	}
	
	
	
}
