package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.kotcrab.vis.ui.VisUI;
import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.managers.LapisAssets;
import com.souchy.randd.ebishoal.sapphire.confs.SapphireDevConfig;
import com.souchy.randd.ebishoal.sapphire.gfx.ui.roundImage.RoundTextureRegion;
import com.souchy.randd.ebishoal.sapphire.main.SapphireAssets;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.jade.Constants;
import com.souchy.randd.moonstone.white.Moonstone;

/**
 * 
 * @author Blank
 * @date 29 juill. 2019 (documented, but created before)
 */
public class SapphireHudSkin extends Skin {

	private final LabelStyle styleDmgLife;
	private final LabelStyle styleDmgLifeComposite;
	private final LabelStyle styleDmgLifeShield;
	
	private final LabelStyle styleDmgMana;
	private final LabelStyle styleDmgMove;
	private final LabelStyle styleDmgSpecial;
	
	public static LabelStyle styleNormal;
	public static LabelStyle styleTitle;
	
	public SapphireHudSkin(FileHandle file) {
		super(file);

		// should be color for ressource type and border for shields
		BitmapFont damageFont = LapisAssets.get("gen_damage.ttf");
		styleDmgLife = new LabelStyle(damageFont, Color.WHITE);
		styleDmgLifeComposite = new LabelStyle(damageFont, Color.WHITE);
		styleDmgLifeShield = new LabelStyle(damageFont, Color.WHITE);
		
		styleDmgMana = new LabelStyle(damageFont, Color.CYAN);
		styleDmgMove = new LabelStyle(damageFont, Color.GREEN);
		styleDmgSpecial = new LabelStyle(damageFont, Color.ORANGE);
		
		BitmapFont normalFont = LapisAssets.get("gen_normal.ttf");
		styleNormal = new LabelStyle(normalFont, Color.WHITE);

		BitmapFont titleFont = LapisAssets.get("gen_title.ttf");
		styleTitle = new LabelStyle(titleFont, Color.WHITE);
		
//		add("default", styleNormal);
		add("title", styleTitle);
		add("styleDmgLife", styleDmgLife);
		add("styleDmgLifeComposite", styleDmgLifeComposite);
		add("styleDmgLifeShield", styleDmgLifeShield);
		add("styleDmgMana", styleDmgMana);
		add("styleDmgMove", styleDmgMove);
		add("styleDmgSpecial", styleDmgSpecial);
		
		finishLoading(); // start with default textures
	}
	
	public void finishLoading() {
		add("defaultTexture", LapisAssets.get("res/textures/default.png", Texture.class)); 
//		add("defaultTexture", new Texture(Gdx.files.internal("res/textures/default.png"), true)); 
//		add("defaultTexture", new Texture(Gdx.files.internal("res/textures/default.png"), true)); 
		
		// Link all textures by their name
		LapisAssets.getAssetNames().forEach(a -> {
			if(a.contains("res/textures")) {
				if(SapphireDevConfig.conf.logSkinResources)
					Log.info("hud skin asset : " + a);
				var str = a.substring(a.indexOf("textures"), a.lastIndexOf(".")).replace("/", "."); // enl??ve le res/, enl??ve l'extension, et remplace / par .
				var tex = LapisAssets.get(a, Texture.class);
				add(str, tex);
				add(str + "_round", new TextureRegionDrawable(new RoundTextureRegion(tex)));
			}
//			if(a.startsWith("i18n"))
//				lml.addI18nBundle(a.substring(0, a.lastIndexOf("/")).replace("/", "."), SapphireResources.assets.get(a));
		});
		

		NinePatch patch = new NinePatch(LapisAssets.get("res/textures/borders/border.9.png", Texture.class), 10, 10, 10, 10); // new Texture(Gdx.files.internal("res/textures/borders/border.9.png"))
		NinePatchDrawable background = new NinePatchDrawable(patch);
		add("backgroundBorder", background);
		
		// link all creature/spells/statuses texture icons by their model id and category
		linkModelIcons();
	}


	public static Drawable getIcon(Creature c) {
		return VisUI.getSkin().get("creatureIcon" + c.modelid, Drawable.class);
	}
	public static Drawable getIconRound(Creature c) {
		return VisUI.getSkin().get("creatureIcon" + c.modelid + "_round", Drawable.class);
	}
	public static Drawable getIcon(Spell s) {
		return VisUI.getSkin().get("spellIcon" + s.modelid(), Drawable.class);
	}
	public static Drawable getIcon(Status s) {
		return VisUI.getSkin().get("statusIcon" + s.modelid(), Drawable.class);
	}
	
	
	/**
	 * link all creature/spells/statuses texture icons by their model id and category
	 */
	public void linkModelIcons() {
		for(var c : DiamondModels.creatures.values()) {
			// set creature avatar
			if(!AssetData.creatures.containsKey(c.id())) {
				Log.error("SapphireHudSkin Missing creature icon for model id " + c.id());
				continue;
			}
			var iconpath = AssetData.creatures.get(c.id()).icon;
			add("creatureIcon" + c.id(), this.getDrawable(SapphireAssets.getCreatureIconPath(iconpath)));
			add("creatureIcon" + c.id() + "_round", this.getDrawable(SapphireAssets.getCreatureIconPath(iconpath) + "_round"));
		}
		for(var c : DiamondModels.spells.values()) {
			// set spell icon
			if(!AssetData.spells.containsKey(c.modelid())) {
				Log.error("SapphireHudSkin Missing spell icon for model id " + c.modelid());
				continue;
			}
			var iconpath = AssetData.spells.get(c.modelid()).icon;
			var avatarPath = SapphireAssets.getSpellIconPath(iconpath) + "_round";
			var avatar = this.getDrawable(avatarPath);
			add("spellIcon" + c.modelid(), avatar);
		}
		for(var c : DiamondModels.statuses.values()) {
			// set status icon
			if(!AssetData.statuses.containsKey(c.modelid())) {
				Log.error("SapphireHudSkin Missing status icon for model id " + c.modelid());
				continue;
			}
			var iconpath = AssetData.statuses.get(c.modelid()).icon;
			var avatarPath = SapphireAssets.getStatusIconPath(iconpath) + "_round";
			var avatar = this.getDrawable(avatarPath);
			add("statusIcon" + c.modelid(), avatar);
		}
	}

	/**
	 * When it's your turn on the current playing creature, show spells u can cast
	 */
	public static void play(Creature c) {
		String prefix = "playingCreature";
		set(prefix, c);
	}
	
	/* *
	 * When selecting a creature on the board to show its sheet. Sets all the esources for the selected creature ()
	 */
//	public static void select(Creature c) {
//		String prefix = "selectedCreature";
//		set(prefix, c);
//	}
	
	/**
	 * Take resources from the AssetManager and sets them into the hud skin to be displayed (textures like creature avatar, spell icons and data like resistances)
	 */
	private static void set(String prefix, Creature c) {
		if(c == null) return;
		var lml = SapphireLmlParser.parser.getData();
		var model = c.getModel();
		//int i = 0;
		int val = 0;
		
		try {
			Log.format("SapphireHudSkin playset c [%s/%s] s [%s,%s]", c.id, c.modelid, 
					SapphireGame.fight.spells.get(c.spellbook.get(0)).modelid(), 
					SapphireGame.fight.spells.get(c.spellbook.get(1)).modelid()
			);
		} catch (Exception e) {
			
		}
		
		// set i18n
//		var i18nPath = SapphireResources.getI18nPath(model.getStrID());
//		lml.addI18nBundle(prefix + "I18N", SapphireResources.assets.get(i18nPath));
		
		
		// set creature id / name
		val = c.id;
		lml.addArgument(prefix + camel("id", "current"), val);

		val = c.modelid;
		lml.addArgument(prefix + camel("model", "id", "current"), val);
		

		// set creature avatar
		var avatarName = AssetData.creatures.get(model.id()).icon;
		var avatarPath = SapphireAssets.getCreatureIconPath(avatarName) + "_round";
//		avatarPath = SapphireAssets.getSkinPath(avatarPath) + "_round";
		var avatar = VisUI.getSkin().getDrawable(avatarPath);
		lml.getDefaultSkin().add(prefix + "Avatar", avatar); //new TextureRegionDrawable(new RoundTextureRegion(LapisAssets.get(avatarPath))));
		
		lml.addArgument(prefix + camel("icon", "current"), SapphireAssets.getCreatureIconPath(avatarName));
		
		//Log.info("c.spellbook : " + c.spellbook);

		// set spell icons
		for(int i = 0; i < Constants.numberOfSpells; i++) {
			//lml.getDefaultSkin().remove(prefix + "Spell" + i, Drawable.class);
			lml.getDefaultSkin().remove(prefix + "Spell" + i, TextureRegionDrawable.class);
			lml.removeArgument(prefix + "Spell" + i);
			
			int s = i < c.spellbook.size() ? c.spellbook.get(i) : -1;

			String spellPath = "missing";
			var spell = SapphireGame.fight.spells.get(s);
			var spellData = spell == null ? null : AssetData.spells.get(spell.modelid()); 
			
			lml.addArgument(prefix + "Spell" + i + "b", spellData != null);
			if (spellData != null) {
				spellPath = SapphireAssets.getSpellIconPath(spellData.icon) + "_round";
//				iconPath = SapphireAssets.getSkinPath(iconPath) + "_round";
				var img = VisUI.getSkin().getDrawable(spellPath);
				Log.info("SapphireHudSkin spell ["+i+"]="+s+" icon : " + spellData.icon + " -> " + spellPath + " -> " + img + " ------ " + prefix + "Spell" + i);
				
				lml.getDefaultSkin().add(prefix + "Spell" + i, img);
				lml.addArgument(prefix + "Spell" + i, spellData.icon + "\n" + Constants.simplifiedSpellId(spell.modelid()));
				
			} else {
				//Log.critical("SapphireHudSkin ---------------------");
				Log.info("SapphireHudSkin spell ["+i+"]="+s+" icon : " + spellData + " -> " + spellPath + " -> " + null +  " ------ " + prefix + "Spell" + i);
				//lml.getDefaultSkin().add(prefix + "Spell" + i, VisUI.getSkin().getDrawable(iconPath));
				//lml.addArgument(prefix + "Spell" + i, "null");
				//lml.getDefaultSkin().add(prefix + "Spell" + i, null);
				//lml.getDefaultSkin().get("", Drawable.class);
				var img = VisUI.getSkin().getDrawable(spellPath);
				lml.getDefaultSkin().add(prefix + "Spell" + i, img);
				if(spell != null) {
					Log.critical("SapphireHudSkin does this ever happen");
					lml.addArgument(prefix + "Spell" + i, Constants.simplifiedSpellId(spell.modelid()));
				}
				//lml.getDefaultSkin().remove(prefix + "Spell" + i, Drawable.class);
			}
		}
		
		// set all resources (current, max and shields)
		for (var r : Resource.values()) {
			val = c.stats.resources.get(r).value();
			lml.addArgument(prefix + camel(r.name(), "Current"), val);

			val = c.stats.shield.get(r).value();
			lml.addArgument(prefix + camel(r.name(), "Current", "Shield"), val);
			
			val = c.stats.resources.get(r).max(); 
			lml.addArgument(prefix + camel(r.name(), "Max"), val);
		}
		
		// set all resistances
		for (var ele : Element.values) {
			var res = c.stats.resistance.get(ele);
			val = (int) res.baseflat; 
			lml.addArgument(prefix + camel(ele.name(), "Resistance", "flat"), val);
			
			val = (int) res.inc; 
			lml.addArgument(prefix + camel(ele.name(), "Resistance", "scl"), val);
			
			val = (int) res.more; 
			lml.addArgument(prefix + camel(ele.name(), "Resistance", "more"), val);
		}
		
		// log
		if(SapphireDevConfig.conf.logSkinResources)
			lml.getDefaultSkin().getAll(TextureRegionDrawable.class).keys().forEach(s -> Log.info("TextureRegionDrawable : " + s));
		if(SapphireDevConfig.conf.logSkinResources) 
			lml.getDefaultSkin().getAll(Texture.class).keys().forEach(s -> Log.info("Texture : " + s));
		
		// refresh hud
//		SapphireGame.gfx.hud.reload(); // SapphireHud.refresh();
	}

	private static String camel(String... args) {
		String tot = "";
		for (var s : args) {
			if(s.length() > 0) {
				tot += s.toUpperCase().charAt(0);
			}
			if(s.length() > 1) {
				tot += s.toLowerCase().substring(1);
			}
		}
		return tot;
	}
	
	
	/**
	 * Override to return a default texture if the requested one doesnt exist
	 */
	@Override
	public Drawable getDrawable(String name) {
		//Log.info("SapphireHudSkin get : " + name);
		if(name == null) return getDrawable("defaultTexture");
		
		Drawable drawable = optional(name, Drawable.class);
		if(drawable != null) return drawable;
		
		drawable = optional(name, TextureRegionDrawable.class);
		if(drawable != null) return drawable;
		
		// Use texture or texture region. If it has splits, use ninepatch. If it has
		// rotation or whitespace stripping, use sprite.
		try {
			TextureRegion textureRegion = getRegion(name);
			if(textureRegion instanceof AtlasRegion) {
				AtlasRegion region = (AtlasRegion) textureRegion;
				if(region.splits != null) 
					drawable = new NinePatchDrawable(getPatch(name));
				else if(region.rotate || region.packedWidth != region.originalWidth || region.packedHeight != region.originalHeight)
					drawable = new SpriteDrawable(getSprite(name));
			}
			if(drawable == null) 
				drawable = new TextureRegionDrawable(textureRegion);
		} catch (GdxRuntimeException ignored) {
		}
		
		// Check for explicit registration of ninepatch, sprite, or tiled drawable.
		if(drawable == null) {
			NinePatch patch = optional(name, NinePatch.class);
			if(patch != null) 
				drawable = new NinePatchDrawable(patch);
			else {
				Sprite sprite = optional(name, Sprite.class);
				if(sprite != null) 
					drawable = new SpriteDrawable(sprite);
				else {
					return getDrawable("defaultTexture");
					//throw new GdxRuntimeException("No Drawable, NinePatch, TextureRegion, Texture, or Sprite registered with name: " + name);
				}
			}
		}
		
		if(drawable instanceof BaseDrawable) ((BaseDrawable) drawable).setName(name);
		
		add(name, drawable, Drawable.class);
		return drawable;
	}
	
}
