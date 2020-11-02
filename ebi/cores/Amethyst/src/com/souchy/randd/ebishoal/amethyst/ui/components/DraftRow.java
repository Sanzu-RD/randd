package com.souchy.randd.ebishoal.amethyst.ui.components;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ResourceBundle;

import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.CreatureModel;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.ebishoal.amethyst.main.Amethyst;
import com.souchy.randd.jade.Constants;
import com.souchy.randd.jade.meta.JadeCreature;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class DraftRow extends GridPane {

	@FXML
	public Label name;
	
	@FXML
	public ImageView icon;
	
	@FXML
	public FlowPane spells;
	
	@FXML
	public FlowPane affinities;
	
	@FXML
	public Button btnPage;
	
//	public Label affinities;
	
	
	/**
	 * Customization
	 */
	public JadeCreature creature = new JadeCreature();
	public CreatureModel model;
	
	public DraftRow() {
//		Amethyst.app.loadComponent(this, "draftrow");
	}
	
//	public DraftRow(int creatureModelId) {
//		this();
//		model = DiamondModels.creatures.get(creatureModelId);
//		creature.creatureModelID = creatureModelId;
//		creature.affinities = new int[Elements.values().length];
//		creature.spellIDs = new int[Constants.numberOfSpells];
//	}
	
	@FXML
	public void initialize() {
		Amethyst.core.bus.register(this);
		
		this.name.setText("bambi");
		
		for (var e : Elements.values()) {
			this.affinities.getChildren().add(new AffinityBox(model, creature, e));
		}
	}
	
	public void init(int creatureModelId) {
		// get model
		model = DiamondModels.creatures.get(creatureModelId);
		// make creature
		creature.creatureModelID = creatureModelId;
		creature.affinities = new int[Elements.values().length];
		creature.spellIDs = new int[Constants.numberOfSpells];

		// get i18n name 
		//Log.info("draft row initialize " + creatureModelId);
		try {
			ResourceBundle b = ResourceBundle.getBundle("../res/i18n/creatures/bundle");
			var namestr = b.getString("creature." + creatureModelId + ".name");
			//Log.info("DraftRow i18n name : " + namestr);
			this.name.setText(namestr);
		} catch (Exception e) {
//			Log.info("", e);
			if(name != null) this.name.setText("missing bundle");
		}
		// get icon
		this.icon.setImage(new Image(AssetData.creatures.get(creatureModelId).getIconURL().toString()));
//		refreshAffinities();
//		refreshSpells();
	}
	
	
//	public void setAffinity(Element e, int affinity) {
//		creature.affinities[e.val()] = affinity;
//		refreshAffinities();
//	}
	public void setSpell(int index, int id) {
		creature.spellIDs[index] = id;
		refreshSpells();
	}
	
//	private void refreshAffinities() {
//		// 2 rows
//		for(int i = 0; i < creature.affinities.length; i++) {
//			var node = new AffinityBox();
//			affinities.getChildren().add(node);
//		}
//		
////		var halfLength = creature.affinities.length / 2;
////		for (int r = 0; r < 2; r++) {
////			for (int i = r * halfLength; i < (r + 1) * halfLength; i++) {
////				if(r * halfLength + i < creature.affinities.length) {
////					affinities.addRow(r, new Label(String.valueOf(creature.affinities[r * halfLength + i])));
////				}
////			}
////		}
//	}
	
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
