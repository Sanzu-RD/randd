package com.souchy.randd.ebishoal.amethyst.ui.components;

import java.net.MalformedURLException;
import java.util.ResourceBundle;

import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.jade.meta.JadeCreature;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class DraftRow extends GridPane {

	@FXML
	public Label name;
	
	@FXML
	public ImageView icon;
	
	@FXML
	public HBox spells;
	
	@FXML
	public GridPane affinities;
	
//	public Label affinities;
	/**
	 * Customization
	 */
	public JadeCreature creature;
	
	public DraftRow(int creatureModelId) {
		// var model = DiamondModels.creatures.get(creatureModelId);
		creature.creatureModelID = creatureModelId;
		try {
			ResourceBundle b = ResourceBundle.getBundle("res/i18n/creatures/bundle");
			var namestr = b.getString("creature." + creatureModelId + ".name");
			Log.info("DraftRow i18n name : " + namestr);
			this.name.setText(namestr);
		} catch (Exception e) {
			this.name.setText("bambi");
		}
		this.icon.setImage(new Image(AssetData.creatures.get(creatureModelId).getIconURL().toString()));
		refreshAffinities();
		refreshSpells();
	}
	
	
	public void setAffinity(Element e, int affinity) {
		creature.affinities[e.val()] = affinity;
		refreshAffinities();
	}
	public void setSpell(int index, int id) {
		creature.spellIDs[index] = id;
		refreshSpells();
	}
	
	private void refreshAffinities() {
		// 2 rows
		var halfLength = creature.affinities.length / 2;
		for(int r = 0; r < 2; r++) {
			for(int i = r * halfLength; i < (r+1) * halfLength; i++) {
				affinities.addRow(r, new Label(String.valueOf(creature.affinities[r * halfLength + i])));
			}
		}
	}
	
	private void refreshSpells() {
		this.spells.getChildren().clear();
		for(int i = 0; i < creature.spellIDs.length; i++) {
			var spellid = creature.spellIDs[i];
			if(spellid == 0) {
				this.spells.getChildren().add(new ImageView());
				continue;
			}
			var res = AssetData.spells.get(spellid);
			try {
				this.spells.getChildren().add(new ImageView(res.getIconPath().toUri().toURL().toString()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
