package data;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;
import com.souchy.randd.commons.tealwaters.commons.Namespace;
import com.souchy.randd.commons.tealwaters.commons.Namespace.I18NNamespace;
import com.souchy.randd.jade.combat.JadeCreature;

import gamemechanics.creatures.CreatureType;
import gamemechanics.models.entities.Creature;
import gamemechanics.properties.Properties;
import gamemechanics.stats.StatModifier.BasicMod;

/**
 * Creature basic data : spell data, creature types, creature colors, base stats, base properties<br>
 * 
 * The Ebi version also contains : textures, i18n, default model file
 * 
 * @author Blank
 *
 */
public abstract class CreatureData implements Identifiable<Integer> {
	
	
	public static final I18NNamespace creatures = new I18NNamespace("Creatures");
	

	/**
	 * Creature colors
	 */
	public final ImmutableList<CreatureType> colors;
	/**
	 * Creature types
	 */
	public final ImmutableList<CreatureType> types;
	/**
	 * List of all spells this creature can learn (spells specific to this creature and more) 
	 */
	public final ImmutableList<SpellData> spells;
	/**
	 * Base stats
	 */
	public final ImmutableList<BasicMod> baseMods;
	/**
	 * Base properties
	 */
	public final Properties baseProperties;
	
	public final EbiCreatureData resources;

	/**
	 * Textures
	 */
	public CreatureSkin skin;
	
	/**
	 * i18n bundle for every name, spell, description about this creature
	 */
	public I18NBundle i18n;
	
	/**
	 * Model model
	 */
	public Model model;
	
	public static interface CreatureCreator {
		public ImmutableList<CreatureType> createColors();
		public ImmutableList<CreatureType> createTypes();
		public ImmutableList<SpellData> createSpells();
		public ImmutableList<BasicMod> createBaseStats();
		public Properties createBaseProperties();
		public Skin createSkin();
		public Model createModel();
		public I18NBundle createI18N();
	}

	public CreatureData() {
		colors = discoverColors();
		types = discoverTypes();
		spells = discoverSpells();
		baseMods = discoverBaseMods();
		baseProperties = null;
		resources = null;
	}
	
	protected abstract ImmutableList<CreatureType> discoverColors();
	protected abstract ImmutableList<CreatureType> discoverTypes();
	protected abstract ImmutableList<BasicMod> discoverBaseMods();
	
	/**
	 * Add all spellsdata this creature can learn
	 */
	private ImmutableList<SpellData> discoverSpells() {
		var spells = new ArrayList<SpellData>();
		// base spells
		var spellClasses = new SpellDiscoverer().explore("com.souchy.randd.data.creatures." + getIDName());
		spellClasses.forEach(s -> {
			try {
				// add spell to list and spell icon to skin
				SpellData d = s.getDeclaredConstructor().newInstance();
				spells.add(d);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		// spells common to this creature's types
		// ...
		// specific common spells this creature can learn
		// ...
		return ImmutableList.copyOf(spells);
	}
	
	/**
	 * Name identifier for this creature / module. Ex : "Sungjin", "Kunoichi"
	 * This is used to identify the resource folder, i18n properties and skin objects.
	 */
	public abstract String getIDName();

	/**
	 * i18n name property identifier example : "Creatures.Sungjin.Name"
	 */
	public String getNamePropertyID() {
		return "Creatures." + getIDName() + ".Name";
	}
	
	
	public Creature createCreature(JadeCreature emerald) {
		var c = new Creature();
		//c.data = getCreatureData(emerald.creatureID)
		//c.book.spellGroups.add(e)
		return null;
	}
	
	
}
