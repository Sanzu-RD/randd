package com.souchy.randd.ebishoal.sapphire.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisResources;
import com.souchy.randd.ebishoal.sapphire.confs.SapphireDevConfig;
import com.souchy.randd.ebishoal.sapphire.gfx.ui.roundImage.RoundTextureRegion;
import com.souchy.randd.ebishoal.sapphire.main.SapphireResources;

import gamemechanics.models.entities.Creature;
import gamemechanics.statics.Element;
import gamemechanics.statics.stats.modifiers.eleMod;
import gamemechanics.statics.stats.modifiers.mathMod;
import gamemechanics.statics.stats.properties.Resource;

/**
 * 
 * @author Blank
 * @date 29 juill. 2019 (documented, but created before)
 */
public class SapphireHudSkin extends Skin {
	
	public SapphireHudSkin(FileHandle file) {
		super(file);

		add("defaultTexture", new Texture(Gdx.files.absolute("G:/Assets/test/default.png"), true));
		
		//this.getAll(Texture.class).values().forEach(t -> t.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear));

		// Link all textures
		LapisResources.assets.getAssetNames().forEach(a -> {
			if(SapphireDevConfig.conf.logSkinResources)
				Log.info("hud skin asset : " + a);
			if(a.contains("res/textures")) {
				var str = a.substring(a.indexOf("textures"), a.lastIndexOf(".")).replace("/", "."); // enlève le res/, enlève l'extension, et remplace / par .
				add(str, LapisResources.assets.get(a));
			}
//			if(a.startsWith("i18n"))
//				lml.addI18nBundle(a.substring(0, a.lastIndexOf("/")).replace("/", "."), SapphireResources.assets.get(a));
		});
	}
	

	/**
	 * When it's your turn on the current playing creature, show spells u can cast
	 */
	public static void play(Creature c) {
		String prefix = "playingCreature";
		set(prefix, c);
	}
	
	/**
	 * When selecting a creature on the board to show its sheet. Sets all the esources for the selected creature ()
	 */
	public static void select(Creature c) {
		String prefix = "selectedCreature";
		set(prefix, c);
	}
	
	/**
	 * Take resources from the AssetManager and sets them into the hud skin to be displayed (textures like creature avatar, spell icons and data like resistances)
	 */
	private static void set(String prefix, Creature c) {
		var lml = SapphireHud.parser.getData();
		var model = c.model;
		int i = 0;
		int val = 0;
		
		// set i18n
//		var i18nPath = SapphireResources.getI18nPath(model.getStrID());
//		lml.addI18nBundle(prefix + "I18N", SapphireResources.assets.get(i18nPath));
		
		// set creature avatar
		var avatarPath = SapphireResources.getCreatureIconPath(model.getIconName());
		lml.getDefaultSkin().add(prefix + "Avatar", new TextureRegionDrawable(new RoundTextureRegion(LapisResources.get(avatarPath))));
		
		//Log.info("c.spellbook : " + c.spellbook);
		// set spell icons
		
		for(var s : c.spellbook) {
			//int typeid = s.id() / 1000000 * 1000000;
			//int creatureid = s.id() / 1000 * 1000;
			//int sid = s.id() - typeid - creatureid;
			//String iconPath = "";
			//Log.info("SapphireHudSkin spell ids : " + typeid + ", " + creatureid + ", " + sid);
			//if(typeid > 0) {
				//iconPath = SapphireResources.getCreatureTypeGfx(CreatureType.getName(typeid), s.getIconName());
				//Log.info("SapphireHudSkin spell asset path (creature type) : " + iconPath + ". For creature type : " + CreatureType.values()[typeid].name() + ". For Spell : " + s.getIconName());
			//} else 
			//if (creatureid > 0) {
				String iconPath = SapphireResources.getSpellIconPath(s.getIconName()); //model.getStrID(), 
				//var str = iconPath.substring("res/".length(), iconPath.lastIndexOf(".")).replace("/", ".");
				//Log.info("SapphireHudSkin spell asset path (creature) : " + iconPath + ". For creature : " + model.getStrID() + ". For Spell : " + s.getIconName());
			//}
			lml.getDefaultSkin().add(prefix + "Spell" + i, LapisResources.assets.get(iconPath));
			i++;
		}
		
		
		// set all resources (current, max and shields)
		for (var r : Resource.values()) {
			val = c.getStats().resources.get(r).value();
			lml.addArgument(prefix + camel(r.name(), "Current"), val);

			val = c.getStats().shield.get(r).value();
			lml.addArgument(prefix + camel(r.name(), "Current", "Shield"), val);
			
			val = c.getStats().resources.get(r).max(); 
			lml.addArgument(prefix + camel(r.name()) + "Max", val);
			
//			c.getStats().getResourceMax(r);
		}
		
		// set all resistances
		for (var ele : Element.values) {
			//for (var m : mathMod.values()) {
				var res = c.getStats().resistance.get(ele);
				val = (int) res.baseflat; //.getEle(e, m, eleMod.res);
				lml.addArgument(prefix + camel(ele.name(), "Resistance", "flat"), val); // m.name()), val);

				val = (int) res.inc; //.getEle(e, m, eleMod.res);
				lml.addArgument(prefix + camel(ele.name(), "Resistance", "scl"), val); // m.name()), val);

				val = (int) res.more; //.getEle(e, m, eleMod.res);
				lml.addArgument(prefix + camel(ele.name(), "Resistance", "more"), val); // m.name()), val);
			//}
		}
		
		try {
			if(SapphireDevConfig.conf.logSkinResources)
				lml.getDefaultSkin().getAll(TextureRegionDrawable.class).keys().forEach(s -> Log.info("TextureRegionDrawable : " + s));
		} catch (Exception e) {
			Log.error("", e);
		}
		try {
			if(SapphireDevConfig.conf.logSkinResources)
				lml.getDefaultSkin().getAll(Texture.class).keys().forEach(s -> Log.info("Texture : " + s));
		} catch (Exception e) {
			Log.error("", e);
		}

//		try {
//			//textures.forEach(t -> t.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear));
//			lml.getDefaultSkin().getAll(Texture.class).values().forEach(t -> 
//				t.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear)
//			);
//			lml.getDefaultSkin().getAll(TextureRegionDrawable.class).values().forEach(t -> 
//				t.getRegion().getTexture().setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear)
//			);
//		} catch(Exception e) {
//			Log.error("", e);
//		}
		
		SapphireHud.refresh();
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
