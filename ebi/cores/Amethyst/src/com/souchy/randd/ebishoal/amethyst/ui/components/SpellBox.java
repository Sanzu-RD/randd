package com.souchy.randd.ebishoal.amethyst.ui.components;

import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.CreatureModel;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.ebishoal.amethyst.main.Amethyst;
import com.souchy.randd.jade.meta.JadeCreature;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class SpellBox extends StackPane {

	/**
	 * Model reference for resetting value etc
	 */
	public final CreatureModel model;
	/**
	 * Creature we're editing/customizing
	 */
	public final JadeCreature creature;
	/**
	 * Spell for this box
	 */
	public Spell spell;
	
	/**
	 * Spell icon
	 */
	@FXML
	public ImageView icon;
	
	
	public SpellBox(CreatureModel model, JadeCreature creature, Spell s) {
		this.model = model;
		this.creature = creature;
		this.spell = s;
		Amethyst.app.loadComponent(this, "spellbox");
	}
	
	@FXML
	public void initialize() {
		Amethyst.core.bus.register(this);
		
		this.setOnMouseClicked(this::onClick);
	}
	
	public void onClick(MouseEvent e) {
		
	}
	
	public void setSpell(int id) {
		this.spell = DiamondModels.spells.get(id);
		
	}
	
	
	
	
}
