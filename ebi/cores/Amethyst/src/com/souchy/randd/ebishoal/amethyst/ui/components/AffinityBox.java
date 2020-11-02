package com.souchy.randd.ebishoal.amethyst.ui.components;

import com.souchy.randd.commons.diamond.models.CreatureModel;
import com.souchy.randd.jade.Constants;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.ebishoal.amethyst.main.Amethyst;
import com.souchy.randd.jade.meta.JadeCreature;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class AffinityBox extends StackPane {
	
	/**
	 * Model reference for resetting value etc
	 */
	public final CreatureModel model;
	/**
	 * Creature we're editing/customizing
	 */
	public final JadeCreature creature;
	/**
	 * element for this box
	 */
	public final Elements ele;
	
	
	/**
	 * label containing the current value
	 */
	@FXML
	public Label value;
	/**
	 * Element icon
	 */
	@FXML
	public ImageView icon;
	
	/**
	 * edit box showing on click / focus
	 */
	public TextField box = new TextField();
	
	public AffinityBox(CreatureModel model, JadeCreature creature, Elements ele) {
		this.model = model;
		this.creature = creature;
		this.ele = ele;
		Amethyst.app.loadComponent(this, "affinitybox");
	}
	
	@FXML
	public void initialize() {
		Amethyst.core.bus.register(this);
		
		// set base value
		this.value.setText(model.baseStats.affinity.get(ele).value() + "");
		this.box.setText(model.baseStats.affinity.get(ele).value() + "");
		// invisible field
		this.box.setVisible(false);
		// events
		this.setOnMouseClicked(this::onClick);
		this.setOnMouseExited(this::onExit);
		this.box.textProperty().addListener(this::onChange);
	}
	
	/**
	 * on enter, open the edit box
	 */
	public void onClick(MouseEvent e) {
		// go to edit mode
		if(e.isPrimaryButtonDown()) {
			value.setVisible(false);
			box.setVisible(true);
		} else 
	    // reset box->value on right click
		if(e.isSecondaryButtonDown()) {
			this.box.setText(String.valueOf(model.baseStats.affinity.get(ele).value()));
			this.box.setText(value.getText());
		}
	}

	/**
	 * on exit, close the edit box
	 */
	public void onExit(MouseEvent e) {
		box.setVisible(false);
		value.setVisible(true);
	}
	
	/**
	 * When changing the value from the textfield, set the value
	 */
	public void onChange(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		int val = Integer.parseInt(this.value.getText());
		try {
			val = Integer.parseInt(newValue);
		} catch (Exception e) {
			
		}
		// set val
		creature.affinities[ele.ordinal()] = val;
		
		// check if total is valid
		int totaldiff = 0;
		for(int i = 0; i < Elements.values().length; i++) {
			var e = Elements.values()[i];
			int jade = creature.affinities[i];
			int m = model.baseStats.affinity.get(e).value();
			totaldiff += Math.abs(jade - m);
		}
		var excedent = totaldiff - model.affinityPoints;
		var diff = val - model.baseStats.affinity.get(ele).value();
		
		// if total is too big
		if(excedent > 0) {
			if(diff > 0) val -= excedent;
			else val += excedent;
			creature.affinities[ele.ordinal()] = val;
		}
		
		if(diff == 0) {
			// if the value is the same as the model's
			this.value.setStyle("-fx-text-fill: lblColor1"); // white
			this.box.setStyle("-fx-text-fill: lblColor1"); // white
		} else {
			// if the value is different/edited
			this.value.setStyle("-fx-text-fill: d703fc"); // purple
			this.box.setStyle("-fx-text-fill: d703fc"); // purple
		}
		
		// copy box to value
		this.value.setText(box.getText());
	}
	
}
